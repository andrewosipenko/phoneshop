<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<template:page>
    <p>
    <h3>Thank you for your order!</h3>
    </p>
    <template:getOrderOverview order="${order}"/>
    <a class="btn btn-success" href="<c:url value="/productList"/>">Back to shopping</a>
</template:page>