<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags/components" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<template:page title="Order">
    <script> <%@ include file="scripts/cart.js" %> </script>
    <components:header cartShown="true"/>

    <div class="container mt-3 py-1">
        <div class="d-inline-block">
            <h3>Order</h3>
        </div>
    </div>
    <div class="container mt-1">
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/productList">Back to product List</a>
    </div>

    <div class="container mt-3">
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
            <c:forEach items="${orderPageForm.order.orderItems}" var="orderItem">
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
    </div>
    <div class="container mt-3">
        <table class="table table-bordered table-striped float-right w-25">
            <tbody>
            <tr>
                <td>Subtotal:</td>
                <td><c:out value="$${orderPageForm.order.subtotal}"/></td>
            </tr>
            <tr>
                <td>Delivery price:</td>
                <td><c:out value="$${orderPageForm.order.deliveryPrice}"/></td>
            </tr>
            <tr>
                <td>Total:</td>
                <td><c:out value="$${orderPageForm.order.totalPrice}"/></td>
            </tr>
            </tbody>
        </table>
        <div class="float-left w-50">
            <form:form modelAttribute="orderPageForm" method="post">
                <label>First name: <form:input path="firstName"/></label>
                <form:errors delimiter="<br/>" cssStyle="color: red; display: block;" path="firstName"/><br/>
                <label>Last name: <form:input path="lastName"/></label>
                <form:errors delimiter="<br/>" cssStyle="color: red; display: block;" path="lastName"/><br/>
                <label>Delivery address: <form:input path="deliveryAddress"/></label>
                <form:errors delimiter="<br/>" cssStyle="color: red; display: block;" path="deliveryAddress"/><br/>
                <label>Contact phone number: <form:input path="contactPhoneNo"/></label>
                <form:errors delimiter="<br/>" cssStyle="color: red; display: block;" path="contactPhoneNo"/><br/>
                <label style="width: 200px;">Additional information: <br/><form:textarea path="additionalInformation" cssStyle="width: 100%;"/></label>
                <form:errors path="stocksAvailable" cssStyle="color: red; display: block;"/><br/>
                <button type="submit" class="btn btn-primary">Place an order</button>
            </form:form>
        </div>
    </div>

</template:page>
