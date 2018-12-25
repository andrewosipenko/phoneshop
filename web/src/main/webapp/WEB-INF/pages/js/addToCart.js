function addToCart(phoneId, quantity) {
    var request = $.ajax({
        url: '${pageContext.request.contextPath}/ajaxCart',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Locale': locale
        },
        type: 'POST',
        data: 'phoneId=' + phoneId + '&quantity=' + quantity
    });

    request.done(function (data) {
        $("#quantity" + phoneId).val("0");
        $("#itemsAmount").text(data.cartItemsAmount);
        $("#subtotal").text(data.cartItemsPrice);

        var selector = '#errorMessage' + phoneId;
        $(selector).html("");
    });

    request.fail(function (errorMessage) {
        var selector = '#errorMessage' + phoneId;
        $(selector).html(errorMessage.responseText);
    });
}