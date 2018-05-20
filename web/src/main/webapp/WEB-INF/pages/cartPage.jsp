<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<template:page >
    <script> <%@include file="js/cartPage.js" %> </script>
    <c:choose>
        <c:when test="${cart ne null and cart.items.size() ne 0}">
            <div class="container">
                <div class="row">
                    <div class="col-md-auto">
                        <div class="display-4">Cart</div>
                            </div>
                    <div class="col-md-auto">
                        <div class="container" style="padding-top: 10px">
                            <a href="<c:url value="/productList"/>">
                                <button class="btn btn-dark btn-lg">Back to Product List</button>
                            </a>
                        </div>
                    </div>
                </div>
                    <div class="container">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>Image</th>
                                <th>Brand</th>
                                <th>Model</th>
                                <th>Color</th>
                                <th>Display</th>
                                <th>Price</th>
                                <th>Quantity</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            <form:form method="post" modelAttribute="cartPageInfo">
                                <input type="hidden" name="_method" value="PUT" id="method-put"/>
                                <input type="hidden" name="phoneId" id="phone-id">
                                <c:forEach var="cartItem" items="${cart.items}" varStatus="status">
                                    <tr>
                                        <c:set var="phone" value="${cartItem.phone}" />
                                        <td>
                                            <a href="<c:url value="/productDetails/${phone.id}"/>">
                                                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                                            </a>
                                        </td>
                                        <td>${phone.brand}</td>
                                        <td>${phone.model}</td>
                                        <td>
                                            <c:forEach var="color" items="${phone.colors}">
                                                <p><c:out value="${color.code}"/></p>
                                            </c:forEach>
                                        </td>
                                        <td>${phone.displaySizeInches}"</td>
                                        <td>${phone.price}$</td>
                                        <td>
                                            <form:hidden path="cartItemInfo[${status.index}].phoneId"/>
                                            <form:input path="cartItemInfo[${status.index}].quantity"/>
                                            <div style="color: red; font-size: small">
                                                <form:errors cssClass="errors" path="cartItemInfo[${status.index}].quantity"/>
                                            </div>
                                        </td>
                                        <td>
                                            <button type="submit" onclick="switchToPost(${phone.id})"
                                                    class="btn btn-secondary btn-lg">
                                                Delete
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <c:if test="${not empty cart.items}">
                                        <td>
                                            <button type="submit" class="btn btn-success">
                                                Update
                                            </button>
                                        </td>
                                        <td>
                                            <a href="<c:url value="/order"/>"  class="btn btn-primary">
                                                Order
                                            </a>
                                        </td>
                                    </c:if>
                                </tr>
                            </form:form>
                            </tbody>
                        </table>
                    </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="lead">
                <div class="jumbotron">
                    <p class="lead">Your cart is empty</p>
                    <a href="<c:url value="/productList"/>">
                        <p class="lead">Go to main page</p>
                    </a>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</template:page>