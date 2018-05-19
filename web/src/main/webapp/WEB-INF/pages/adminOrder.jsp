<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<template:page>
    <template:getOrderOverview order="${order}"/>
    <a class="btn btn-success" href="<c:url value="/admin/orders"/>">Back</a>
        <c:forEach var="orderStatusName" items="${orderStatusNames}">
            <c:url var="urlValue" value="/admin/orders/${order.id}/setNewOrderStatus" />
            <sf:form method="post" action="${urlValue}">
                <input hidden name="newOrderStatus" value="${orderStatusName}">
                <button class="btn btn-success" type="submit">${orderStatusName}</button>
            </sf:form>
        </c:forEach>
</template:page>