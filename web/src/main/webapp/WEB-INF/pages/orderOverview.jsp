<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%--
  Created by IntelliJ IDEA.
  User: sashayukhimchuk
  Date: 2019-07-25
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
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
    <title>OrderOverview</title>
</head>
<body>
<div>
    <div style="float: left;">

    </div>
    <div style="float: right;">
        <form action="/logout">
            <button class="btn btn-error">Logout</button>
        </form>
    </div>
</div>
<h2 align="Left">Thank you for order</h2>
<h4 align="left">Order number: ${order.id}</h4>
<table border="1px" align="center" frame="void">
    <thead>
    <tr>
        <td>Brand
        </td>
        <td>Model
        </td>
        <td>Colors</td>
        <td>Display Size
        </td>
        <td>Quantity</td>
        <td>Price
        </td>
    </tr>
    </thead>
    <c:forEach var="orderItem" items="${order.orderItems}">
        <tr>
            <td>${orderItem.phone.brand}</td>
            <td><a href="/productDetails/${orderItem.phone.id}">${orderItem.phone.model}</a></td>
            <td>
                <c:forEach var="color" items="${orderItem.phone.colors}" varStatus="i">
                    <c:choose>
                        <c:when test="${(fn:length(orderItem.phone.colors) - i.count) gt 0}">
                            ${color.code},
                        </c:when>
                        <c:otherwise>
                            ${color.code}
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </td>
            <td>${orderItem.phone.displaySizeInches}"</td>
            <td>${orderItem.quantity}</td>
            <td>${orderItem.phone.price}$</td>
        </tr>
    </c:forEach>
    <tr>
        <td style="visibility: hidden;"></td>
        <td style="visibility: hidden;"></td>
        <td style="visibility: hidden;"></td>
        <td style="visibility: hidden;"></td>
        <td>Subtotal</td>
        <td>${order.subtotal}$</td>
    </tr>
    <tr>
        <td style="visibility: hidden;"></td>
        <td style="visibility: hidden;"></td>
        <td style="visibility: hidden;"></td>
        <td style="visibility: hidden;"></td>
        <td>Delivery</td>
        <td>${order.totalPrice - order.subtotal}$</td>
    </tr>
    <tr>
        <td style="visibility: hidden;"></td>
        <td style="visibility: hidden;"></td>
        <td style="visibility: hidden;"></td>
        <td style="visibility: hidden;"></td>
        <td>TOTAL</td>
        <td>${order.totalPrice}$</td>
    </tr>
</table>
<table border="0" width="90%">
    <tr>
        <td align="left" width="20%">First name:</td>
        <td align="left" width="40%">${order.firstName}</td>
    </tr>
    <tr>
        <td>Last name:</td>
        <td>${order.lastName}</td>
    </tr>
    <tr>
        <td>Address:</td>
        <td>${order.deliveryAddress}</td>
    </tr>
    <tr>
        <td>Phone:</td>
        <td>${order.contactPhoneNo}</td>
    </tr>
    <tr>
        <td>${order.additionalInformation}</td>
    </tr>
    <tr>
        <td>
            <form action="/productList">
                <button class="btn btn-success" type="submit">Back to shopping</button>
            </form>
        </td>
    </tr>
</table>
</body>
</html>
