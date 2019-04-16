<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<template:page>
    <div class="container">
        <h4>Admin orders</h4>
        <c:if test="${orderStatusMessage ne null}">
            <h4>
                    ${orderStatusMessage}
            </h4>
        </c:if>
        <c:choose>
            <c:when test="${not empty orders}">
                <table class="table table-striped">
                    <thead>
                    <th>Order number</th>
                    <th>Customer</th>
                    <th>Phone</th>
                    <th>Address</th>
                    <th>Total price</th>
                    <th>Status</th>
                    </thead>
                    <tbody>
                    <c:forEach var="order" items="${orders}">
                        <tr>
                            <td>
                                <a href="<c:url value="/admin/orders/${order.id}"/>">${order.id}</a>
                            </td>
                            <td>
                                ${order.firstName} ${order.lastName}
                            </td>
                            <td>
                                ${order.contactPhoneNo}
                            </td>
                            <td>
                                ${order.deliveryAddress}
                            </td>
                            <td>
                                ${order.totalPrice}
                            </td>
                            <td>
                                ${order.status}
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <p>
                    There are no orders!
                </p>
            </c:otherwise>
        </c:choose>
    </div>
</template:page>