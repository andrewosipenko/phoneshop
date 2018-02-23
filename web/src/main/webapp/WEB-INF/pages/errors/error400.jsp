<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/templates" %>
<template:page>
    <p>
        Error 400. Bad request.
    </p>
    <a href="${pageContext.request.contextPath}">
        <p>
            Go to main page.
        </p>
    </a>
</template:page>