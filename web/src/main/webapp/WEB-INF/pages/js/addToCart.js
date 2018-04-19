function addToCart (phoneId, quantity) {
    var request = $.ajax({
        url: '${pageContext.request.contextPath}/ajaxCart',
        type: 'POST',
        data: 'phoneId=' + phoneId + '&quantity=' + quantity,
    });

    request.done(function(data){
        $("#quantity" + phoneId).val("0");
        $("#itemsAmount").text(data.itemsAmount);
        $("#subtotal").text(data.subtotal);
    });

    request.fail(function(errorMessage){
        var selector = '#errorMessage' + phoneId;
        $(selector).text(errorMessage.responseText);
    });
}

