function ajaxAddCartItem(index, phoneId) {
    alert('dddd');
    var cartItem = {
        phoneId : 1,
        quantity : 2
    };
    // $.ajax({
    //     headers: {
    //         'Accept': 'application/json',
    //         'Content-Type': 'application/json',
    //         'Locale': locale
    //     },
    //     url : 'ajaxCart/add',
    //     type: 'POST',
    //     dataType: 'json',
    //     // contentType: 'application/json',
    //     // mimeType: 'application/json',
    //     data: JSON.stringify(cartItem),
    //     success: function (result) {
    //
    //     }).do
    // });
    //



    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',

        },
        url: "ajaxCart",
        method: "POST",
        dataType: 'json',
        data: JSON.stringify(cartItem)
    })
        .done(function (data, textStatus, jqXHR) {
            alert("succes")
        })
        .fail(function (jqXHR, textStatus, errorThrown) {
            alert("not succes")
        });
}