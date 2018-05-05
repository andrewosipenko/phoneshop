<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags/components" %>

<template:page title="Order overview">
    <script> <%@ include file="scripts/cart.js" %> </script>
    <components:header cartShown="true"/>

    <div class="container mt-3 py-1">
        <div class="d-inline-block">
            <h3>Thank you for your order</h3>
            <h4>Order number: ${order.id}</h4>
        </div>
    </div>
    <div class="container mt-1">
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/productList">Back to shopping</a>
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
            <c:forEach items="${order.orderItems}" var="orderItem">
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
                <td><c:out value="$${order.subtotal}"/></td>
            </tr>
            <tr>
                <td>Delivery price:</td>
                <td><c:out value="$${order.deliveryPrice}"/></td>
            </tr>
            <tr>
                <td>Total:</td>
                <td><c:out value="$${order.totalPrice}"/></td>
            </tr>
            </tbody>
        </table>
        <div class="float-left w-50">
            <p><b>First name:</b> <c:out value="${order.firstName}"/></p>
            <p><b>Last name:</b> <c:out value="${order.lastName}"/></p>
            <p><b>Delivery address:</b> <c:out value="${order.deliveryAddress}"/></p>
            <p><b>Contact phone number:</b> <c:out value="${order.contactPhoneNo}"/></p>
            <p><b>Additional information:</b> <br/> <c:out value="${order.additionalInformation}"/></p>
        </div>
    </div>

</template:page>
