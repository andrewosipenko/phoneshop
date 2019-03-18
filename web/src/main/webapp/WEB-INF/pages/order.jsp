<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="forn" uri="http://www.springframework.org/tags/form" %>

<tags:master pageTitle="order" isShowCart="false">
    <div class="content">
        <h1>Order</h1>
        <tags:backToCart/>
        <form:form action="${pageContext.servletContext.contextPath}/order/create" modelAttribute="orderInfo"
                   method="POST">
            <table class="table table-bordered cart-table">
                <thead>
                <tr>
                    <td>Brand</td>
                    <td>Model</td>
                    <td>Color</td>
                    <td>Display size</td>
                    <td>Price</td>
                    <td>Quantity</td>
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
                        <td>
                            <c:choose>
                                <c:when test="${errors != null}">
                                    <c:set var="errorIndex" value="${-1}" scope="page"/>
                                    <c:forEach var="error" items="${errors}" varStatus="errorStatus">
                                        <c:if test="${cartItem.phone.id.equals(error.arguments[0])}">
                                            <c:set var="errorIndex" value="${errorStatus.index}"/>
                                        </c:if>
                                    </c:forEach>
                                    <form:input path="quantity" name="quantity"
                                                value="${cartItem.quantity}" disabled="true"/>
                                    <c:if test="${errorIndex != -1}">
                                        <div class="error-message">${errors.get(errorIndex).defaultMessage}</div>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <form:input path="quantity" name="quantity${cartItem.phone.id}"
                                                value="${cartItem.quantity}" disabled="true"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>Subtotal</td>
                    <td>${cart.totalPrice}$</td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>Delivery</td>
                    <td>${deliveryPrice}$</td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>TOTAL</td>
                    <td>${cart.totalPrice + deliveryPrice}$</td>
                </tr>
            </table>
            <br>
            <div class="order-input">
                <p class="order-input-info">First name: </p><form:input path="name" placeholder="First name"
                                                                        value="${name[0]}"/><br><br>
                <div class="error-message">${name[1]}</div>
                <p class="order-input-info">Last name: </p><form:input path="lastName" placeholder="Last name"
                                                                       value="${lastName[0]}"/><br><br>
                <div class="error-message">${lastName[1]}</div>
                <p class="order-input-info">Address: </p><form:input path="address" placeholder="Address"
                                                                     value="${address[0]}"/><br><br>
                <div class="error-message">${address[1]}</div>
                <p class="order-input-info">Phone: </p><form:input path="phone" placeholder="Phone"
                                                                   value="${phone[0]}"/><br><br>
                <div class="error-message">${phone[1]}</div>
                <form:textarea path="additionalInfo" placeholder="Additional Info" value="${additionalInfo[0]}"/><br>
                <div class="error-message">${additionalInfo[1]}</div>
            </div>
            <button class="btn button-on-page left-button">Order</button>
        </form:form>
    </div>
</tags:master>
