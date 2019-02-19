function ajaxAddCartItem(index, phoneId) {
    var cartItem = {
        phoneId : phoneId,
        quantity : $("#quantity" + phoneId).val()
    };
    $.ajax({
        url : 'ajaxCart/add',
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        data: cartItem,
        success: function (result) {

        }
    });
}