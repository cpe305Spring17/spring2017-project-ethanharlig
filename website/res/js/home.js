$(document).ready(function () {
    getCheapest();
});


function getCheapest() {
    $.ajax({
        url: 'https://s7pc06oh92.execute-api.us-west-2.amazonaws.com/test/all-beers?',
        type: 'GET',
        success: function (response) {
            if (response == null) {
                alert("Dang");
            } else {
                response.forEach(function (oneQuantity) {
                    var quantity = oneQuantity[0].quantity;
                    oneQuantity.forEach(function (entry) {
                        var tr = "<tr>";
                        tr += "<td>$" + entry.price + "</td>";
                        tr += "<td>" + entry.name + "</td>";
                        tr += "<td>" + entry.storeName + "</td>";
                        tr += "<td>" + moment(new Date(0).setUTCMilliseconds(entry.timestamp)).fromNow() + "</td>";
                        tr += "</tr>";
                        $("#brews-table-" + entry.quantity + " tbody").append(tr);
                    });
                    toggleButton(quantity);
                });
            }
        }
    });

}

function toggleButton(quantity) {
    $("#brews-table-" + quantity + " > tbody > tr").hide().slice(0, 3).show();
    $("#show-" + quantity).on("click", function () {
        $("tbody > tr", $(this).prev()).show();
        $("#show-" + quantity).html("Show fewer");
    });
}
