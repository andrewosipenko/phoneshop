<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tags:master pageTitle="orders" isShowCart="false">
    <div class="content">
        <h1>Orders</h1>
        <table class="table table-bordered cart-table">
            <thead>
            <tr>
                <td>Order number</td>
                <td>Customer</td>
                <td>Phone</td>
                <td>Address</td>
                <td>Date</td>
                <td>Total price</td>
                <td>Status</td>
            </tr>
            </thead>
            <c:forEach var="order" items="${orders}">
                <tr class="content-align">
                    <td><a href="${pageContext.servletContext.contextPath}/admin/orders/${order.id}">${order.id}</a></td>
                    <td>${order.firstName.concat(" ").concat(order.lastName)}</td>
                    <td>${order.contactPhoneNo}"</td>
                    <td>${order.deliveryAddress}</td>
                    <td>${order.orderDate}</td>
                    <td>${order.totalPrice}$</td>
                    <td>${order.status}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</tags:master>