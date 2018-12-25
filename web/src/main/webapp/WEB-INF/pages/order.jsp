<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page>
    <div class="container">
        <h4>Cart</h4>
        <a class ="btn" href="<c:url value="/productList"/>"><h4>Back to product list</h4></a>
        <c:when test="${not empty orderForm.orderItems.size() ne 0}">
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
            <c:set var="orderItems" value="${orderForm.orderItems}"/>
            <c:forEach var="orderItem" items="${orderItems}">
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
                    <td>${orderItem.quantity}"</td>
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
                <td>${orderForm.delivery}$</td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td>TOTAL</td>
                <td>${orderForm.total}$</td>
            </tr>
            </tbody>
        </table>

        </c:when>
        <c:otherwise>

        </c:otherwise>
    </div>
</template:page>