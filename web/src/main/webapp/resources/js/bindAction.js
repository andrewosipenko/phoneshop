$(document).ready(function () {
    $('.buttonAddCartItem').bind('click', function(e){
        ajaxAddCartItem($(e.target).val());
    });
});