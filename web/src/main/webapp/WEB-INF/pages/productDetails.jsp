<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<template:page >
    <script> <%@ include file="js/addToCart.js" %> </script>
    <c:choose>
        <c:when test="${phone ne null}">
            <div class="jumbotron" style="background:transparent !important">
                <div class="row">
                    <div class="col">
                        <div class="container">
                            <div class="d-flex align-content-between flex-wrap">
                                <a href="<c:url value="/productList"/>">
                                    <button class="btn btn-secondary btn-lg">Back to Product List</button>
                                </a>
                            </div>
                        </div>
                        <div class="jumbotron" style="background:transparent !important">
                            <div class="display-4">
                                    <c:out value="${phone.model}"/>
                            </div>
                            <hr class="my-4">
                            <div class="d-flex justify-content-center">
                                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}"/> </div>
                            </div>
                        <div class="lead">
                            <c:out value="${phone.description}"/>
                        </div>
                        <div class="container">
                            <div class="jumbotron" style="background:transparent !important">
                                <div class="lead">
                                    <h4>Price :<c:out value=" ${phone.price} "/>$</h4>
                                </div>
                                <form:form modelAttribute="cartItem" id="addToCart${phone.id}">
                                    <div class="lead">
                                        <div class="row">
                                            <div class="col">
                                                <input type="hidden" name="phoneId" value="${phone.id}"/>
                                                <form:input path="quantity" cssClass="form-control" id="phone-${phone.id}-quantity" />
                                                <div id="error-message-${phone.id}" style="color: red; font-size: small"></div>
                                            </div>
                                            <div class="col">
                                                <button type="button" onclick="addToCart(${phone.id})"
                                                        class="btn btn-success">Add To Cart</button>
                                            </div>
                                        </div>
                                    </div>
                                </form:form>
                        </div>
                        </div>
                    </div>
                    <div class="col">
                        <div class="container">
                            <table class="table table-hover">
                                <tbody>
                                <p class="lead">Display</p>
                                <c:if test="${phone.displaySizeInches ne null}">
                                    <tr>
                                        <td>Size</td>
                                        <td><c:out value = "${phone.displaySizeInches}"/>"</td>
                                    </tr>
                                </c:if>
                                <c:if test="${phone.displayResolution ne null}">
                                    <tr>
                                        <td>Resolution</td>
                                        <td><c:out value = "${phone.displayTechnology}"/></td>
                                    </tr>
                                </c:if>
                                <c:if test="${phone.displayTechnology ne null}">
                                    <tr>
                                        <td>Technology</td>
                                        <td><c:out value = "${phone.displayTechnology}"/></td>
                                    </tr>
                                </c:if>
                                <c:if test="${phone.pixelDensity ne null}">
                                    <tr>
                                        <td>Pixel Density</td>
                                        <td><c:out value = "${phone.pixelDensity}"/></td>
                                    </tr>
                                </c:if>
                                </tbody>
                            </table>
                        </div>
                        <div class="container">
                            <table class="table table-hover">
                                <tbody>
                                <p class="lead">Dimensions & weight</p>
                                <c:if test="${phone.lengthMm ne null}">
                                    <tr>
                                        <td>Length</td>
                                        <td><c:out value = "${phone.lengthMm}"/>mm</td>
                                    </tr>
                                </c:if>
                                <c:if test="${phone.widthMm ne null}">
                                    <tr>
                                        <td>Width</td>
                                        <td><c:out value = "${phone.widthMm}"/>mm</td>
                                    </tr>
                                </c:if>
                                <c:if test="${phone.weightGr ne null}">
                                    <tr>
                                        <td>Weight</td>
                                        <td><c:out value = "${phone.weightGr}"/>gr</td>
                                    </tr>
                                </c:if>
                                </tbody>
                            </table>
                        </div>
                        <div class="container">
                            <table class="table table-hover">
                                <tbody>
                                <p class="lead">Camera</p>
                                <c:if test="${phone.frontCameraMegapixels ne null}">
                                    <tr>
                                        <td>Front</td>
                                        <td><c:out value = "${phone.frontCameraMegapixels} "/>megapixels</td>
                                    </tr>
                                </c:if>
                                <c:if test="${phone.backCameraMegapixels ne null}">
                                    <tr>
                                        <td>Back</td>
                                        <td><c:out value = "${phone.backCameraMegapixels} "/>megapixels</td>
                                    </tr>
                                </c:if>
                                </tbody>
                            </table>
                        </div>
                        <div class="container">
                            <table class="table table-hover">
                                <tbody>
                                <p class="lead">Battery</p>
                                <c:if test="${phone.talkTimeHours ne null}">
                                    <tr>
                                        <td>Talk time</td>
                                        <td><c:out value = "${phone.talkTimeHours} "/>hours</td>
                                    </tr>
                                </c:if>
                                <c:if test="${phone.standByTimeHours ne null}">
                                    <tr>
                                        <td>Stand by time</td>
                                        <td><c:out value = "${phone.standByTimeHours} "/>hours</td>
                                    </tr>
                                </c:if>
                                <c:if test="${phone.batteryCapacityMah ne null}">
                                    <tr>
                                        <td>Battery Capacity</td>
                                        <td><c:out value = "${phone.batteryCapacityMah}"/>mAh</td>
                                    </tr>
                                </c:if>
                                </tbody>
                            </table>
                        </div>
                        <div class="container">
                            <table class="table table-hover">
                                <tbody>
                                <p class="lead">Other</p>
                                <c:if test="${phone.colors ne null}">
                                    <tr>
                                        <td>Colors</td>
                                        <td>
                                            <c:forEach var="color" items="${phone.colors}">
                                                <c:out value = "${color.code} "/>
                                            </c:forEach>
                                        </td>
                                    </tr>
                                </c:if>
                                <c:if test="${phone.deviceType ne null}">
                                    <tr>
                                        <td>Device Type</td>
                                        <td><c:out value = "${phone.deviceType}"/></td>
                                    </tr>
                                </c:if><c:if test="${phone.bluetooth ne null}">
                                    <tr>
                                        <td>Bluetooth</td>
                                        <td><c:out value = "${phone.bluetooth}"/></td>
                                    </tr>
                                </c:if>
                                </tbody>
                            </table>
                        </div>
                </div>
            </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="container">
                <div class="jumbotron">
                    <p class="lead">No details found</p>
                    <a href="<c:url value="/productList"/>">
                        <p class="lead">Go to main page</p>
                    </a>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</template:page>