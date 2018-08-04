var updateStatus = function(orderId, status) {
    $.post({
        url: '${pageContext.request.contextPath}/admin/orders/' + orderId,
        data: {
            status: status
        },
        success: function() { location.reload(); }
    });
};