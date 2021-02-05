<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>

<header>
    <form class="cartInfo" action="${pageContext.servletContext.contextPath}/cart">
        <p>
            <input type="submit" id="cartQuantity" class="btn btn-outline-danger"
            value="Total quantity : ${cart.totalQuantity} items">
        </p>
    </form>
</header>
<form class="cartInfo" action="${pageContext.servletContext.contextPath}/cart">
    <input class="btn btn-outline-danger" type="submit" id="cartValue" value="Cart : ${cart.totalCost}">
</form>
<body>
<div class="d-flex flex-row">
    <div style="margin-left: 15%">
        <table class="table table-bordered">
            <thead class="thead-light">
            <tr>
                <th scope="col">Image and description</th>
            </thead>
            <tr class="productItem">
                <td>
                    <div class="card" style="width: 30rem;">
                        <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}"
                             class="card-img-top">
                        <div class="card-body">
                            <h5 class="card-title">Description</h5>
                            <p class="card-text">${phone.description}</p>
                            <input type="text" name="quantity" id="${phone.id}" value="${quantity}">
                            <input class="btn btn-primary" type="submit"
                                   onclick="addPhoneAjax(${phone.id}, $('#${phone.id}').val())" value="Add"
                                   class="add"/>
                            <div>
                                <a readonly name="error-message" class="error-message&${phone.id}"
                                   style="color: crimson"/>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
        </table>
    </div>


<div class="d-flex flex-column" style="width: 50rem;">

    <div style="margin-left: 20%; margin-bottom: 3%;">
        <table class="table table-bordered">
            <thead class="thead-light">Display
            <tr>
                <th scope="col">Size</th>
                <th scope="col">Pixel density</th>
                <th scope="col">Resolution</th>
                <th scope="col">Technology</th>
            </tr>
            </thead>
            <td>
                <p class="card-text">${phone.displaySizeInches} "</p>
            </td>
            <td>
                <p class="card-text">${phone.displayResolution}</p>
            </td>
            <td>
                <p class="card-text">${phone.displayTechnology}</p>
            </td>
            <td>
                <p class="card-text">${phone.pixelDensity} px </p>
            </td>
            </tr>
        </table>
    </div>

    <div style="margin-left: 20%;">
        <table class="table table-bordered">
            <thead class="thead-light">Battery
            <tr>
                <th scope="col">Talk time</th>
                <th scope="col">Stand by time</th>
                <th scope="col">Battery capacity</th>
            </tr>
            </thead>
            <td>
                <p class="card-text">${phone.talkTimeHours} h </p>
            </td>
            <td>
                <p class="card-text">${phone.standByTimeHours} h </p>
            </td>
            <td>
                <p class="card-text">${phone.batteryCapacityMah} mAh </p>
            </td>
            </tr>
        </table>
    </div>

    <div style="margin-left: 20%; margin-bottom: 3%;">
        <table class="table table-bordered">
            <thead class="thead-light">Dimension & Weight
            <tr>
                <th scope="col">Length</th>
                <th scope="col">Width</th>
                <th scope="col">Colors</th>
                <th scope="col">Weight</th>
            </tr>
            </thead>
            <td>
                <p class="card-text">${phone.lengthMm} mm</p>
            </td>
            <td>
                <p class="card-text">${phone.widthMm} mm</p>
            </td>
            <td>
                <p class="card-text">
                    <c:forEach items="${phone.colors}" var="colorItem">
                <p>
                        ${colorItem.code}
                </p>
                </c:forEach>
                </p>
            </td>
            <td>
                <p class="card-text">${phone.weightGr} gr</p>
            </td>
            </tr>
        </table>
    </div>

    <div style="margin-left: 20%; margin-bottom: 3%;">
        <table class="table table-bordered">
            <thead class="thead-light">Camera
            <tr>
                <th scope="col">Back camera</th>
                <th scope="col">Front camera</th>
            </tr>
            </thead>
            <td>
                <p class="card-text">${phone.backCameraMegapixels} MP </p>
            </td>
            <td>
                <p class="card-text">${phone.frontCameraMegapixels} MP </p>
            </td>
            </tr>
        </table>
    </div>

</div>
</div>

</body>
</html>

<script>
    function addPhoneAjax(phoneId, quantity, errorMsg) {
        var data = {};
        data["totalCost"] = ${sessionScope.get("cart").totalCost};
        data["totalQuantity"] = ${sessionScope.get("cart").totalQuantity};
        data["quantityToAdd"] = quantity;
        data["productId"] = phoneId;
        data["msg"] = errorMsg;
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "${pageContext.servletContext.contextPath}/addToCart",
            data: JSON.stringify(data),
            dataType: 'json',
            success: function (data) {
                var allMessages = document.getElementsByName('error-message');
                allMessages.forEach(function (messageItem) {
                    messageItem.innerHTML = "";
                });
                $('#cartQuantity').val('Cart :' + data.totalCost + " $ ");
                $('#cartValue').val('Items :' + data.totalQuantity + " in order ");
            },
            error: function (res) {
                var tbody = document.getElementsByClassName('error-message&' + phoneId)[0];
                if (res.responseText === "")
                    res.responseText = "No letters in quantity are allowed";
                tbody.innerHTML = res.responseText;
                document.createElement('div').innerHTML = tbody.innerHTML;
            }
        });
    }
</script>
