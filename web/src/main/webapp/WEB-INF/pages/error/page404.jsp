<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page>
    <div class="container">
        <h3>
            Error 404 not found!
        </h3>
        <p>
            Go back to <a class="hyperlink" href="<c:url value="/productList"/>"> product list</a> page.
        </p>
    </div>
</template:page>

