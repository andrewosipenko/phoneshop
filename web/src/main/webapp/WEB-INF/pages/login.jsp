<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags/components" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<template:page title="Login">

    <div class="container" style="width: 300px;">
        <c:url value="/j_spring_security_check" var="loginUrl" />
        <form action="${loginUrl}" method="post">
            <h2 class="form-signin-heading mb-3 mt-5">Please sign in</h2>
            <input type="text" class="form-control my-3" name="j_username" placeholder="Login" required autofocus value="">
            <input type="password" class="form-control my-3" name="j_password" placeholder="Password" required value="">
            <button class="btn btn-lg btn-primary btn-block my-3" type="submit">Login</button>
        </form>
        <c:if test="${not empty loginError}">
            <p style="color: red;"><c:out value="${loginError}"/></p>
        </c:if>
    </div>

</template:page>