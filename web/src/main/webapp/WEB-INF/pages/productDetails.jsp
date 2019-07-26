<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<%--
  Created by IntelliJ IDEA.
  User: sashayukhimchuk
  Date: 2019-07-23
  Time: 09:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <title>Title</title>
</head>
<body>
<div>
<div style="float: right;">
    <form action="/cart">
        <button style="margin-left: 10px" class="btn btn-primary">My cart: ${cart.totalCount} items ${cart.totalPrice}$</button>
    </form>
</div>
</div>
<p></p>
<div>
    <div style="float: left;">
        <form action="/productList/1">
            <input type="submit" class="btn btn-info" value="Back to product list"/>
        </form>
    </div>
    <div style="float: right;">

    </div>
</div>
    <h2 align="center">${phone.model}</h2>
<div>
    <div style="float: left; width: 50%;">
    <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}"/>
        ${phone.description}
    <div style="border:1px solid black; width: 32%">
        <h3 align="left"><b>Price:</b> ${phone.price}$</h3>
        <form>
            <input type="text" id="quantity"/>
            <button type="button" class="btn btn-primary" onclick="addToCart_ajax(${phone.id});">Add to cart</button>
            <span id="response"></span>
        </form>
    </div>
    </div>
    <div style="float: right;">
        <b>Display</b>
        <table border="1px">
            <tr>
                <td>Size</td>
                <td>${phone.displaySizeInches}"</td>
            </tr>
            <tr>
                <td>Resolution</td>
                <td>${phone.displayResolution}</td>
            </tr>
            <tr>
                <td>Technology</td>
                <td>${phone.displayTechnology}</td>
            </tr>
            <tr>
                <td>Pixel density</td>
                <td>${phone.pixelDensity}</td>
            </tr>
        </table>
        <b>Dimensions & weight</b>
        <table border="1px">
            <tr>
                <td>Length</td>
                <td>${phone.lengthMm}mm</td>
            </tr>
            <tr>
                <td>Width</td>
                <td>${phone.widthMm}</td>
            </tr>
            <tr>
                <td>Weight</td>
                <td>${phone.weightGr}gr</td>
            </tr>
        </table>
        <b>Camera</b>
        <table border="1px">
            <tr>
                <td>Front</td>
                <td>${phone.frontCameraMegapixels} megapixels</td>
            </tr>
            <tr>
                <td>Back</td>
                <td>${phone.backCameraMegapixels} megapixels</td>
            </tr>
        </table>
        <b>Battery</b>
        <table border="1px">
            <tr>
                <td>Talk time</td>
                <td>${phone.talkTimeHours} hours</td>
            </tr>
            <tr>
                <td>Stand by time</td>
                <td>${phone.standByTimeHours} hours</td>
            </tr>
            <tr>
                <td>Battery capacity</td>
                <td>${phone.batteryCapacityMah}mAh</td>
            </tr>
        </table>
        <b>Other</b>
        <table border="1px">
            <tr>
                <td>Colors</td>
                <td>
                <c:forEach var="color" items="${phone.colors}" varStatus="i">
                    <c:choose>
                        <c:when test="${(fn:length(phone.colors) - i.count) gt 0}">
                            ${color.code},
                        </c:when>
                        <c:otherwise>
                            ${color.code}
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </td>
            </tr>
            <tr>
                <td>Device type</td>
                <td>${phone.deviceType}</td>
            </tr>
            <tr>
                <td>Bluetooth</td>
                <td>${phone.bluetooth}</td>
            </tr>
        </table>
    </div>
</div>
<script type="text/javascript">
    function addToCart_ajax(phoneId) {
        let quantity = $('#quantity').val();
        let responseField = $('#response');
        $.ajax({
            type: "post",
            url: "${pageContext.request.contextPath}/ajaxCart/",
            data: {'phoneId': phoneId, 'quantity': quantity},
            success: function (response) {
                responseField.text(response);
            },
            error: function (xhr, status, error) {
                var errorMessage = xhr.status + ': ' + xhr.statusText
                alert('Error - ' + errorMessage);
            }
        });
    }
</script>
</body>
</html>
