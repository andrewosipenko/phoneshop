<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/templates" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<template:page>
    <div class="pt-3">
        <form method="post">
            <c:if test="${param.error != null}">
                <p class="error-message">
                    Invalid username or password.
                </p>
            </c:if>
            <p>
                <label for="username">Username</label>
                <input type="text" id="username" name="username"/>
            </p>
            <p>
                <label for="password">Password</label>
                <input type="password" id="password" name="password"/>
            </p>
            <sec:csrfInput/>
            <button type="submit" class="btn">Log in</button>
        </form>
    </div>
</template:page>