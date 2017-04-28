var API_URL = 'https://s7pc06oh92.execute-api.us-west-2.amazonaws.com/test/';

$(document).ready(function () {
    //  party mode below!
    //    createPartyBackground();

    getCheapest();

    $("#my_popup").popup({
        transition: 'all 0.7s'
    });

    $("#submit-update-beer").click(function (event) {
        handleUpdate();
        event.preventDefault();
    });
});


function getCheapest() {
    $.ajax({
        url: API_URL + 'all-beers?',
        type: 'GET',
        success: function (response) {
            if (response == null) {
                alert("Dang");
            } else {
                response.forEach(function (oneQuantity) {
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
                });
            }
        }
    });

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
                alert("failed");
            } else {
                closeWindow();
            }
        }
    });
}

function closeWindow() {
    $("#my_popup").popup("hide");
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
