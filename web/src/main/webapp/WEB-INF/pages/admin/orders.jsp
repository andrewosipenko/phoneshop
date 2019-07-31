<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: sashayukhimchuk
  Date: 2019-07-28
  Time: 12:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Orders</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
            integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
</head>
<body>
<h1 align="left">Orders</h1>
<table border="1px">
    <thead>
    <tr>
        <td>Order Number</td>
        <td>Customer</td>
        <td>Phone</td>
        <td>Address</td>
        <td>Date</td>
        <td>Total price</td>
        <td>Status</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${orders}" var="order">
        <tr>
            <td>
                <a href="/admin/orders/${order.id}">${order.id}</a>
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
                    ${order.localDateTime}
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
</body>
</html>
