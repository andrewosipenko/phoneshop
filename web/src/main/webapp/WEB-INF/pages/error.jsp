<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page >
    <div class="container">
        <div class="jumbotron">
            <div class="display-3">
                Ooops, something went wrong
            </div>
            <div class="display-4">
                <a href="<c:url value="/productList"/>">
                    <p>Go to main page</p>
                </a>
            </div>
        </div>
    </div>
</template:page>
