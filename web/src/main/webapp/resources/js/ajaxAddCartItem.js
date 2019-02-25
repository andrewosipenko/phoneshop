function ajaxAddCartItem(index, phoneId) {
    var cartItem = {
        phoneId : phoneId,
        quantity : Number($("#quantity" + phoneId).val())
    };

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        url: "ajaxCart",
        method: "POST",
        dataType: 'json',
        data: JSON.stringify(cartItem),
        success: function (result) {
            console.log(result);
        }
    })

}