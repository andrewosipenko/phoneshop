$(init);

function init() {
    $(".add-to-cart").bind('click', addToCart);
}

function addToCart() {
    var parentTr = $(this).parent().parent();
    var url = context_path + "/ajaxCart";
    var id = parentTr.find('.phone-id').val();
    var quantity = parentTr.find('.phone-quantity').val();
    var requestData = {
        phoneId: id,
        quantity: quantity
    };

    $.ajax({
        url: url,
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(requestData),
        success: function (cartStatus) {
            $(".cart-count").text(cartStatus.phonesCount);
            $(".cart-cost").text(cartStatus.cartCost);
            parentTr.find('.error-message').text("");
        },
        error: function (response) {
            var cartStatus = JSON.parse(response.responseText);
            parentTr.find('.error-message').text(cartStatus.errorMessage);
        }
    });
}