function ajaxAddCartItem(phoneId) {
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
            var status = result.status;
            switch (status) {
                case 'SUCCESS': {
                    document.getElementById('countOfCartItem').innerHTML = result.countOfCartItems;
                    document.getElementById('totalPrice').innerHTML = result.totalPrice;
                    document.getElementById('quantity' + phoneId).value = '';
                } break;
                case 'ERROR': {
                    console.log('errorField' + phoneId);
                    document.getElementById('errorField' + phoneId).innerHTML = result.errors[0].defaultMessage;
                }
            }
        }
    })

}