<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<template:page>
    <div class="container">
        <h4>Order</h4>
        <a class ="btn" href="<c:url value="/productList"/>"><h4>Back to product list</h4></a>
        <c:if test="${errorMessage ne null}">
            <h4>
                ${errorMessage}
            </h4>
        </c:if>
        <c:choose>
        <c:when test="${not empty orderForm.orderFormItems}">
        <table id="tableProductsOrder" border="1px" width="100%" cellspacing="0" class="table table-striped table-bordered table-hover">
            <thead>
            <tr id="headerTable">
                <td>Brand</td>
                <td>Model</td>
                <td>Color</td>
                <td>Display size</td>
                <td>Quantity</td>
                <td>Price</td>
            </tr>
            </thead>
            <tbody>
            <c:set var="orderFormItems" value="${orderForm.orderFormItems}"/>
            <c:forEach var="orderItem" items="${orderFormItems}">
                <tr>
                    <td>${orderItem.phone.brand}</td>
                    <td>
                        <a class="hyperlink" href="<c:url value="/productDetails/phoneId=${orderItem.phone.id}"/>">${orderItem.phone.model}</a>
                    </td>
                    <td>
                        <c:forEach var="color" items="${orderItem.phone.colors}" varStatus="counter">
                            ${color.code}
                            <c:if test="${counter.count != orderItem.phone.colors.size()}">
                                ,
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>${orderItem.phone.displaySizeInches}"</td>
                    <td>${orderItem.quantity}</td>
                    <td>${orderItem.phone.price}.00$</td>
                </tr>
            </c:forEach>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td>Subtotal</td>
                <td>${orderForm.subtotal}$</td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td>Delivery</td>
                <td>${orderForm.deliveryPrice}$</td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td>TOTAL</td>
                <td>${orderForm.totalPrice}$</td>
            </tr>
            </tbody>
        </table>
        <form:form method="post" modelAttribute="orderForm">
            <table>
                <tr>
                    <td>First name*</td>
                    <td><form:input path="firstName"/></td>
                    <td><form:errors cssClass="text-danger" path="firstName"/></td>
                </tr>
                <tr>
                    <td>Last name*</td>
                    <td><form:input path="lastName"/></td>
                    <td><form:errors cssClass="text-danger" path="lastName"/></td>
                </tr>
                <tr>
                    <td>Address*</td>
                    <td><form:input path="deliveryAddress"/></td>
                    <td><form:errors cssClass="text-danger" path="deliveryAddress"/></td>
                </tr>
                <tr>
                    <td>Phone*</td>
                    <td><form:input path="contactPhoneNo"/></td>
                    <td><form:errors cssClass="text-danger" path="contactPhoneNo"/></td>
                </tr>
                <tr>
                    <td>Additional information:</td>
                    <td><form:textarea path="additionalInformation"/></td>
                </tr>
            </table>
            <input type="submit" value="Order" style="width: 100px;">
        </form:form>
        </c:when>
        <c:otherwise>
            <p>
                There is nothing to order, please add some items from <a class="hyperlink" href="<c:url value="/productList"/>">product list</a>
                to your <a class="hyperlink" href="<c:url value="/cart"/>">cart</a>
            </p>
        </c:otherwise>
        </c:choose>
    </div>
</template:page>