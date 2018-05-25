<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<template:page>
    <c:choose>
        <c:when test="${order ne null and order.orderItems.size() ne 0}">
            <div class="container">
                <div class="row">
                    <div class="col-md-auto">
                        <div class="display-4">Order Overview</div>
                    </div>
                    <div class="col-md-auto">
                        <div class="container" style="padding-top: 10px">
                            <a href="<c:url value="/productList"/>">
                                <button class="btn btn-dark btn-lg">Back to Shopping</button>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="jumbotron">
                <div class="lead">
                    <div class="row">
                        <div class="col-6">
                            <p>First Name</p>
                            <p>Last Name</p>
                            <p>Contact Phone Number</p>
                            <p>Delivery Address</p>
                            <p>Additional Information</p>
                        </div>
                        <div class="col-6">
                            <p>${order.firstName}</p>
                            <p>${order.lastName}</p>
                            <p>${order.contactPhoneNo}</p>
                            <p>${order.deliveryAddress}</p>
                            <p>${order.additionalInfo}</p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="container">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>Image</th>
                            <th>Brand</th>
                            <th>Model</th>
                            <th>Color</th>
                            <th>Display</th>
                            <th>Quantity</th>
                            <th>Price</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="orderItem" items="${order.orderItems}" varStatus="status">
                            <tr>
                                <c:set var="phone" value="${orderItem.phone}" />
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
                                <td>${orderItem.quantity}</td>
                                <td>${phone.price}$</td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td><p class="lead">Subtotal</p></td>
                            <td><p class="lead">${order.subtotal}$</p></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td><p class="lead">Delivery Price</p></td>
                            <td><p class="lead">${order.deliveryPrice}$</p></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td><p class="lead">Total Price</p></td>
                            <td><p class="lead">${order.totalPrice}$</p></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="container">
                <div class="jumbotron">
                    <p class="lead">Your cart is empty, click here to shop for phones</p>
                    <a href="<c:url value="/productList"/>">
                        <p class="lead">Go to main page</p>
                    </a>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</template:page>
