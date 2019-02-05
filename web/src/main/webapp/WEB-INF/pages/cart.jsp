<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<template:page>
    <div class="container">
        <h4>Cart</h4>
        <a class ="btn" href="<c:url value="/productList"/>"><h4>Back to product list</h4></a>
        <c:choose>
        <c:when test="${not empty updateCartForm.cartFormList}">
            <form:form method="post" action="${pageContext.request.contextPath}/cart/update" modelAttribute="updateCartForm">
                <c:set var="cartFormList" value="${updateCartForm.cartFormList}"/>
                <div class="table-responsive" id="tablePhonesCart">
                    <table id="tableProductsCart" border="1px" width="100%" cellspacing="0" class="table table-striped table-bordered table-hover">
                        <thead>
                            <tr id="headerTable">
                                <td>Brand</td>
                                <td>Model</td>
                                <td>Color</td>
                                <td>Display size</td>
                                <td>Price</td>
                                <td>Quantity</td>
                                <td>Action</td>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach begin="0" end="${cartFormList.size() - 1}" var="index">
                            <form:hidden path="cartFormList['${index}'].phoneId" value="${cartFormList[index].phoneId}"/>
                            <tr>
                                <td>${phones[index].brand}</td>
                                <td>
                                    <a class="hyperlink" href="<c:url value="/productDetails/phoneId=${phones[index].id}"/>">${phones[index].model}</a>
                                </td>
                                <td>
                                    <c:forEach var="color" items="${phones[index].colors}" varStatus="counter">
                                        ${color.code}
                                        <c:if test="${counter.count != phones[index].colors.size()}">
                                            ,
                                        </c:if>
                                    </c:forEach>
                                </td>
                                <td>${phones[index].displaySizeInches}"</td>
                                <td>${phones[index].price}.00$</td>
                                <td id="quantityArea">
                                    <form:input class="text-input" cssStyle="margin-top: 9px !important;" path="cartFormList['${index}'].quantity" value="${cartFormList[index].quantity}"/><br>
                                    <p class="text-danger"}>${errors[index]}</p>
                                </td>
                                <td>
                                    <input formmethod="post" formaction="<c:url value="/cart/delete"/>?phoneId=${phones[index].id}" type="submit" value="Delete">
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <input type="submit" value="Update" style="width: 100px;">
            </form:form>
            <br>
            <form>
                <input type="button" value="Order" onClick='location.href="<c:url value="/order"/>"' style="width: 100px">
            </form>
        </c:when>
        <c:otherwise>
            <div id="emptyCart">
                <h3>Cart is empty</h3>
            </div>
        </c:otherwise>
        </c:choose>
    </div>
</template:page>