<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Product Details</title>
    <jsp:useBean id="phone" class="com.es.core.model.entity.phone.Phone" scope="request"/>
</head>


<tags:master pageTitle="Product details">
    <body>
    <nav class="navbar navbar-light bg-light">
        <a href="${pageContext.request.contextPath}/productList"
           class="ref-btn btn btn-secondary justify-content-lg-start">
            Back to Product List
        </a>
    </nav>
    <p></p>
    <div class="row">
        <div class="col-6">
            <div class="card text-center col-10 offset-3" style="width: 36rem;">
                <div class="card-header">
                    <h5 class="card-title">${phone.model}</h5>
                </div>
                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}"
                     class="card-img-top" alt="...">
                <div class="card-body">
                    <p class="card-text">${phone.description}</p>
                    <p>
                        Quantity:
                        <input id="quantity-${phone.id}"
                               class="quantityInput quantity"
                               type="text"
                               size="5"
                               name="quantity"
                               value="1"/>
                    </p>
                    <a class="btn btn-outline-success my-2 my-sm-0" onclick="addPhoneToCart(${phone.id})" style="">Add
                        to cart</a>
                    <p></p>
                    <div id="quantityInputMessage-${phone.id}">
                    </div>
                </div>
                <div class="card-footer">
                        ${phone.price} $
                </div>
            </div>
        </div>
        <div class="col-6">
            <div>
                <h5 class="table-label">
                    Display
                </h5>
                <table class="phone-details-table table table-striped col-10 offset-1">
                    <tbody>
                    <tr>
                        <th>Size</th>
                        <td>${phone.displaySizeInches}</td>
                    </tr>
                    <tr>
                        <th>Resolution</th>
                        <td>${phone.displayResolution}</td>
                    </tr>
                    <tr>
                        <th>Technology</th>
                        <td>${phone.displayTechnology}</td>
                    </tr>
                    <tr>
                        <th>Pixel density</th>
                        <td>${phone.pixelDensity}</td>
                    </tr>

                    </tbody>
                </table>
            </div>
            <div>
                <h5 class="table-label">
                    Dimensions & Weight
                </h5>
                <table class="table table-striped col-10 offset-1">
                    <tbody>
                    <tr>
                        <th>Length</th>
                        <td>${phone.lengthMm}</td>
                    </tr>
                    <tr>
                        <th>Width</th>
                        <td>${phone.widthMm}</td>
                    </tr>
                    <tr>
                        <th>Color</th>
                        <td>
                            <c:forEach var="color" items="${phone.colors}" end="0">
                                <c:out value="${color.code}"/>
                            </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <th>Weight</th>
                        <td>${phone.weightGr}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div>
                <h5 class="table-label">
                    Camera
                </h5>
                <table class="table table-striped col-10 offset-1">
                    <tbody>
                    <tr>
                        <th>Front</th>
                        <td>${phone.frontCameraMegapixels}</td>
                    </tr>
                    <tr>
                        <th>Back</th>
                        <td>${phone.backCameraMegapixels}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div>
                <h5 class="table-label">
                    Battery
                </h5>
                <table class="table table-striped col-10 offset-1">
                    <tbody>
                    <tr>
                        <th>Talk time</th>
                        <td>${phone.talkTimeHours}</td>
                    </tr>
                    <tr>
                        <th>Stand by time</th>
                        <td>${phone.standByTimeHours}</td>
                    </tr>
                    <tr>
                        <th>Battery capacity</th>
                        <td>
                                ${phone.batteryCapacityMah}
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div>
                <h5 class="table-label">
                    Other
                </h5>
                <table class="table table-striped col-10 offset-1">
                    <tbody>
                    <tr>
                        <th>Colors</th>
                        <td>
                            <c:forEach var="color" items="${phone.colors}" varStatus="statusColors">
                                <c:out value="${color.code}"/>
                                <c:if test="${not statusColors.last}">
                                    <c:out value=","/>
                                </c:if>
                            </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <th>Device type</th>
                        <td>${phone.deviceType}</td>
                    </tr>
                    <tr>
                        <th>Bluetooth</th>
                        <td>
                                ${phone.bluetooth}
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    </body>
    </html>
</tags:master>