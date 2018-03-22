<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ attribute name="catalogTabIsActive" type="java.lang.Boolean" %>
<%@ attribute name="ordersTabIsActive" type="java.lang.Boolean" %>
<jsp:useBean id="cart" scope="session" class="com.es.core.model.cart.Cart"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <sec:csrfMetaTags/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>"/>
</head>
<body>
<div class="auth">
    <sec:authorize access="hasRole('ANONYMOUS')">
        <a href="<c:url value="/login"/>">Login</a>
    </sec:authorize>
    <sec:authorize access="hasRole('ADMIN')">
        <form action="<c:url value="/logout"/>" method="post">
            <sec:csrfInput/>
            <span><sec:authentication property="principal.username"/></span>
            <button class="logout_button" type="submit">Logout</button>
        </form>
    </sec:authorize>
</div>
<div class="topnav">
    <a ${catalogTabIsActive ? 'class="active"' : ''} href="<c:url value="/productList"/>">Catalog</a>
    <sec:authorize access="hasRole('ADMIN')">
        <a ${ordersTabIsActive ? 'class="active"' : ''} href="<c:url value="/admin/orders"/>">Orders</a>
    </sec:authorize>
    <div class="cart-container">
        <a href="<c:url value="/cart"/>" class="btn btn-outline-dark">
            My Cart: <span class="cart-count"><c:out value="${not empty cart ? cart.countItems : 0}"/></span> Items ($
            <span
                    class="cart-cost"><c:out value="${not empty cart ? cart.cost : 0}"/></span>)
        </a>
    </div>
    <div class="search-container">
        <form action="<c:url value="/productList"/>/">
            <input type="text" placeholder="Search.." name="query">
            <button type="submit"><i class="fa fa-search"></i></button>
        </form>
    </div>
</div>
<div class="container">
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">
            <strong><c:out value="${errorMessage}"/></strong>
        </div>
    </c:if>
    <jsp:doBody/>
</div>
<script>
    var context_path = "<c:out value="${pageContext.request.contextPath}"/>";
    var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");
</script>
<script src="<c:url value="/resources/js/cart.js"/>"></script>
</body>
</html>