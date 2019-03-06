<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<tags:master pageTitle="Cart">
    <div class="content">
        <c:if test="${cart.totalPrice != null && cart.totalPrice != 0}">
            <h1>Cart</h1>
        </c:if>
        <tags:backToProductList/>
        <c:choose>
            <c:when test="${cart.totalPrice == null || cart.totalPrice == 0}">
                <div class="message">
                    Your cart is empty
                </div>
            </c:when>
            <c:otherwise>
                <tags:orderButton/>
                <form id="deleteForm" method="post">
                    <input type="hidden" name="_method" value="DELETE"/>
                </form>
                <form:form action="${pageContext.servletContext.contextPath}/cart/update" modelAttribute="cartItemsInfo"
                           method="POST">
                    <input type="hidden" name="_method" value="PUT"/>
                    <button class="btn button-on-page left-button">
                        Update
                    </button>

                    <div class="clearfix"/>
                    <table class="table table-bordered cart-table">
                        <thead>
                        <tr>
                            <td>Brand</td>
                            <td>Model</td>
                            <td>Color</td>
                            <td>Display size</td>
                            <td>Price</td>
                            <td>Quantity</td>
                            <td>Action</td>
                        </tr>
                        </thead>
                        <c:forEach var="cartItem" items="${cart.cartItems}" varStatus="cartItemStatus">
                            <tr class="content-align">
                                <td>${cartItem.phone.brand}</td>
                                <td>${cartItem.phone.model}</td>
                                <td>
                                    <c:forEach var="color" items="${cartItem.phone.colors}">
                                        ${color.code}
                                        <br>
                                    </c:forEach>
                                </td>
                                <td>${cartItem.phone.displaySizeInches}"</td>
                                <td>${cartItem.phone.price}$</td>
                                <td class="td-center-align">
                                    <c:choose>
                                        <c:when test="${errors != null}">
                                            <c:set var="errorIndex" value="${-1}" scope="page"/>
                                            <c:forEach var="error" items="${errors}" varStatus="errorStatus">
                                                <c:if test="${cartItemStatus.index == error.code}">
                                                    <c:set var="errorIndex" value="${errorStatus.index}"/>
                                                </c:if>
                                            </c:forEach>
                                            <c:choose>
                                                <c:when test="${errorIndex != -1}">
                                                    <form:input path="quantity" name="quantity${cartItem.phone.id}"
                                                                value="${errors.get(errorIndex).arguments[0]}"/>
                                                    <div class="error-message">${errors.get(errorIndex).defaultMessage}</div>
                                                </c:when>
                                                <c:otherwise>
                                                    <form:input path="quantity" name="quantity${cartItem.phone.id}"
                                                                value="${cartItem.quantity}"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <form:input path="quantity" name="quantity${cartItem.phone.id}"
                                                        value="${cartItem.quantity}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="td-center-align">
                                        <button formaction="${pageContext.servletContext.contextPath}/cart/delete/${cartItem.phone.id}" form="deleteForm">
                                            Delete
                                        </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </form:form>
                <tags:orderButton/>
            </c:otherwise>
        </c:choose>
    </div>
</tags:master>