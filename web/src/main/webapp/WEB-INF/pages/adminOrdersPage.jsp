<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<tags:page pageTitle="Admin Orders Page">

    <h3 style="margin: 10px">Orders</h3><br>

    <c:choose>
        <c:when test="${orders == null || orders.size() <= 0}">
            <p><em>No orders found</em></p>
        </c:when>
        <c:otherwise>
            <br><br>
            <table class="table table-striped table-bordered table-sm" style="text-align: center">
                <thead>
                <tr>
                    <td style="width: 20%">Order number</td>
                    <td style="width: 13%">Customer</td>
                    <td style="width: 14%">Phone</td>
                    <td style="width: 10%">Address</td>
                    <td style="width: 10%">Date</td>
                    <td style="width: 10%">Total price</td>
                    <td style="width: 10%">Status</td>
                </tr>
                </thead>
                <c:forEach var="order" items="${orders}">
                    <tr>
                        <td><a href="<c:url value="/admin/orders/${order.id}"/>">${order.id}</a></td>
                        <td>${order.firstName} ${order.lastName}</td>
                        <td>${order.contactPhoneNo}</td>
                        <td>${order.deliveryAddress}</td>
                        <td>${order.orderCreationDate}</td>
                        <td>${order.totalPrice}$</td>
                        <td>${order.status}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
</tags:page>
