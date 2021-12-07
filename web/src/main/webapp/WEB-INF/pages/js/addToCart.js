function addToCart(id) {
    $.post("${pageContext.servletContext.contextPath}/ajaxCart",
        {
            phoneId: id,
            quantity: $("#phoneQuantity").val()
        },
        function (data, status) {
            if (data.status === "Success") {
                $("#success-message").html(data.message);
                $("#error-message").html("");
                console.log(data.totalQuantity);

                $("#totalQuantity").html(data.totalQuantity);
                $("#totalCost").html(data.totalPrice)
            } else if (data.status === "Error") {
                $("#success-message").html("");
                $("#error-message").html(data.message);
            }
        }
    )
}