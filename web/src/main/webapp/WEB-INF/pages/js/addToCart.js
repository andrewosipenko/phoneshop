function addToCart(phoneId, quantity) {
    var request = $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        url: 'ajaxCart',
        type: 'POST',
        data: 'phoneId=' + phoneId + '&quantity=' + quantity
    });

    request.done(function (data) {
        $("#quantity" + phoneId).val("1");
        $("#cartItemsAmount").text(data.cartItemsAmount);
        $("#cartItemsPrice").text(data.cartItemsPrice);

        var selector = '#errorMessage' + phoneId;
        $(selector).html("");
    });

    request.fail(function (errorMessage) {
        var selector = '#errorMessage' + phoneId;
        $(selector).html(errorMessage.responseText);
    });
}
