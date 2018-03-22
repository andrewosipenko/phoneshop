<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/templates" %>
<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@ attribute name="order" type="com.es.core.model.order.Order" required="true" %>
<div class="row py-3">
    <div class="col-sm-6">
        <h3>Order number: <c:out value="${order.id}"/></h3>
    </div>
    <div class="col-sm-6 text-right">
        <h3>Order status: ${order.status}</h3>
    </div>
</div>
<template:orderTable order="${order}"/>
<table class="table table-hover">
    <tbody>
    <tr>
        <td>
            <p>First name</p>
        </td>
        <td>
            <p><c:out value="${order.firstName}"/></p>
        </td>
    </tr>
    <tr>
        <td>
            <p>Last name</p>
        </td>
        <td>
            <p><c:out value="${order.lastName}"/></p>
        </td>
    </tr>
    <tr>
        <td>
            <p>Delivery address</p>
        </td>
        <td>
            <p><c:out value="${order.deliveryAddress}"/></p>
        </td>
    </tr>
    <tr>
        <td>
            <p>Phone</p>
        </td>
        <td>
            <p><c:out value="${order.contactPhoneNo}"/></p>
        </td>
    </tr>
    <tr>
        <td>
            <p>Additional info</p>
        </td>
        <td>
            <p><c:out value="${order.additionalInfo}"/></p>
        </td>
    </tr>
    </tbody>
</table>
