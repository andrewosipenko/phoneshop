<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tags:master pageTitle="login" isShowCart="false">
    <div class="content">
        <c:if test="${not empty param.error}">
            <div class="error-message page-text">${errorMessage}</div>
        </c:if>
        <form class="login-form" action="<c:url value='/j_spring_security_check'/>" method="post">
            Name: <input class="login_input" type="text" name="j_username"/><br><br>
            Password: <input class="login_input" type="password" name="j_password"/><br><br>
            Remember me: <input class="login_input check-box" type="checkbox" name="_spring_security_remember_me"/><br><br>
            <button>Login</button>
        </form>
    </div>
</tags:master>