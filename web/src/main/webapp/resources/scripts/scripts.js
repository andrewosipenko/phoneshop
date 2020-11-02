function addPhoneToCart(phoneId) {
    let quantity = $('#quantity-' + phoneId).val();
    let quantityInputId = 'quantity-' + phoneId;
    let addPhoneButtonId = 'btn-addPhoneToCart-' + phoneId;
    let quantityInputMessageId = 'quantityInputMessage-' + phoneId;
    $(document).ready(function () {
        $('#' + addPhoneButtonId).prop("disabled", true);
        $.post('ajaxCart/', {
            phoneId: phoneId,
            quantity: quantity
        }).done(function (data) {
            $("#" + addPhoneButtonId).prop("disabled", false);
            if (data.successful) {
                $("#" + quantityInputMessageId).text(data.message).css({'color': 'green'});
            } else {
                $("#" + quantityInputMessageId).text(data.message).css({'color': 'red'});
            }
            loadMiniCart(data.miniCart)
        })
    });
}

function clearError(phoneId) {
    $("#quantityInputMessage-"+ phoneId).text("dick")
}

function getMiniCart() {
    $(document).ready(function () {
        $.get('ajaxCart/', function (data) {
            loadMiniCart(data)
        })
    });
}

function loadMiniCart(miniCart) {
    $("#miniCart").text("My cart: " + miniCart.totalQuantity + " items " + miniCart.totalCost + " $");
}