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
    if(data.valid){
        updateCart(data.cartQuantity, data.cartSubTotal);
    } else {
        form.find("input[name=quantity]").addClass("is-invalid");
        var tooltip = form.find(".invalid-tooltip");
        tooltip.empty();
        data.errors.forEach(function(message){
            tooltip.append("<p class=\"mb-0\">"+message+"</p>");
        });
    }
}


$(".quantity-input").on("keydown", function(){
    $(this).parents(":eq(1)").find(".invalid-feedback").empty();
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