"use strict";

var PAGE_URL = "/phoneshop-web/productList";

$(".quantity-input").on('keyup', function(){
    $(this).parents(":eq(1)").find(".invalid-feedback").text("");
    $(this).removeClass("is-invalid");
});

$(".add-to-cart-btn").on('click', function(){
    var inputGroup = $(this).parents(":eq(1)").find(".input-group");
    var input = inputGroup.find("input");
    var quantity = input.val();
    var phoneId = $(this).data('id');

    console.log(phoneId, quantity);
    addToCard(phoneId, quantity, function(result){
        console.log(result);
        if(result.message!=="success"){
            input.addClass("is-invalid");
            inputGroup.find(".invalid-tooltip").text(result.message);
        } else {
            updateCart(result.cartQuantity, result.cartBill);
        }
    });
});

$("#pagination").each(function () {
    var limit = $(this).data('limit') !== "" ? $(this).data('limit') : 10;
    var amount = $(this).data('amount');
    var page = $(this).data('page');
    $(this).twbsPagination({
            initiateStartPageClick: false,
            totalPages: Math.ceil(amount/limit),
            startPage: page,
            visiblePages: 5,
            onPageClick: function(event, page){
                var link = PAGE_URL
                    +"?page=" + page
                    + ($(this).data('limit')!=="" ? '&limit='+$(this).data('limit') : '')
                    + ($(this).data('sortby')!=="" ? '&sortBy='+$(this).data('sortby') : '')
                    + ($(this).data('search')!=="" ? '&search='+$(this).data('search') : '');
                window.location.replace(link);
            }
    })
});

function addToCard(phoneId, quantity, onSuccess){
    $.ajax({
        type: 'POST',
        contentType: 'application/json;charset=utf-8',
        url: "/phoneshop-web/ajaxCart?phoneId="+phoneId+"&quantity="+quantity,
        dataType: 'json',
        success: onSuccess
    });
}

function updateCart(cartQuantity, cartBill){
    $("#cart-amount").text(cartQuantity);
    $("#cart-price").text(cartBill);
}

