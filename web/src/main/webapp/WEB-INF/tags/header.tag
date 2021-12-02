<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="cart" required="true" type="com.es.core.model.cart.Cart" %>
<div class="row">
    <div class="col col-md-10"></div>
    <div class="col col-md-2">
        <a href="">Login</a>
    </div>
</div>
<br>
<div class="row">
    <div class="col col-md-2">
        <a href="${pageContext.request.contextPath}/productList">
            <h1>Phones</h1>
        </a>
    </div>
    <div class="col col-md-7"></div>
    <div class="col col-md-3">
        <form action="${pageContext.servletContext.contextPath}/cart">
            <button class="btn btn-outline-secondary">
                My cart:
                <text id="totalQuantity">${cart.totalQuantity}</text>
                item
                <text id="totalCost">${cart.totalCost}</text>
                $
            </button>
        </form>
    </div>
    <hr align="center" width="300" color="Red"/>
</div>