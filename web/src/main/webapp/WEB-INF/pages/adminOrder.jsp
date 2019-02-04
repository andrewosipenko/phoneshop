<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<template:page>
    <div class="container">
        <h4>Admin order</h4>
        <h4>Order number: ${order.id}</h4>
        <h4>Order status: ${order.status}</h4>
        <br>
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
            <c:set var="orderItems" value="${order.orderItems}"/>
            <tbody>
            <c:forEach var="orderItem" items="${orderItems}">
                <tr>
                    <td>${orderItem.phone.brand}</td>
                    <td>${orderItem.phone.model}</td>
                    <td>
                        <c:forEach var="color" items="${orderItem.phone.colors}" varStatus="counter">
                            ${color.code}
                            <c:if test="${counter.count != phone.colors.size()}">
                                ,
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>${orderItem.phone.displaySizeInches}</td>
                    <td>${orderItem.quantity}</td>
                    <td>${orderItem.phone.price}</td>
                </tr>
            </c:forEach>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td>Subtotal</td>
                <td>${order.subtotal}</td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td>Subtotal</td>
                <td>${order.deliveryPrice}</td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td>Subtotal</td>
                <td>${order.totalPrice}</td>
            </tr>
            </tbody>
        </table>
        <table>
            <tr>
                <td>First name</td>
                <td>${order.firstName}</td>
            </tr>
            <tr>
                <td>Last name</td>
                <td>${order.lastName}</td>
            </tr>
            <tr>
                <td>Address</td>
                <td>${order.deliveryAddress}</td>
            </tr>
            <tr>
                <td>Phone</td>
                <td>${order.contactPhoneNo}</td>
            </tr>
            <tr>
                <td>Additional Information</td>
                <td>${order.additionalInformation}</td>
            </tr>
        </table>
        <a class ="btn" href="<c:url value="/admin/orders"/>"><h4>Back</h4></a>
        <form method="post">
            <input type="submit" <c:if test='${order.status != "NEW"}'> disabled </c:if> name="orderStatus" value="Delivered">
            <input type="submit" <c:if test='${order.status != "NEW"}'> disabled </c:if> name="orderStatus" value="Rejected">
        </form>
    </div>
</template:page>