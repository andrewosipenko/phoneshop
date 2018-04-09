function addToCart (phoneId, quantity) {
    $.ajax({
        url: '${pageContext.request.contextPath}/ajaxCart',
        type: 'POST',
        data: 'phoneId=' + phoneId + '&quantity=' + quantity,
        success:  function(receivedData){
                functionSuccess(receivedData, phoneId);
        },
        error: function (){
            functionError(phoneId);
        }
    });
}

function functionSuccess(receivedData, phoneId) {
    $("#" + phoneId).val("0");
    $("#itemsAmount").text(receivedData.itemsAmount);
    $("#subtotal").text(receivedData.subtotal);
}

function functionError(phoneId) {
    var selector = '#errorMessage' + phoneId;
    $(selector).text("wrong format");
}
