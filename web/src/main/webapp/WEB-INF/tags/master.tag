<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="pageTitle" type="java.lang.String" required="true" %>
<%@attribute name="isShowCart" type="java.lang.Boolean" required="true" %>

<html>
<head>
    <title>${pageTitle}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <link href=${pageContext.request.contextPath}/resources/css/main.css rel="stylesheet" type="text/css"/>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/ajaxAddCartItem.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bindAction.js"></script>
</head>
<body>
<header>
    <a href="${pageContext.servletContext.contextPath}/productList?page=1">
        <img src="${pageContext.servletContext.contextPath}/resources/images/logo.png"/>
    </a>
    <c:if test="${isShowCart == true}">
        <div class="header-cart">
            <a href="${pageContext.servletContext.contextPath}/cart"> My cart:
                <p class="inline-element" id="countOfCartItem">${countOfCartItems}</p>
                items
                <p class="inline-element" id="totalPrice">${totalPrice == null ? "0" : totalPrice}</p>$</a>
        </div>
    </c:if>
    <c:choose>
        <c:when test="${username == null}">
            <a class="authorization" href="${pageContext.request.contextPath}/login">Login</a>
        </c:when>
        <c:otherwise>
            <a class="username" href="${pageContext.request.contextPath}/logout">Logout</a>
            <p class="authorization">${username}</p>
        </c:otherwise>
    </c:choose>
    <div class="clearfix"/>
</header>

<jsp:doBody/>
</body>
</html>