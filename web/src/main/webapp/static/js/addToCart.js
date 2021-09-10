
$(document).ready($(document).ajaxSend(function (e, xhr, options) {
    const csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
    const csrfHeader = $("meta[name='_csrf_header']").attr("content");
    const csrfToken = $("meta[name='_csrf']").attr("content");
    xhr.setRequestHeader(csrfHeader, csrfToken);
}));
function addToCart(phoneId, url) {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        url: url,
        type: 'post',
        dataType: 'json',
        data: JSON.stringify({
            phoneId: phoneId,
            quantity: $('#quantity-' + phoneId).val()
        }),
        success: function (response) {
            showAddToCartResponse('ok', 'Successfully added to cart', phoneId);
            console.log(response);
            let totalCount = response.infoCart.totalCount;
            let subtotalPrice = response.infoCart.subtotalPrice;
            $('#totalCount').html(totalCount);
            $('#subtotalPrice').html(subtotalPrice);
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
