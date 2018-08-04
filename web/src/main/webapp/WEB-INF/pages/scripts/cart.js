var updateCart = function() {
    $('#updateCartForm').submit();
};
var deleteFromCart = function (phoneId) {
    $('#deleteFromCartPhoneId').val(phoneId);
    $('#deleteFromCartForm').submit();
};