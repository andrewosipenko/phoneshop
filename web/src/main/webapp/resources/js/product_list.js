$(function () {
    $("#price").number(true, 2);
});

function addToCart(id, color) {
    var quantity = $("#quantity-" + id + "-" + color).val();
    var contextPath = $("#contextPath").val();
    var url = contextPath + "/ajaxCart";

    $.ajax({
        url: url,
        type: "POST",
        dataType: "json",
        data: ({
            phoneId : id,
            color: color,
            quantity : quantity
        }),
        success: function (cartStatus) {
            $("#count-items").text(cartStatus.countItems);
            $("#price").number(cartStatus.price, 2);
            $("#quantity-" + id + "-" + color + "-wrong-format").text("");
        },
        statusCode: {
            400: function (data) {
                var error = JSON.parse(data.responseText);
                $("#quantity-" + id + "-" + color + "-wrong-format").text(error.errorMessage);
            }
        }
    });
}

function redirectToOldUrlWithNewParam(paramName, val) {
    var urlWithParams = new URL(window.location.href);
    urlWithParams.searchParams.set(paramName, val);
    location.href = urlWithParams;
}

function changeOrder(column) {
    var searchParams = new URLSearchParams(window.location.search);
    var orderBy = searchParams.get("order");

    if (orderBy !== null) {
        var columnInUrl = orderBy.split("_")[0];
        var order = orderBy.split("_")[1];

        if (columnInUrl === column && order === "asc") {
            return column + "_desc";
        }
    }

    return column + "_asc";
}