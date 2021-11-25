function doAjaxPost(id) {
    // get the form values
    var cartAddForm = $('#cartAddForm' + id).serialize();
    $.post({
        type: "POST",
        url: "${pageContext.request.contextPath}/ajaxCart",
        data: cartAddForm,
        success: function (status) {
            $('#phone' + id).html('');
            $('#totalQuantity').html(status.totalCount);
            $('#totalCost').html(status.totalCost);
            $('#error-message-'+id).html('');
        }
    }).fail(function(msg) {
        var errorMessage = $('#error-message-' + id);
        errorMessage.html('');
        errorMessage.html(msg.responseText);
    });
}