<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<template:page >
    <c:choose>
        <c:when test="${orders ne null and orders.size() ne 0}">
            <div class="container">
                <div class="row">
                    <div class="col-md-auto">
                        <div class="display-4">Orders</div>
                    </div>
            </div>
            <div class="lead">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>Order Id</th>
                        <th>Customer</th>
                        <th>Phone</th>
                        <th>Address</th>
                        <th>Total Price</th>
                        <td>Status</td>
                    </tr>
                    </thead>
                    <c:forEach var="order" items="${orders}">
                        <tr>
                            <td>
                                <a href="<c:url value="/admin/orders/${order.id}"/>">
                                   <c:out value="${order.id}"/>
                                </a>
                            </td>
                            <td><c:out value="${order.firstName}  ${order.lastName}" /></td>
                            <td><c:out value="${order.contactPhoneNo}" /></td>
                            <td><c:out value="${order.deliveryAddress}" /></td>
                            <td><c:out value="${order.totalPrice} $" /></td>
                            <td><c:out value="${order.status}" /></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </c:when>
        <c:otherwise>
            <div class="lead">
                <div class="jumbotron">
                    <h3>No orders found</h3>
                    <a href="<c:url value="/productList"/>">
                        <p>Go to main page</p>
                    </a>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</template:page>
