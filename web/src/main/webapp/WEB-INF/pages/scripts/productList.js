$(document).ready(function() {
    $('body').append('<div id="tooltip" style="position: absolute;"><p style="color: red;"></p></div>');
    $('#tooltip').fadeOut();
    $.get({
        url: "${pageContext.request.contextPath}/ajaxCart",
        success: updateCartStatus
    });
});

var updateCartStatus = function(status) {
    $('#phonesTotal').html(status.phonesTotal);
    $('#costTotal').html(status.costTotal);
};

var sortBy = function (what) {
    var compare = '${param.sortBy}';
    if (what === compare)
        what += '_desc';
    $('#sortByInput').val(what);
    $('#sortByForm').submit();
};

var addToCart = function (id) {
    var quantityField = $('#phone' + id + 'Quantity');
    var quantity = quantityField.val();
    $.post({
        url: "${pageContext.request.contextPath}/ajaxCart",
        data: {
            quantity: quantity,
            phoneId: id
        }
    }).fail(function(response) {
        var tooltip = $('#tooltip');
        var pos = quantityField.offset();
        tooltip.css({top: pos.top + quantityField.height() + 20, left: pos.left});
        tooltip.find('>p').html(response.responseText);
        if (!tooltip.is(':visible')) {
            tooltip.fadeIn();
            tooltip.fadeOut({duration: 3000});
        }
    }).done(function(status) {
        quantityField.val(0);
        updateCartStatus(status);
    });
};