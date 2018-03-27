<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Order</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="<c:url value="/resources/js/product_list.js"/>"></script>
        <script src="<c:url value="/resources/js/jquery.number.js"/>"></script>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col">
                    <a href="${pageContext.request.contextPath}/productList"><img src="<c:url value="/resources/img/logo.jpg"/>"></a>
                </div>
            </div>
            <div class="row">
                <div class="col bold">Order</div>
            </div>
            <div class="row">
                <div class="col">
                    <form method="GET" action="${pageContext.request.contextPath}/cart">
                        <input type="submit" value="Back to cart">
                    </form>
                </div>
            </div>
            <c:if test="${empty order.items}">
                <span class="error bold"><spring:message code="cart.empty"/></span>
            </c:if>
            <c:if test="${not empty order.items}">
            <div class="row">
                <table id="order-table">
                    <thead>
                    <tr>
                        <td>Brand</td>
                        <td>Model</td>
                        <td>Color</td>
                        <td>Display size</td>
                        <td>Quantity</td>
                        <td>Price</td>
                    </tr>
                    </thead>
                    <c:forEach var="phone" items="${order.items.keySet()}">
                        <tr>
                            <td>${phone.brand}</td>
                            <td>${phone.model}</td>
                            <td>
                                <c:forEach var="color" items="${phone.colors}">
                                    ${color.code}
                                </c:forEach>
                            </td>
                            <td>${phone.displaySizeInches}''</td>
                            <td>${order.items.get(phone)}</td>
                            <td><span class="price">${phone.price * order.items.get(phone)}</span>$</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td class="empty-td"></td>
                        <td class="empty-td"></td>
                        <td class="empty-td"></td>
                        <td class="empty-td"></td>
                        <td>Subtotal</td>
                        <td><span class="price">${order.subtotal}</span>$</td>
                    </tr>
                    <tr>
                        <td class="empty-td"></td>
                        <td class="empty-td"></td>
                        <td class="empty-td"></td>
                        <td class="empty-td"></td>
                        <td>Delivery</td>
                        <td><span class="price">${order.delivery}</span>$</td>
                    </tr>
                    <tr>
                        <td class="empty-td"></td>
                        <td class="empty-td"></td>
                        <td class="empty-td"></td>
                        <td class="empty-td"></td>
                        <td>TOTAL</td>
                        <td><span class="price">${order.total}</span>$</td>
                    </tr>
                </table>
            </div>
            </c:if>

            <div class="row">
                <div class="col">
                    <c:if test="${not empty noProducts}">
                        <div class="error bold">${noProducts}</div>
                    </c:if>
                </div>
            </div>

            <c:if test="${not empty order.items}">
            <div class="row">
                <div class="col-3">
                    <form:form id="order" method="POST" action="${pageContext.request.contextPath}/order" modelAttribute="personInfo">
                        <table id="person-info">
                            <tr>
                                <td align="left"><form:label path="firstName">First name<sup>*</sup></form:label></td>
                                <td>
                                    <form:input path="firstName" placeholder="First name"/>
                                    <form:errors path="firstName" cssClass="error" element="div"/>
                                </td>
                            </tr>
                            <tr>
                                <td><form:label path="lastName">Last name<sup>*</sup></form:label></td>
                                <td>
                                    <form:input path="lastName" placeholder="Last name"/>
                                    <form:errors path="lastName" cssClass="error" element="div"/>
                                </td>
                            </tr>
                            <tr>
                                <td><form:label path="address">Address<sup>*</sup></form:label></td>
                                <td>
                                    <form:input path="address" placeholder="Address"/>
                                    <form:errors path="address" cssClass="error" element="div"/>
                                </td>
                            </tr>
                            <tr>
                                <td><form:label path="phone">Phone<sup>*</sup></form:label></td>
                                <td>
                                    <form:input path="phone" placeholder="Phone"/>
                                    <form:errors path="phone" cssClass="error" element="div"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2"><form:textarea path="additionalInfo" rows="5" cols="40" placeholder="Additional information"/></td>
                            </tr>
                        </table>
                    </form:form>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <input form="order" type="submit" value="Order">
                </div>
            </div>
            </c:if>
        </div>
    </body>
</html>