<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="cart" required="false" type="com.es.core.model.cart.Cart" %>
<%@attribute name="isCartAvailable" required="true" type="java.lang.Boolean" %>
<div class="row">
    <sec:authorize access="!isAuthenticated()">
        <div class="col col-md-11"></div>
        <div class="col col-md-1">
            <a href="<c:url value="/login"/>"><spring:message key="header.login"/></a>
        </div>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
        <div class="row">
            <div class="col col-md-10"></div>
            <div class="col-1">
                <div class="float-end">
                    <c:out value="${pageContext.request.remoteUser}"/>
                </div>
            </div>
            <div class="col-1">
                <form method="post" action="${pageContext.request.contextPath}/logout">
                    <a class="float-end" href="<c:url value="/logout"/>"><spring:message key="button.logout"/></a>
                </form>
            </div>
        </div>
        <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
            <div class="row">
                <div class="col-md-7"></div>
                <div class="col-md-5">
                    <a class="float-end" href="${pageContext.servletContext.contextPath}/admin/orders"><spring:message key="titlePage.admin"/></a>
                </div>
            </div>
        </c:if>
    </sec:authorize>
</div>
<br>
<div class="row">
    <div class="col col-md-2">
        <a href="${pageContext.request.contextPath}/productList">
            <h1>
                <spring:message key="header.mainTextLogo"/>
            </h1>
        </a>
    </div>
    <div class="col col-md-7"></div>
    <div class="col col-md-3">
        <c:if test="${isCartAvailable == true}">
            <form action="${pageContext.servletContext.contextPath}/cart">
                <button class="btn btn-outline-secondary">
                    <spring:message key="header.miniCart.myCart"/>
                    <text id="totalQuantity">${cart.totalQuantity}</text>
                    <spring:message key="header.miniCart.item"/>
                    <text id="totalCost">${cart.totalCost}</text>
                    $
                </button>
            </form>
        </c:if>
    </div>
    <hr align="center" width="300" color="Red"/>
</div>

