function addToCart(id){
	var url = $('#context-path').val() + "/ajaxCart"
	var quantity = $("#quantity-" + id).val();
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
		success : function(cartStatus) {
			$("#cart-count").text(cartStatus.phonesCount);
			$("#cart-cost").text(cartStatus.cartCost);
			$("#error-message-" + id).text("");
		},
		error : function(response) {
			var cartStatus = JSON.parse(response.responseText);
			$("#error-message-" + id).text(cartStatus.errorMessage);
		}
		});
}