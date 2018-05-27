<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@ attribute name="cartShown" type="java.lang.Boolean" required="false" %>
<%@ attribute name="productListButtonEnabled" type="java.lang.Boolean" required="false" %>
<%@ attribute name="titleEnabled" type="java.lang.Boolean" required="false" %>
<%@ attribute name="title" type="java.lang.String" required="false" %>

<c:if test="${cartShown}">
    <script>
        $(document).ready(function() {
            $.get({
                url: "${pageContext.request.contextPath}/ajaxCart",
                success: function(status) {
                    $('#phonesTotal').html(status.phonesTotal);
                    $('#costTotal').html(status.subtotal);
                }
            });
        });
    </script>
</c:if>

<security:authentication property="principal" var="principal"/>

<div class="bg-light w-100">
    <div class="container pb-4 pt-3">
        <div class="page-header text-primary">
            <a style="text-decoration: none; color: inherit;" href="${pageContext.request.contextPath}"><h1 class="d-inline">Phone Shop</h1></a>
            <div class="float-right" style="margin-top: -5px;">
                <security:authorize access="isAuthenticated()">
                    <span class="mt-1 text-success"><c:out value="${principal.username}"/></span>
                </security:authorize>
                <security:authorize access="hasRole('ROLE_ADMIN')">
                    <a class="mt-1 ml-2" href="${pageContext.request.contextPath}/admin/orders">Orders</a>
                </security:authorize>
                <security:authorize access="isAuthenticated()">
                    <a class="mt-1 ml-2" href="${pageContext.request.contextPath}/j_spring_security_logout">Logout</a> <br/>
                </security:authorize>
                <security:authorize access="isAnonymous()">
                    <a class="mt-1 ml-2" href="${pageContext.request.contextPath}/login">Login</a>
                </security:authorize>
                <c:if test="${cartShown}">
                    <a class="btn btn-primary d-block" href="${pageContext.request.contextPath}/cart">
                        My cart: <span id="phonesTotal"></span> items $<span id="costTotal"></span>
                    </a>
                </c:if>
            </div>
        </div>
    </div>
</div>
<c:if test="${titleEnabled}">
    <div class="container mt-3 py-1">
        <div class="d-inline-block">
            <h3><c:out value="${title}"/></h3>
        </div>
    </div>
</c:if>
<c:if test="${productListButtonEnabled}">
    <div class="container mt-3">
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/productList">Back to product list</a>
    </div>
</c:if>

