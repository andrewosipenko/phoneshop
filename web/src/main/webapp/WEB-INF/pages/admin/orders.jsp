<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true"%>

<html>
<head>
  <title>Orders</title>
  <%@ include file = "header.jsp" %>
</head>
<body>
<h2>Admin page</h2>
<table border="1px">
    <thead>
      <tr>
        <td>Order number</td>
        <td>Customer</td>
        <td>Phone</td>
        <td>Address</td>
        <td>Total price</td>
        <td>Status</td>
        <td>Date<td>
      </tr>
    </thead>
    <c:forEach var="order" items="${ordersList}" varStatus="loop">
      <tr>
        <td><a href="${contextUrl}admin/orders/${order.id}">${order.id}</a></td>
        <td>${order.firstName} ${order.lastName}</td>
        <td>${order.contactPhoneNo}</td>
        <td>${order.deliveryAddress}</td>
        <td>$ ${order.totalPrice}</td>
        <td>${order.status}</td>
        <td>${order.date}</td>
      </tr>
    </c:forEach>
</table>
<br>
<a href="${contextUrl}productList">Leave admin area</a>
</body>
</html>