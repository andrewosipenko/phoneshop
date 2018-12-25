<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<template:page>
    <div class="container">
        <h4>Cart</h4>
        <a class ="btn" href="<c:url value="/productList"/>"><h4>Back to product list</h4></a>
        <c:when test="${not empty updateCart.cartItems}">
            <form:form method="post" action="${pageContext.request.contextPath}/cart/update" modelAttribute="updateCart">
                <c:set var="cartItems" value="${updateCart.cartItems}"/>
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
                        <c:forEach begin="0" end="${cartItems.size() - 1}" var="index">
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
                                <td style="text-align: center; width: 100px">
                                    <input class="text-input" name="quantityCart" id="quantityCart${phone.id}" style="text-align: right; width: 90px; margin-top: 18px !important;" value="${cartItems[index].quantity}"><br>
                                    <p class="text-danger" id="errorMessage${phone.id}"></p>
                                </td>
                                <td>
                                    <input formmethod="post" formaction="<c:url value="/cart/delete"/>?phoneId=${phones[index].id}" type="submit" value="Delete">
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            <input type="submit" class="btn" value="Update">
            </form:form>
            <a class ="btn" href="<c:url value="/order"/>">Order</a>
        </c:when>
        <c:otherwise>
            <h3>Cart is empty</h3>
        </c:otherwise>
    </div>
</template:page>