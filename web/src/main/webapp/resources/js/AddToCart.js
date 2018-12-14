$("#addToCart").click(function(event) {

    var data = {}
    data["productId"] = $("#jsonProductId").val();
    data["quantity"] = $("#quantity").val();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "addToCart",
        data: JSON.stringify(data),
        dataType: 'json',
        timeout: 600000,
        success: function (data) {

            //...
        },
        error: function (e) {

            //...
        }
    });
    event.preventDefault();

});