function addToCart(id) {
    $.post("${pageContext.servletContext.contextPath}/ajaxCart",
        {
            phoneId: id,
            quantity: $("#phoneQuantity" + id).val()
        },
        function (data, status) {
            if (data.status === "Success") {
                $(".error-message").text("");
                $(".success-message").text("");
                $("#success-message" + id).html(data.message);
                $("#totalQuantity").html(data.totalQuantity);
                $("#totalCost").html(data.totalPrice)
            } else if (data.status === "Error") {
                $(".error-message").text("");
                $(".success-message").text("");
                $("#error-message" + id).html(data.message);
            }
        }
    )
}