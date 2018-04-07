$(document).ready(function() {
    $.get({
        url: "${pageContext.request.contextPath}/ajaxCart",
        success: updateCartStatus
    });
});

var updateCartStatus = function(status) {
    $('#phonesTotal').html(status.phonesTotal);
    $('#costTotal').html(status.costTotal);
};

var addToCart = function (id) {
    var data = $('#addToCart' + id + 'Form').serialize();
    $.post({
        url: 'ajaxCart',
        data: data,
        success: function(status) {
            $('#phone' + id + 'Quantity').val(0);
            updateCartStatus(status);
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

var sortBy = function (what) {
    var compare = '${param.sortBy}';
    if (what === compare)
        what += '_desc';
    $('#sortByInput').val(what);
    $('#sortByForm').submit();
};