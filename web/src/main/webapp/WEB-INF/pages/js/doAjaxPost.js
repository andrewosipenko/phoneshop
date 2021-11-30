function doAjaxPost(id) {
    $.ajax({
        type: 'POST',
        url: '${pageContext.request.contextPath}/ajaxCart',
        quantity: $('quantity' + id).val(),
        dataType: 'json',
        success: function(text) {
            $('error-message' + id).text('Response: ' + text);
        },
        error: function (jqXHR) {
            $(document.body).text('Error: ' + jqXHR.status);
        }
    });
}