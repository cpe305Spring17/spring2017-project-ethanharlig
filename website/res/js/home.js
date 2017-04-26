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
                console.log(response);
            }
        },
        complete: function () {}
    });

}
