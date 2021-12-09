<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="cart" required="true" type="com.es.core.model.cart.Cart" %>
<%@attribute name="isCartAvailable" required="true" type="java.lang.Boolean" %>
<div class="row">
    <div class="col col-md-10"></div>
    <div class="col col-md-2">
        <a href="">
            <spring:message key="header.login"/>
        </a>
    </div>
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