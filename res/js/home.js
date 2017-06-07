var API_URL = 'https://s7pc06oh92.execute-api.us-west-2.amazonaws.com/test/';
var subscriptions = [];

$(document).ready(function () {
    //  party mode below!
    //    createPartyBackground();
    if (sessionStorage.getItem("username") != null) {
        $("#sign-in").hide();
        $("#sign-out").show();
        getSubscriptions(sessionStorage.getItem("username"));
    } else {
        $("#sign-out").hide();
        $("#sign-in").show();
        getCheapest();
    }

    $("#update_popup").popup({
        transition: 'all 0.3s'
    });

    $("#sign_in_popup").popup({
        transition: 'all 0.3s'
    });

    $("#sign-out").click(function (ev) {
        sessionStorage.removeItem("username");
        $("#sign-out").hide();
        $("#sign-in").show();
        subscriptions = [];
        getCheapest();
    });

    $("#submit-update-beer").click(function (ev) {
        $("#submit-update-beer").button("loading");
        handleUpdate();
        ev.preventDefault();
    });

    $("#submit-sign-in").click(function (ev) {
        $("#submit-sign-in").button("loading");
        authenticateUser();
        ev.preventDefault();
    });

    $("#submit-sign-up").click(function (ev) {
        $("#submit-sign-up").button("loading");
        addUser();
        ev.preventDefault();
    });

    $("#sign-in-btn").click(function (ev) {
        $("#sign-up-form").hide();
        $("#sign-in-form").show();
        ev.preventDefault();
    });

    $("#sign-up-btn").click(function (ev) {
        $("#sign-in-form").hide();
        $("#sign-up-form").show();
        ev.preventDefault();
    });

    $("#filter-store").click(function (ev) {
        $("#options").empty();
        $("#generating").show();
        displayStores();
        ev.preventDefault();
    });

    $("#filter-beer-name").click(function (ev) {
        $("#options").empty();
        $("#generating").show();
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
                    createTrs(oneQuantity, subscriptions);
                });
            }
        }
    });
}

function createTrs(oneQuantity, toCheck) {
    if (oneQuantity.length == 0)
        return;
    var quantity = oneQuantity[0].quantity;
    $("#brews-table-" + quantity + " tbody").empty();

    var ndx = 0;

    oneQuantity.forEach(function (entry) {
        var tr = "<tr>";
        tr += "<td>$" + (Number(entry.price)).toFixed(2) + "</td>";
        tr += "<td>" + entry.name + "</td>";
        tr += "<td>" + entry.storeName + "</td>";
        tr += "<td>" + moment(new Date(0).setUTCMilliseconds(entry.timestamp)).fromNow() + "</td>";

        if (sessionStorage.getItem("username") != null) {
            if (toCheck.indexOf(entry.name + "-" + quantity) == -1) {
                tr += "<td><button type='button' class='btn btn-default' data-loading-text='Subscribing' id='subscribe-" + quantity + "-" + ndx + "'>Subscribe</button></td>";
                tr += "</tr>";
                $("#brews-table-" + entry.quantity + " tbody").append(tr);
                $("#subscribe-" + quantity + "-" + ndx).click(function (ev) {
                    $(this).button("loading");
                    subscribeToBeer(entry.name, quantity, ndx);
                });
            } else {
                tr += "<td><button type='button' class='btn btn-default' data-loading-text='Unsubscribing' id='unsubscribe-" + quantity + "-" + ndx + "'>Unsubscribe</button></td>";
                tr += "</tr>";
                $("#brews-table-" + entry.quantity + " tbody").append(tr);
                $("#unsubscribe-" + quantity + "-" + ndx).click(function (ev) {
                    $(this).button("loading");
                    unsubscribeFromBeer(entry.name, quantity, ndx);
                });
            }
        } else {
            tr += "</tr>";
            $("#brews-table-" + entry.quantity + " tbody").append(tr);
        }
        ndx++;
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
            $("#submit-update-beer").button("reset");
            if (response != null) {
                console.log("failed");
            } else {
                closeUpdateWindow();
                getSubscriptions(sessionStorage.getItem("username"));
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
            $("#submit-sign-in").button("reset");
            if (response == null) {
                console.log("Invalid username or password.")
            } else {
                sessionStorage.setItem("username", $("#username").val());
                sessionStorage.setItem("email", response);
                closeSignInWindow();
                $("#sign-in").hide();
                $("#sign-out").show();
                getSubscriptions($("#username").val());
            }
        }
    });
}


