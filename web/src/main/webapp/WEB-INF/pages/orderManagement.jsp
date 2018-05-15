<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags/components" %>

<template:page title="Order management">
    <script> <%@ include file="scripts/orderManagement.js" %> </script>
    <components:header cartShown="false"/>

    <div class="container mt-3 py-1 w-100">
        <div class="d-inline-block float-left">
            <h4>Order id: <c:out value="${order.id}"/></h4>
        </div>
        <div class="d-inline-block float-right">
            <h4>Order status: <span style="font-size: 20pt; font-weight: bold;"><c:out value="${order.status}"/></span></h4>
        </div>
    </div>
    <div class="container mt-3 clearfix">
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
    <div class="container mt-3 clearfix">
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
    <div class="container mt-3">
        <a class="btn btn-primary mx-2" href="${pageContext.request.contextPath}/admin/orders">Back to orders</a>
        <button class="btn btn-primary mx-2" onclick="updateStatus('${order.id}', 'DELIVERED')">Delivered</button>
        <button class="btn btn-primary mx-2" onclick="updateStatus('${order.id}', 'REJECTED')">Rejected</button>
    </div>
</template:page>
