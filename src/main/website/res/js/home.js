var API_URL = 'https://s7pc06oh92.execute-api.us-west-2.amazonaws.com/test/';

$(document).ready(function () {
    //  party mode below!
    //    createPartyBackground();
    getCheapest();
    if (localStorage.getItem("username") != null) {
        console.log("hey");
    }

    $("#update_popup").popup({
        transition: 'all 0.3s'
    });

    $("#sign_in_popup").popup({
        transition: 'all 0.3s'
    });

    $("#submit-update-beer").click(function (ev) {
        handleUpdate();
        ev.preventDefault();
    });

    $("#submit-sign-in").click(function (ev) {
        authenticateUser();
        ev.preventDefault();
    });

    $("#filter-store").click(function (ev) {
        displayStores();
        ev.preventDefault();
    });

    $("#filter-beer-name").click(function (ev) {
        displayBeers();
        ev.preventDefault();
    });

});



function getCheapest() {
    $.ajax({
        url: API_URL + 'all-beers?',
        type: 'GET',
        success: function (response) {
            if (response == null) {
                console.log("Dang");
            } else {
                response.forEach(function (oneQuantity) {
                    if (oneQuantity.length == 0)
                        return;
                    createTrs(oneQuantity);
                });
            }
        }
    });

}

function createTrs(oneQuantity) {
    console.log(oneQuantity);
    var quantity = oneQuantity[0].quantity;
    $("#brews-table-" + quantity + " tbody").empty();
    oneQuantity.forEach(function (entry) {
        var tr = "<tr>";
        tr += "<td>$" + (Number(entry.price)).toFixed(2) + "</td>";
        tr += "<td>" + entry.name + "</td>";
        tr += "<td>" + entry.storeName + "</td>";
        tr += "<td>" + moment(new Date(0).setUTCMilliseconds(entry.timestamp)).fromNow() + "</td>";
        tr += "</tr>";
        $("#brews-table-" + entry.quantity + " tbody").append(tr);
    });
    buildTable(quantity);
}

function buildTable(quantity) {
    $("#brews-table-" + quantity + " > tbody > tr").hide().slice(0, 3).show();

    $("#show-" + quantity).on("click", function () {
        if ($(this).attr("value") == "hide") {
            $("tbody > tr", $(this).prev()).show();
            $("#show-" + quantity).html("Show fewer");
            $("#show-" + quantity).val("show");
        } else {
            $("tbody > tr", $(this).prev()).hide();
            $("#show-" + quantity).html("Show more");
            $("#show-" + quantity).val("hide");

            $("#brews-table-" + quantity + " > tbody > tr").hide().slice(0, 3).show();
        }
    });
}


function handleUpdate() {
    var data = {
        storeName: $("#store").val(),
        name: $("#beer").val(),
        quantity: $("input[name='quantity']:checked").val(),
        price: (Number($("#price").val())).toFixed(2)
    };
    $.ajax({
        url: API_URL + 'update-beer',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (response) {
            console.log(response);
            if (response != null) {
                console.log("failed");
            } else {
                closeUpdateWindow();
            }
        }
    });
}




function createPartyBackground() {
    for (var x = -1; x < $(document).width() / 140; x += 1) {
        for (var y = -1; y < $(document).height() / 130; y += 1) {
            var img = $("<img />", {
                style: "position:absolute; top:" + ((y * 14) + 5) + "%; left:" + (x * 15) + "%; z-index: -1;",
                src: "res/img/party.gif",
                width: "19%"
            });
            img.appendTo($("#background-imgs"));
        }
    }

}


function authenticateUser() {
    var data = {
        username: $("#username").val(),
        password: $("#password").val(),
    };
    $.ajax({
        url: API_URL + 'authenticate-user',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (response) {
            console.log(response);
            if (!response) {
                alert("Invalid username or password.")
            } else {
                localStorage.setItem("username", $("#username").val());
                closeSignInWindow();
            }
        }
    });

}


function closeUpdateWindow() {
    $("#update_popup").popup("hide");
}


function closeSignInWindow() {
    $("#sign_in_popup").popup("hide");
}


function displayStores() {
    $.ajax({
        url: API_URL + 'get-all-stores',
        type: 'GET',
        success: function (response) {
            response.forEach(function (store) {
                $("#options").append("<input type='checkbox' name='store' value='" + store + "'>" + store + "<br>");
            });
            $("#options").append("<button type='submit' id='submit-store'>Filter by these stores!</button>");
            $("#options-container").show();
            $("#submit-store").click(function (ev) {
                var selected = [];
                $("#options-container input:checked").each(function () {
                    selected.push($(this).attr('value'));
                });
                console.log(selected);
                filterStores(selected);
                ev.preventDefault();
            });
        }
    });

}


function displayBeers() {
    $.ajax({
        url: API_URL + 'get-all-beer-names',
        type: 'GET',
        success: function (response) {
            console.log(response);
        }
    });

}


function filterStores(stores) {
    var data = {};

    var ndx = 0;
    stores.forEach(function (store) {
        data[ndx.toString()] = store;
        ndx += 1;
    });

    console.log(data);

    $.ajax({
        url: API_URL + 'filter-stores',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (response) {
            if (response == null) {
                console.log("Dang");
            } else {
                $("#brews-table-12 tbody").empty();
                $("#brews-table-30 tbody").empty();
                response.forEach(function (oneQuantity) {
                    if (oneQuantity == null || oneQuantity.length == 0)
                        return;
                    createTrs(oneQuantity);
                });
            }
        }
    });
}


function filterBeers() {
    var data = {};

    $.ajax({
        url: API_URL + 'filter-stores',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (response) {
            if (response == null) {
                console.log("Dang");
            } else {
                response.forEach(function (oneQuantity) {
                    if (oneQuantity == null || oneQuantity.length == 0)
                        return;
                    createTrs(oneQuantity);
                });
            }
        }
    });

}
