<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page>
    <div class = "container">
        <h4 class="headers-region">
            400 bad request
        </h4>
        <a class="selection-handle">
            Go back to <a class="hyperlink" href="<c:url value="/productList"/>">product list</a> page
        </a>
    </div>
</template:page>