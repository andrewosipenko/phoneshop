<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<template:page>
    <c:if test="${errorMessage ne null}">
        <p class="text-danger">
            ${errorMessage}
        </p>
    </c:if>
    <c:choose>
        <c:when test="${orderForm.orderFormItems.size() ne 0}">
        <table class="table table-striped">
            <thead>
            <tr>
                <td>Brand</td>
                <td>Model</td>
                <td>Color</td>
                <td>Display size</td>
                <td>Quantity</td>
                <td>Price</td>
            </tr>
            </thead>
            <c:set var="orderFormItems" value="${orderForm.orderFormItems}"/>
            <c:forEach var="orderFormItem" items="${orderFormItems}" >
                <tr>
                    <td>${orderFormItem.phone.brand}</td>
                    <td><a class="hyperlink" href="<c:url value="/productDetails/phoneId=${orderFormItem.phone.id}"/>">${orderFormItem.phone.model}</a></td>
                    <td>
                        <template:getColors phoneColors="${orderFormItem.phone.colors}"/>
                    </td>
                    <td>${orderFormItem.phone.displaySizeInches}"</td>
                    <td>${orderFormItem.quantity}</td>
                    <td>${orderFormItem.phone.price} $</td>
                </tr>
            </c:forEach>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td>Subtotal</td>
                <td>${orderForm.subtotal} $</td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td>Delivery price</td>
                <td>${orderForm.deliveryPrice} $</td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td>TOTAL</td>
                <td>${orderForm.totalPrice} $</td>
            </tr>
        </table>
    <form:form method="post" commandName="orderForm" >
    <table>
            <tr>
                <td>First name*</td>
                <td><form:input path="firstName"  /></td>
                <td><form:errors cssClass="text-danger" path="firstName" /></td>
            </tr>
            <tr>
                <td>Last name*</td>
                <td><form:input path="lastName" /></td>
                <td><form:errors cssClass="text-danger" path="lastName" /></td>
            </tr>
            <tr>
                <td>Address*</td>
                <td><form:input path="deliveryAddress" /></td>
                <td><form:errors cssClass="text-danger" path="deliveryAddress" /></td>
            </tr>
            <tr>
                <td>Phone*</td>
                <td><form:input path="contactPhoneNo" /></td>
                <td><form:errors cssClass="text-danger" path="contactPhoneNo" /></td>
            </tr>
        <tr>
            <td>Extra information:</td>
            <td><form:textarea path="extraInfo" /></td>
        </tr>
        </table>
        <input type="submit" class ="btn btn-success" value="Order">
    </form:form>
        </c:when>
        <c:otherwise>
            <p>
                There is nothing to order, please add some items from <a class="hyperlink" href="<c:url value="/productList"/>">product list</a>
                to your <a class="hyperlink" href="<c:url value="/cart"/>">cart</a>
            </p>
        </c:otherwise>
    </c:choose>
</template:page>