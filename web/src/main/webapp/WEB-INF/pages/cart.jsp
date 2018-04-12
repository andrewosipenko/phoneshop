<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags/components" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<template:page title="Cart">
    <script> <%@ include file="scripts/updateCart.js" %> </script>
    <components:header cartShown="true"/>

    <div class="container mt-3 py-1">
        <div class="d-inline-block">
            <h3>Cart</h3>
        </div>
    </div>
    <div class="container mt-1">
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/productList">Back to product List</a>
    </div>

    <div class="container mt-3">
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
            <c:if test="${not empty phones}">
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
                            <td class="text-center" style="vertical-align: middle!important"><button class="btn btn-info" type="button" onclick="">Delete</button></td>
                        </tr>
                    </c:forEach>
                </form:form>
            </c:if>
            </tbody>
        </table>
    </div>

    <div class="container mt-3">
        <div class="float-right">
            <button class="btn btn-primary mx-2" onclick="updateCart()">Update</button>
            <button class="btn btn-primary mx-2">Order</button>
        </div>
    </div>
</template:page>

