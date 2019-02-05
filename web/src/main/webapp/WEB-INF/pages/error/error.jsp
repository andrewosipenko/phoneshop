<!doctype html>
<html lang="en">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page>
    <div class = "container">
        <h3>
            Something was wrong.
        </h3>
        <p class="error">
            <c:if test="${errorMessage ne null}">
                Error message: ${errorMessage}
            </c:if>
        </p>
        <p>
            Go back to <a class="hyperlink" href="/productList"> product list</a> page.
        </p>
    </div>
</template:page>