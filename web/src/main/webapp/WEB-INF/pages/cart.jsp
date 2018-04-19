<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<template:page>
    <div class="container">
        <a class ="btn btn-success" href="<c:url value="/productList"/>">Back to product list</a>
        <c:choose>
            <c:when test="${not empty updateCartForm.cartFormItems}">
                <form:form method="post" action="${pageContext.request.contextPath}/cart/update"
                modelAttribute="updateCartForm">
                <table class="table table-striped">
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
                    <c:set var="cartFormItems" value="${updateCartForm.cartFormItems}"/>
                    <c:forEach begin="0" end="${cartFormItems.size() - 1}" var="index" >
                        <form:hidden path="cartFormItems['${index}'].phoneId" value="${cartFormItems[index].phoneId}" />
                        <tr>
                            <td>${phones[index].brand}</td>
                            <td><a class="hyperlink" href="<c:url value="/productDetails/phoneId=${phones[index].id}"/>">${phones[index].model}</a></td>
                            <td>
                                <template:getColors phoneColors="${phones[index].colors}"/>
                            </td>
                            <td>${phones[index].displaySizeInches}"</td>
                            <td>${phones[index].price} $</td>
                            <td>
                                <label>
                                <form:input class="text-input" path="cartFormItems['${index}'].quantity"
                                       value="${cartFormItems[index].quantity}"/><br>
                                    <form:errors path="cartFormItems['${index}'].quantity" cssClass="text-danger"/>
                                </label>
                            </td>
                            <td>
                                <input formmethod="post" formaction="<c:url value="/cart/delete"/>?phoneId=${phones[index].id}" type="submit" value="Delete">

                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <input type="submit" class ="btn btn-success" value="Update">
                </form:form>
                <a class ="btn btn-success" href="<c:url value="/productList"/>">Order</a>
            </c:when>
            <c:otherwise>
                <h4>Cart is empty</h4>
            </c:otherwise>
        </c:choose>
    </div>
</template:page>