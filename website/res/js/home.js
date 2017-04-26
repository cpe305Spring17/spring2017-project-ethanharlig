$(document).ready(function () {
    getCheapest();
});


function getCheapest() {
    $.ajax({
        url: 'https://s7pc06oh92.execute-api.us-west-2.amazonaws.com/test/cheapest-price?',
        type: 'GET',
        success: function (response) {
            if (response == null) {
                alert("Dang");
            } else {
                var brewsTableBody = $("#brews-table tbody");
                response.forEach(function (entry) {
                    var tr = "<tr>";
                    tr += "<td>$" + entry.price + "</td>";
                    tr += "<td>" + entry.quantity + " beers</td>";
                    tr += "<td>" + entry.name + "</td>";
                    tr += "<td>" + entry.storeName + "</td>";
                    tr += "<td>" + moment(new Date(0).setUTCMilliseconds(entry.timestamp)).fromNow() + "</td>";
                    tr += "</tr>";
                    brewsTableBody.append(tr);
                });
            }
        },
        complete: function () {}
    });

}
