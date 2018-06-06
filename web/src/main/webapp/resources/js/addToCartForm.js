"use strict";

function addToCard(data, onSuccess){
    $.ajax({
        type: "POST",
        data: data,
        url: "/phoneshop-web/ajaxCart",
        dataType: "json",
        success: onSuccess
    });
}

function updateCart(cartQuantity, cartSubTotal){
    $("#cart-amount").text(cartQuantity);
    $("#cart-price").text(cartSubTotal);
}

function onAddPhone(form, data){
    if(data.message !== "success"){
        form.find("input[name=quantity]").addClass("is-invalid");
        form.find(".invalid-tooltip").text(data.message);
    } else {
        updateCart(data.cartQuantity, data.cartSubTotal);
    }
}


$(".quantity-input").on("keydown", function(){
    $(this).parents(":eq(1)").find(".invalid-feedback").text("");
    $(this).removeClass("is-invalid");
});


$(".add-to-cart-btn").on("click", function(){
    var phoneId = $(this).data("id");
    var form = $("form[name="+phoneId+"]");

    form.submit();
});

$(".quantity-form").on("submit", function(e){
    e.preventDefault();

    addToCard($(this).serialize(), onAddPhone.bind(null, $(this)));
});