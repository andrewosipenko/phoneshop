var addToCart = function (id) {
    var data = $('#addToCart' + id).serialize();
    $.post({
        url: '${pageContext.request.contextPath}/ajaxCart',
        data: data,
        success: function(status) {
            $('#phone-' + id + 'quantity').html('');
            $('#itemsCount').html(status.itemsCount);
            $('#cartCost').html(status.cost);
        }
    }).fail(function(msg) {
        var message = $('#error-message-' + id);
        message.html('');
        message.html(msg.responseText);
    });
};