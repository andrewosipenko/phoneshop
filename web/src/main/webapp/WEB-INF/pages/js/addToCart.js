var addToCart = function (id) {
    var data = $('#addToCart' + id).serialize();
    $.post({
        url: '${pageContext.request.contextPath}/ajaxCart',
        data: data,
        success: function(status) {
            $('#phone-' + id + 'quantity').html('');
            $('#itemsCount').html(status.itemsCount);
            $('#cartCost').html(status.cost);
            $('#error-message-'+id).html('');
        }
    }).fail(function(msg) {
        var errorMessage = $('#error-message-' + id);
        errorMessage.html('');
        errorMessage.html(msg.responseText);
    });
};