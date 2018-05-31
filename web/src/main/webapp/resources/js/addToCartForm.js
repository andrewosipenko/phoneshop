"use strict";

function addToCard(phoneId, quantity, onSuccess){
    $.ajax({
        type: "POST",
        contentType: "application/json;charset=utf-8",
        url: "/phoneshop-web/ajaxCart?phoneId="+phoneId+"&quantity="+quantity,
        dataType: "json",
        success: onSuccess
    });
}

function updateCart(cartQuantity, cartSubTotal){
    $("#cart-amount").text(cartQuantity);
    $("#cart-price").text(cartSubTotal);
}

$(".quantity-input").on("keyup", function(){
    $(this).parents(":eq(1)").find(".invalid-feedback").text("");
    $(this).removeClass("is-invalid");
});

$(".add-to-cart-btn").on("click", function(){
    var inputGroup = $(this).parents(":eq(1)").find(".input-group");
    var input = inputGroup.find("input");
    var quantity = input.val();
    var phoneId = $(this).data("id");

    addToCard(phoneId, quantity, function(result){
        if(result.message!=="success"){
            input.addClass("is-invalid");
            inputGroup.find(".invalid-tooltip").text(result.message);
        } else {
            updateCart(result.cartQuantity, result.cartSubTotal);
        }
    });
});

$("#add-to-cart-btn").on("click", function () {
    var inputGroup = $("#quantity-input-div").find(".input-group");
    var input = inputGroup.find("input");
    var quantity = input.val();
    var phoneId = $(this).data("id");

    addToCard(phoneId, quantity, function (result) {
        if (result.message !== "success") {
            input.addClass("is-invalid");
            inputGroup.find(".invalid-tooltip").text(result.message);
        } else {
            updateCart(result.cartQuantity, result.cartSubTotal);
        }
    });
});