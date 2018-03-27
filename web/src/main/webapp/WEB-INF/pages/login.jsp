<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Login</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <link href="<c:url value="/resources/css/login.css"/>" rel="stylesheet"/>
    </head>
    <body>
        <div class="login-page">
            <a href="${pageContext.request.contextPath}/productList">
                <img class="main-image" src="<c:url value="/resources/img/logo.jpg"/>">
            </a>
            <div class="form">
                <form class="login-form" method="post" action="${pageContext.request.contextPath}/j_spring_security_check">
                    <input type="text" placeholder="username" name="username"/>
                    <input type="password" placeholder="password" name="password"/>
                    <c:if test="${param.error != null}"><span class="error">Invalid username or password</span></c:if>
                    <button name="submit" type="submit">login</button>
                </form>
            </div>
        </div>
    </body>
</html>
