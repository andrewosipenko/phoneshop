<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page>
    <div class = "container">
        <h4 class="headers-region">
            Seems you've done something illegal
        </h4>
        <p class="error">
            <c:if test="${errorMessage ne null}">
                Error message: ${errorMessage}
            </c:if>
        </p>
        <a class="selection-handle">
            Go back to <a class="hyperlink" href="${pageContext.request.contextPath}/productList">product list</a> page
        </a>
    </div>
</template:page>