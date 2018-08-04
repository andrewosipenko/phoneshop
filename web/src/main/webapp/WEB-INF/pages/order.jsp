<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags/components" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<template:page title="Order">
    <script> <%@ include file="scripts/cart.js" %> </script>
    <components:header cartShown="true" productListButtonEnabled="true" titleEnabled="true" title="Order"/>

    <div class="container mt-3">
        <c:choose>
            <c:when test="${not empty cart.cartItems}">
                <table class="table table-bordered table-striped">
                    <thead style="background-color: #828082;">
                    <tr class="d-table-row text-light text-center">
                        <th scope="col" style="width: 10%">Brand</th>
                        <th scope="col" style="width: 19%">Model</th>
                        <th scope="col" style="width: 20%">Colors</th>
                        <th scope="col" style="width: 15%">Display size</th>
                        <th scope="col" style="width: 9%">Quantity</th>
                        <th scope="col" style="width: 9%">Price</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${cart.cartItems}" var="orderItem">
                        <tr>
                            <td style="vertical-align: middle!important"><c:out value="${orderItem.phone.brand}"/></td>
                            <td style="vertical-align: middle!important"><c:out value="${orderItem.phone.model}"/></td>
                            <td style="vertical-align: middle!important"><c:forEach items="${orderItem.phone.colors}" var="color" varStatus="loop"><c:out value="${color.code}"/><c:if test="${not loop.last}">, </c:if></c:forEach></td>
                            <td style="vertical-align: middle!important"><c:out value="${orderItem.phone.displaySizeInches}''"/></td>
                            <td style="vertical-align: middle!important"><c:out value="${orderItem.quantity}"/></td>
                            <td style="vertical-align: middle!important"><c:out value="$${orderItem.phone.price}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <p style="color: red;">Your cart is empty, would you like to continue shopping?</p>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="container mt-3">
        <c:if test="${not empty cart.cartItems}">
            <table class="table table-bordered table-striped float-right w-25">
                <tbody>
                <tr>
                    <td>Subtotal:</td>
                    <td><c:out value="$${cart.subtotal}"/></td>
                </tr>
                <tr>
                    <td>Delivery price:</td>
                    <td><c:out value="$${cart.deliveryPrice}"/></td>
                </tr>
                <tr>
                    <td>Total:</td>
                    <td><c:out value="$${cart.subtotal.add(cart.deliveryPrice)}"/></td>
                </tr>
                </tbody>
            </table>
        </c:if>
        <div class="float-left w-50">
            <form:form modelAttribute="orderPersonalDataForm" method="post">
                <c:if test="${not empty cart.cartItems}">
                    <label>First name: <form:input path="firstName"/></label>
                    <form:errors delimiter="<br/>" cssStyle="color: red; display: block;" path="firstName"/><br/>
                    <label>Last name: <form:input path="lastName"/></label>
                    <form:errors delimiter="<br/>" cssStyle="color: red; display: block;" path="lastName"/><br/>
                    <label>Delivery address: <form:input path="deliveryAddress"/></label>
                    <form:errors delimiter="<br/>" cssStyle="color: red; display: block;" path="deliveryAddress"/><br/>
                    <label>Contact phone number: <form:input path="contactPhoneNo"/></label>
                    <form:errors delimiter="<br/>" cssStyle="color: red; display: block;" path="contactPhoneNo"/><br/>
                    <label style="width: 200px;">Additional information: <br/><form:textarea path="additionalInformation" cssStyle="width: 100%;"/></label>
                </c:if>
                <form:errors path="stocksAvailable" cssStyle="color: red; display: block;"/><br/>
                <c:if test="${not empty cart.cartItems}">
                    <button type="submit" class="btn btn-primary">Place an order</button>
                </c:if>
            </form:form>
        </div>
    </div>
</template:page>
