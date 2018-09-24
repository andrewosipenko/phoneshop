<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>Order</title>
  <c:url var="contextUrl" value = "/" />
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<h2>Thank you for your order!</h2>
<br>
<h3>Order number: <c:out value="${order.id}"/></h3>
<table border="1px">
    <thead>
      <tr>
        <td>Brand</td>
        <td>Model</td>
        <td>Colors</td>
        <td>Display size</td>
        <td>Quantity</td>
        <td>Price</td>
      </tr>
    </thead>
    <c:forEach var="orderItem" items="${order.orderItems}" varStatus="loop">
      <tr>
        <td>${orderItem.phone.brand}</td>
        <td>${orderItem.phone.model}</td>
        <td>
          <c:forEach var="color" items="${orderItem.phone.colors}">
            <c:out value="${color.code}  " />
          </c:forEach>
        </td>
        <td>${orderItem.phone.displaySizeInches} inches</td>
        <td>${orderItem.quantity}</td>
        <td>$ ${orderItem.phone.price}</td>
      </tr>
    </c:forEach>
</table>
Subtotal: <b>$ <c:out value="${order.subtotal}"/></b>
<br>
Delivery price: <b>$ <c:out value="${order.deliveryPrice}"/></b>
<br>
Total: <b>$ <c:out value="${order.totalPrice}"/></b>
<br>
<br>
First name: <c:out value="${order.firstName}"/>
<br>
<br>
Last name: <c:out value="${order.lastName}"/>
<br>
<br>
Adress: <c:out value="${order.deliveryAddress}"/>
<br>
<br>
Phone: <c:out value="${order.contactPhoneNo}"/>
<br>
<br>
Additional information: <c:out value="${order.additionalInfo}"/>
<br>
<br>
<a href="${contextUrl}productList">Back to shopping</a>
</body>
</html>