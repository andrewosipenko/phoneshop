<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form"
           prefix="springForm" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

<%--
  Created by IntelliJ IDEA.
  User: sashayukhimchuk
  Date: 2019-07-24
  Time: 19:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        .error {
            color: #ff0000;
            font-size: 50%;
        }
    </style>
</head>
<body>
<h2 align="center">Order</h2>
<form action="/cart">
    <button style="margin-left: 10px" class="btn btn-primary">Back to cart</button>
</form>
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
    <c:forEach var="entry" items="${phonesAndCount}">
        <tr>
            <td>${entry.key.brand}</td>
            <td><a href="/productDetails/${entry.key.id}">${entry.key.model}</a></td>
            <td>
                <c:forEach var="color" items="${entry.key.colors}" varStatus="i">
                    <c:choose>
                        <c:when test="${(fn:length(entry.key.colors) - i.count) gt 0}">
                            ${color.code},
                        </c:when>
                        <c:otherwise>
                            ${color.code}
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </td>
            <td>${entry.key.displaySizeInches}"</td>
            <td>${entry.value}</td>
            <td>${entry.key.price}$</td>
        </tr>
    </c:forEach>
    <tr>
        <td style="visibility: hidden;"></td>
        <td style="visibility: hidden;"></td>
        <td style="visibility: hidden;"></td>
        <td style="visibility: hidden;"></td>
        <td>Subtotal</td>
        <td>${cart.totalPrice}$</td>
    </tr>
    <tr>
        <td style="visibility: hidden;"></td>
        <td style="visibility: hidden;"></td>
        <td style="visibility: hidden;"></td>
        <td style="visibility: hidden;"></td>
        <td>Delivery</td>
        <td><spring:eval expression="@propertyConfigurer.getProperty('delivery.price')"/>$</td>
    </tr>
    <tr>
        <td style="visibility: hidden;"></td>
        <td style="visibility: hidden;"></td>
        <td style="visibility: hidden;"></td>
        <td style="visibility: hidden;"></td>
        <td>TOTAL</td>
        <td>${total}$</td>
    </tr>
</table>
<table border="0" width="90%">
    <form:form action="/order" commandName="order">
        <tr>
            <td align="left" width="20%">First name:</td>
            <td align="left" width="40%"><form:input path="firstName" size="30"/></td>
        </tr>
        <tr>
            <td></td>
            <td align="left"><form:errors path="firstName" cssClass="error"/></td>
        </tr>
        <tr>
            <td>Last name:</td>
            <td><form:input path="lastName" size="30"/></td>
        </tr>
        <tr>
            <td></td>
            <td><form:errors path="lastName" cssClass="error"/></td>
        </tr>
        <tr>
            <td>Address:</td>
            <td><form:input path="deliveryAddress" size="30"/></td>
        </tr>
        <tr>
            <td></td>
            <td><form:errors path="deliveryAddress" cssClass="error"/></td>
        </tr>
        <tr>
            <td>Phone:</td>
            <td><form:input path="contactPhoneNo" size="30"/></td>
        </tr>
        <tr>
            <td></td>
            <td><form:errors path="contactPhoneNo" cssClass="error"/></td>
        </tr>
        <tr>
            <td>Additional information</td>
            <td><form:textarea path="additionalInformation"/></td>
        </tr>
        <tr>
            <td></td>
            <td align="center"><input type="submit" value="Order"/></td>
            <td></td>
        </tr>
    </form:form>
</table>
</body>
</html>
