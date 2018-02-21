<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>${phone.model}</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="<c:url value="/resources/js/product_list.js"/>"></script>
        <script src="<c:url value="/resources/js/jquery.number.js"/>"></script>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-12 login">
                    <a href="#">Login</a>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <a href="${pageContext.request.contextPath}/productList"><img src="<c:url value="/resources/img/logo.jpg"/>"></a>
                </div>
                <div class="col">
                    <form method="GET" action="${pageContext.request.contextPath}/cart">
                        <button class="float-right cart" type="submit">My cart: <span id="count-items">${cartStatus.countItems}</span> items <span id="price">${cartStatus.price}</span>$</button>
                    </form>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <form method="GET" action="${pageContext.request.contextPath}/productList">
                        <button type="submit">Back to product list</button>
                    </form>
                    <br>
                    <span id="product_name" class="bold">${phone.model}</span>
                    <img id="product-img" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                    <br>
                    <span id="description">${phone.description}</span>
                    <table class="add-to-cart">
                        <tr>
                            <td>
                                <span class="bold">Price: </span>${phone.price}$
                                <br>
                                <div class="row">
                                    <div class="col">
                                        <input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
                                        <input id="quantity-${phone.id}-${phone.colors.toArray()[0].code}" type="text" value="1">
                                        <br>
                                        <span id="quantity-${phone.id}-${phone.colors.toArray()[0].code}-wrong-format" class="error"></span>
                                    </div>
                                    <div class="col">
                                        <button type="button" class="btn btn-default add-cart" onclick="addToCart(${phone.id}, '${phone.colors.toArray()[0].code}')">Add to cart</button>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="col param">
                    <span class="bold">Display</span>
                    <table class="param">
                        <tr>
                            <td>Size</td>
                            <td>${phone.displaySizeInches}''</td>
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
                    <span class="bold">Dimensions &amp; weight</span>
                    <table class="param">
                        <tr>
                            <td>Length</td>
                            <td>${phone.lengthMm}mm</td>
                        </tr>
                        <tr>
                            <td>Width</td>
                            <td>${phone.widthMm}mm</td>
                        </tr>
                        <tr>
                            <td>Color</td>
                            <td>${phone.colors.toArray()[0].code}</td>
                        </tr>
                        <tr>
                            <td>Weight</td>
                            <td>${phone.weightGr}</td>
                        </tr>
                    </table>
                    <span class="bold">Camera</span>
                    <table class="param">
                        <tr>
                            <td>Front</td>
                            <td>${phone.frontCameraMegapixels} megapixels</td>
                        </tr>
                        <tr>
                            <td>Back</td>
                            <td>${phone.backCameraMegapixels} megapixels</td>
                        </tr>
                    </table>
                    <span class="bold">Battery</span>
                    <table class="param">
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
                    <span class="bold">Other</span>
                    <table class="param">
                        <tr>
                            <td>Color</td>
                            <td>
                                <c:forEach var="color" items="${colors}">
                                    ${color.code}
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
        </div>
    </body>
</html>