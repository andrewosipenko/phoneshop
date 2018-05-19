function addToCart(phoneId, quantity) {
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var headers = {};
    headers[csrfHeader] = csrfToken;

    var request = $.ajax({
        url: '${pageContext.request.contextPath}/ajaxCart',
        headers: headers,
        type: 'POST',
        data: 'phoneId=' + phoneId + '&quantity=' + quantity
    });

    request.done(function (data) {
        $("#quantity" + phoneId).val("0");
        $("#itemsAmount").text(data.itemsAmount);
        $("#subtotal").text(data.subtotal);

        var selector = '#errorMessage' + phoneId;
        $(selector).html("");
    });

    request.fail(function (errorMessage) {
        var selector = '#errorMessage' + phoneId;
        $(selector).html(errorMessage.responseText);
    });
}


