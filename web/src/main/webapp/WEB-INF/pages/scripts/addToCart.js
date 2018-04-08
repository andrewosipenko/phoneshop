var addToCart = function (id) {
    var data = $('#addToCart' + id + 'Form').serialize();
    $.post({
        url: '${pageContext.request.contextPath}/ajaxCart',
        data: data,
        success: function(status) {
            $('#phone' + id + 'Quantity').val(0);
            $('#phonesTotal').html(status.phonesTotal);
            $('#costTotal').html(status.costTotal);
        }
    }).fail(function(msg) {
        var msgHolder = $('#quantity' + id + 'ErrorMessage');
        msgHolder.stop(true, true);
        msgHolder.html('');
        msgHolder.html(msg.responseText);
        msgHolder.fadeIn();
        msgHolder.fadeOut({duration: 3000});
    });
};