<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/templates" %>
<template:page ordersTabIsActive="${true}">
    <p>
        Found <c:out value="${ordersPage.count}"/> orders.
    </p>
    <c:if test="${ordersPage.count > 0}">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Number</th>
                <th>Customer</th>
                <th>Phone</th>
                <th>Address</th>
                <th>Total price</th>
                <th>Status</th>
            </tr>
            </thead>
            <c:forEach var="order" items="${ordersPage.orderList}">
                <tr>
                    <td>
                        <a href="<c:url value="/admin/orders/${order.id}"/>">
                            <c:out value="${order.id}"/>
                        </a>
                    </td>
                    <td><c:out value="${order.firstName}"/> <c:out value="${order.lastName}"/></td>
                    <td><c:out value="${order.contactPhoneNo}"/></td>
                    <td><c:out value="${order.deliveryAddress}"/></td>
                    <td><c:out value="${order.totalPrice}"/></td>
                    <td><c:out value="${order.status}"/></td>
                </tr>
            </c:forEach>
        </table>
        <template:pagination targetPage="admin/orders" pagination="${ordersPage.pagination}"/>
    </c:if>
</template:page>