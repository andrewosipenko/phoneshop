<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/templates" %>
<template:page>
    <div class="row">
        <div class="col-sm-12 col-md-10 col-md-offset-1">
            <template:orderOverview order="${order}"/>
            <a href="<c:url value="/productList"/>" class="btn btn-primary">
                Back to shopping
            </a>
        </div>
    </div>
</template:page>