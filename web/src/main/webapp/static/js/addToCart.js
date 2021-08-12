$(function () {
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});

function addToCart(phoneId, url) {
    $.ajax({
        url: url,
        type: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        data: JSON.stringify({
            phoneId: phoneId,
            quantity: $('#quantity-' + phoneId).val()
        }),
        datatype: 'json',

        success: function (response) {
            showAddToCartResponse('ok', 'Successfully added to cart', phoneId);
        },
        error: function (errorResponse) {
            showAddToCartResponse('error', errorResponse.responseText, phoneId);
        }
    })
}

function showAddToCartResponse(status, message, phoneId) {
    let responseMessage = $('#quantity-message-' + phoneId);
    responseMessage.text(message);
    responseMessage.css("color", 'ok' === status ? "green" : "red");
    responseMessage.show();
}
