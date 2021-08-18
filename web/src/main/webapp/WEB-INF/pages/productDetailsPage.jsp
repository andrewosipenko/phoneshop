<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:page pageTitle="Product Details Page">
    <c:url value="/static/js/addToCart.js" var="addToCart"/>
    <script src="${addToCart}"></script>

    <a href="${pageContext.request.contextPath}/productList" style="margin-left: 5px" class="btn btn-secondary btn-lg"
       role="button">
        <h5>Back to product list</h5>
    </a>

    <c:choose>
        <c:when test="${phone == null}">
            <p><em>Nothing found</em></p>
        </c:when>
        <c:otherwise>
            <c:set var="phoneId" value="${phone.id}"/>
            <br><br>
            <table class=" table table-striped table-bordered table-hover table-sm" style="text-align: center">
                <tr>
                    <td style="width: 60%">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item"><h4>${phone.model}</h4></li>
                            <li class="list-group-item">
                                <img style="max-height: 200px;"
                                     src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                            </li>
                            <li class="list-group-item">${phone.description}</li>
                            <li class="list-group-item">Price: ${phone.price}$</li>
                            <li class="list-group-item">
                                <div class="input-group mb-3">
                                    <input type="text" class="form-control" placeholder="Quantity" value="1"
                                           id="quantity-${phoneId}">
                                    <div class="input-group-append">
                                        <button class="btn btn-outline-secondary" type="button"
                                                onclick="addToCart(${phoneId}, '${pageContext.request.contextPath}/ajaxCart')">
                                            Add to cart
                                        </button>
                                    </div>
                                </div>
                                <span id="quantity-message-${phoneId}" style="display: none"></span>
                            </li>
                        </ul>
                    </td>
                    <td style="width: 40%">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">
                                <h4>Display</h4>
                                <table class="table table-bordered" style="text-align: left">
                                    <tbody>
                                    <tr>
                                        <th scope="row">Size</th>
                                        <td>${phone.displaySizeInches}"</td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Resolution</th>
                                        <td>${phone.displayResolution}</td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Technology</th>
                                        <td>${phone.displayTechnology}</td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Pixel density</th>
                                        <td>${phone.pixelDensity}</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </li>
                            <li class="list-group-item">
                                <h4>Dimensions & Weight</h4>
                                <table class="table table-bordered" style="text-align: left">
                                    <tbody>
                                    <tr>
                                        <th scope="row">Length</th>
                                        <td>${phone.lengthMm}mm</td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Width</th>
                                        <td>${phone.widthMm}mm</td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Color</th>
                                        <td>
                                            <c:forEach var="color" items="${phone.colors}" varStatus="status">
                                                <c:out value="${color.code}"/>
                                                <c:if test="${not status.last}">, </c:if>
                                            </c:forEach>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Weight</th>
                                        <td>${phone.weightGr}</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </li>
                            <li class="list-group-item">
                                <h4>Camera</h4>
                                <table class="table table-bordered" style="text-align: left">
                                    <tbody>
                                    <tr>
                                        <th scope="row">Front</th>
                                        <td>${phone.frontCameraMegapixels} megapixels</td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Back</th>
                                        <td>${phone.backCameraMegapixels} megapixels</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </li>
                            <li class="list-group-item">
                                <h4>Battery</h4>
                                <table class="table table-bordered" style="text-align: left">
                                    <tbody>
                                    <tr>
                                        <th scope="row">Talk time</th>
                                        <td>${phone.talkTimeHours} hours</td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Stand by time</th>
                                        <td>${phone.standByTimeHours} hours</td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Battery capacity</th>
                                        <td>${phone.batteryCapacityMah} mAh</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </li>
                            <li class="list-group-item">
                                <h4>Other</h4>
                                <table class="table table-bordered" style="text-align: left">
                                    <tbody>
                                    <tr>
                                        <th scope="row">RAM</th>
                                        <td>${phone.ramGb}Gb</td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Internal Storage</th>
                                        <td>${phone.internalStorageGb}Gb</td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Bluetooth</th>
                                        <td>${phone.bluetooth}</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </li>
                        </ul>
                    </td>
                </tr>
            </table>
        </c:otherwise>
    </c:choose>
</tags:page>