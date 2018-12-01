function addToCart(phoneId, quantity) {
    var data = {phoneId: phoneId, quantity: quantity};
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        url: "ajaxCart",
        method: "POST",
        dataType: 'json',
        data: JSON.stringify(data)
    })
        .done(function (data, textStatus, jqXHR) {
            setLabel(phoneId, quantity, data.message);
            if (data.hasOwnProperty('cartItemsAmount')) {
                var cartInfo = "My cart: " + data.cartItemsAmount + " items $" + data.cartItemsPrice;
                $('#cart').text(cartInfo);
            }
        })
        .fail(function (jqXHR, textStatus, errorThrown) {
            $('#result').text("Something is wrong: " + textStatus + " " + errorThrown);
        });
}

function setLabel(phoneId, quantity, message) {
    if (message === 'success') {
        document.getElementById("label" + phoneId).innerHTML = '<p style="color: green">Successfully adding ' + quantity + ' products to cart</p>';
    } else {
        document.getElementById("label" + phoneId).innerHTML = '<p style="color: red">' + message + '</p>';
    }
}