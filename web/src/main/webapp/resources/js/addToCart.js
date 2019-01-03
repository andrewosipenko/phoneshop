function addToCart(phoneId, quantity) {
    var cartForm = {
        "phoneId" : phoneId,
        "quantity" : quantity
    }
    var request = $.ajax({
        type: "POST",
        contentType : 'application/json; charset=utf-8',
        dataType : 'json',
        url: '${pageContext.request.contextPath}/ajaxCart',
        data: JSON.stringify(cartForm)
    });

    request.done(function (data) {
        $("#quantity" + phoneId).val("1");
        $("#cartAmount").text(data.cartAmount);
        $("#subtotal").text(data.subtotal);

        var selector = '#errorMessage' + phoneId;
        $(selector).html("");
    });

    request.fail(function (errorMessage) {
        var selector = '#errorMessage' + phoneId;
        $(selector).html(errorMessage.responseText);
    });
}