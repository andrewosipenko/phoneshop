<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<template:page>
    <c:choose>
        <c:when test="${order ne null and order.orderItems.size() ne 0}">
            <div class="lead">
                <div class="row">
                    <div class="col-md-auto">
                        <div class="lead" style="padding-top: 14px">
                            <h2>Order ID ${order.id}  Status :  ${order.status}
                            </h2>
                        </div>
                    </div>
                    <div class="col-md-auto">
                        <div class="container"style="padding-bottom: 10px; padding-top: 10px">
                            <a href="<c:url value="/admin/orders"/>">
                                <button class="btn btn-dark btn-lg" >Back to orders</button>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="lead">
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
            <div class="container">
                <div class="lead">
                    <div class="row">
                        <div class="col-6">
                            <p>First Name :   ${order.firstName}</p>
                            <p>Last Name :  ${order.lastName}</p>
                            <p>Contact Phone Number :  ${order.contactPhoneNo}</p>
                            <p>Delivery Address :  ${order.deliveryAddress}</p>
                            <p>Additional Information :  ${order.additionalInfo}</p>
                            <c:if test="${order.status eq 'NEW'}">
                                <div class="lead">
                                <form:form method="post">
                                    <button type="submit" name="orderStatus" value="DELIVERED" class="btn btn-success btn-lg">
                                        Delivered
                                    </button>
                                    <button type="submit" name="orderStatus" value="REJECTED" class="btn btn-danger btn-lg">
                                        Rejected
                                    </button>
                                </form:form>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="lead">
                <div class="jumbotron">
                    <p class="lead">No order found</p>
                    <a href="<c:url value="/admin/orders"/>">
                        <p class="lead">Go back to orders</p>
                    </a>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</template:page>