function addUser() {
    var data = {
        username: $("#sign-up-username").val(),
        email: $("#sign-up-email").val(),
        password: $("#sign-up-password").val()
    };
    $.ajax({
        url: API_URL + 'add-user',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (response) {
            $("#submit-sign-up").button("reset");
            if (!response) {
                alert("That username already exists. Please try to sign up again with a different username!");
            } else {
                $("#sign-up-form").hide();
                $("#sign-in-form").show();
            }
        }
    });
}


function getSubscriptions(username) {
    if (username == null) {
        getCheapest();
    } else {
        var data = {
            username: username
        };
        $.ajax({
            url: API_URL + 'get-subscriptions',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (response) {
                if (!response) {
                    console.log("Invalid username or password.")
                } else {
                    subscriptions = response;
                    getCheapest();
                }
            }
        });
    }
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
            $("#generating").hide();
            response.forEach(function (store) {
                $("#options").append("<input type='checkbox' name='store' value='" + store + "'>" + store.substring(0, 17) + "<br>");
            });
            $("#options").append("<button class='btn btn-default' type='submit' id='submit-store'>Filter</button>");
            $("#options-container").show();
            $("#submit-store").click(function (ev) {
                var selected = [];
                $("#options-container input:checked").each(function () {
                    selected.push($(this).attr('value'));
                });
                filterBy(selected, 'stores');
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
            $("#generating").hide();
            response.forEach(function (beerName) {
                $("#options").append("<input type='checkbox' name='store' value='" + beerName + "'>" + beerName.substring(0, 17) + "<br>");
            });

            $("#options").append("<button class='btn btn-default' type='submit' id='submit-beer'>Filter</button>");
            $("#options-container").show();
            $("#submit-beer").click(function (ev) {
                var selected = [];
                $("#options-container input:checked").each(function () {
                    selected.push($(this).attr('value'));
                });
                filterBy(selected, 'beers');
                ev.preventDefault();
            });
        }
    });
}


function filterBy(input, toFilterBy) {
    var data = {
        filter: toFilterBy
    };

    var ndx = 0;
    input.forEach(function (eachInput) {
        data[ndx.toString()] = eachInput;
        ndx += 1;
    });

    $.ajax({
        url: API_URL + 'filter',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (response) {
            if (response == null) {
                console.log("Dang");
            } else {
                $("#brews-table-12 tbody").empty();
                $("#brews-table-30 tbody").empty();
                var beer12 = [];
                var beer30 = [];

                response.forEach(function (oneQuantity) {
                    if (oneQuantity == null || oneQuantity.length == 0)
                        return;
                    oneQuantity.forEach(function (entry) {
                        if (entry.quantity == 12)
                            beer12.push(entry);
                        else
                            beer30.push(entry);
                    });
                });
                createTrs(beer12, subscriptions);
                createTrs(beer30, subscriptions);
            }
        }
    });
}


function subscribeToBeer(beerName, quantity, ndx) {
    var data = {
        email: sessionStorage.getItem('email'),
        username: sessionStorage.getItem('username'),
        beerName: beerName,
        quantity: quantity
    };

    console.log(data);

    $.ajax({
        url: API_URL + 'observe-beer',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (response) {
            if (response != null) {
                console.log("Couldn't subscribe to beer.")
            } else {
                getSubscriptions(sessionStorage.getItem('username'));
                $("#subscribe-" + quantity + "-" + ndx).button("reset");
            }
        }
    });
}


function unsubscribeFromBeer(beerName, quantity) {
    var data = {
        email: sessionStorage.getItem('email'),
        username: sessionStorage.getItem('username'),
        beerName: beerName,
        quantity: quantity
    };

    console.log(data);

    $.ajax({
        url: API_URL + 'unobserve-beer',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (response) {
            if (response != null) {
                console.log("Couldn't unsubscribe from beer.")
            } else {
                getSubscriptions(sessionStorage.getItem('username'));
            }
        }
    });
}
