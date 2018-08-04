<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags/components" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<template:page title="Cart">
    <script> <%@ include file="scripts/cart.js" %> </script>
    <components:header cartShown="true" titleEnabled="true" title="Cart" productListButtonEnabled="true"/>

    <form method="post" id="deleteFromCartForm" hidden>
        <input type="hidden" name="_method" value="DELETE"/>
        <input type="hidden" name="phoneId" id="deleteFromCartPhoneId"/>
    </form>

    <div class="container mt-3">
        <c:choose>
            <c:when test="${not empty phones}">
                <table class="table table-bordered table-striped">
                    <thead style="background-color: #828082;">
                    <tr class="d-table-row text-light text-center">
                        <th scope="col" style="width: 10%">Brand</th>
                        <th scope="col" style="width: 19%">Model</th>
                        <th scope="col" style="width: 20%">Colors</th>
                        <th scope="col" style="width: 15%">Display size</th>
                        <th scope="col" style="width: 9%">Price</th>
                        <th scope="col" style="width: 9%">Quantity</th>
                        <th scope="col" style="width: 10%">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <form:form modelAttribute="updateCartForm" method="PUT" id="updateCartForm">
                        <c:set var="updateCartItems" value="${updateCartForm.updateCartItems}"/>
                        <c:forEach begin="0" end="${updateCartItems.size()-1}" var="index">
                            <form:hidden path="updateCartItems[${index}].phoneId"/>
                            <tr>
                                <td style="vertical-align: middle!important"><c:out value="${phones[index].brand}"/></td>
                                <td style="vertical-align: middle!important"><c:out value="${phones[index].model}"/></td>
                                <td style="vertical-align: middle!important"><c:forEach items="${phones[index].colors}" var="color" varStatus="loop"><c:out value="${color.code}"/><c:if test="${not loop.last}">, </c:if></c:forEach></td>
                                <td style="vertical-align: middle!important"><c:out value="${phones[index].displaySizeInches}''"/></td>
                                <td style="vertical-align: middle!important">$<c:out value="${phones[index].price}"/></td>
                                <td style="vertical-align: middle!important">
                                    <form:input cssClass="form-control" cssStyle="width:70px;" path="updateCartItems[${index}].quantity"/>
                                    <form:errors path="updateCartItems[${index}].quantity" cssStyle="color: red;"/>
                                </td>
                                <td class="text-center" style="vertical-align: middle!important"><button class="btn btn-info" type="button" onclick="deleteFromCart(${phones[index].id})">Delete</button></td>
                            </tr>
                        </c:forEach>
                    </form:form>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <p style="color: red;">Your cart is empty, would you like to continue shopping?</p>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="container mt-3">
        <div class="float-right">
            <c:if test="${not empty phones}">
                <button class="btn btn-primary mx-2" onclick="updateCart()">Update</button>
                <a class="btn btn-primary mx-2" href="${pageContext.request.contextPath}/order">Order</a>
            </c:if>
        </div>
    </div>
</template:page>

