<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
  <title>Order ${order.id}</title>
  <jsp:include page="header.jsp"/>
</head>
<body>
<h3>Order number: <c:out value="${order.id}"/></h3>
<h3>Order status: <c:out value="${order.status}"/></h3>
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
<form action="${contextUrl}admin/orders" style="display:inline">
<input type="submit" value="Back"/>
</form>
<form action="${contextUrl}admin/orders/${order.id}?setStatus=2" method="post" style="display:inline">
<sec:csrfInput/>
<input type="submit" value="Delivered"/>
</form>
<form action="${contextUrl}admin/orders/${order.id}?setStatus=3" method="post" style="display:inline">
<sec:csrfInput/>
<input type="submit" value="Rejected"/>
</form>
</body>
</html>