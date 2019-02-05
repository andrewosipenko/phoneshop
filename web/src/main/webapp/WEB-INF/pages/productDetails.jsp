<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<template:page>
    <script> <%@ include file="/resources/js/addToCart.js" %> </script>
    <div class="container">
        <div class="row">
            <div class="col-sm-4">
                <a class ="btn" href="<c:url value="/productList"/>"><h4>Back to product list</h4></a>
                <c:if test="${message ne null}">
                    <h4>
                            ${message}
                    </h4>
                </c:if>
                <h3>${phone.model}</h3>
                <img width="60%" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                <p>
                    ${phone.description}
                </p>
                <div>
                    <h4>Price: ${phone.price} $ </h4>
                    <input class="text-input" id="quantity${phone.id}" value="1"><br>
                    <p class="text-danger" id="errorMessage${phone.id}"></p>
                    <input type="button" onclick="addToCart(${phone.id}, $('#quantity${phone.id}').val())" value="Add to">
                    <br>
                </div>
            </div>
            <div class="col-sm-8">
                <h4>Display</h4>
                <table class="table table-bordered">
                    <tbody>
                    <tr>
                        <th>Size</th>
                        <th>Resolution</th>
                        <th>Technology</th>
                        <th>Pixel density</th>
                    </tr>
                    <tr>
                        <td>${phone.displaySizeInches}</td>
                        <td>${phone.displayResolution}</td>
                        <td>${phone.displayTechnology}</td>
                        <td>${phone.pixelDensity}</td>
                    </tr>
                    </tbody>
                </table>
                <h4>Dimensions & weight</h4>
                <table class="table table-bordered">
                    <tbody>
                    <tr>
                        <th>Length</th>
                        <th>Width</th>
                        <th>Weight</th>
                    </tr>
                    <tr>
                        <td>${phone.lengthMm}mm</td>
                        <td>${phone.widthMm}mm</td>
                        <td>${phone.weightGr}</td>
                    </tr>
                    </tbody>
                </table>
                <h4>Camera</h4>
                <table class="table table-bordered">
                    <tbody>
                    <tr>
                        <th>Front</th>
                        <th>Back</th>
                    </tr>
                    <tr>
                        <td>${phone.frontCameraMegapixels} megapixels</td>
                        <td>${phone.backCameraMegapixels} megaixels</td>
                    </tr>
                    </tbody>
                </table>
                <h4>Battery</h4>
                <table class="table table-bordered">
                    <tbody>
                    <tr>
                        <th>Talk time</th>
                        <th>Stand by time</th>
                        <th>Battery capacity</th>
                    </tr>
                    <tr>
                        <td>${phone.talkTimeHours} hours</td>
                        <td>${phone.standByTimeHours} hours</td>
                        <td>${phone.batteryCapacityMah}mAh</td>
                    </tr>
                    </tbody>
                </table>
                <h4>Other</h4>
                <table class="table table-bordered">
                    <tbody>
                    <tr>
                        <th>Colors</th>
                        <th>Device type</th>
                        <th>Bluetooth</th>
                    </tr>
                    <tr>
                        <td>
                            <c:forEach var="color" items="${phone.colors}" varStatus="counter">
                                ${color.code}
                                <c:if test="${counter.count != phone.colors.size()}">
                                    ,
                                </c:if>
                            </c:forEach>
                        </td>
                        <td>${phone.deviceType}</td>
                        <td>${phone.bluetooth}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="col-sm-12">
                <c:choose>
                    <c:when test="${not empty comments}">
                        <table class="table table-striped">
                            <tbody>
                            <tr>
                                <th>Name</th>
                                <th>Rating</th>
                                <th>Comment</th>
                            </tr>
                            <c:forEach var="comment" items="${comments}">
                            <tr>
                                <td>${comment.authorName}</td>
                                <td>${comment.rating}</td>
                                <td>${comment.commentText}</td>
                            </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <h3>There is no comments!</h3>
                    </c:otherwise>
                </c:choose>
                <form:form method="post" modelAttribute="commentForm">
                    <table>
                        <tr>
                            <td>Author name*</td>
                            <td><form:input path="authorName"/></td>
                            <td><form:errors cssClass="text-danger" path="authorName"/></td>
                        </tr>
                        <tr>
                            <td>Rating*</td>
                            <td><form:input path="rating"/></td>
                            <td><form:errors cssClass="text-danger" path="rating"/></td>
                        </tr>
                        <tr>
                            <td>Text of comment:</td>
                            <td><form:textarea path="commentText"/></td>
                            <td><form:errors cssClass="text-danger" path="commentText"/></td>
                        </tr>
                    </table>
                    <input type="submit" value="Send" style="width: 100px;">
                </form:form>
            </div>
        </div>
    </div>
</template:page>
