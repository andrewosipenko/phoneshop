<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/templates" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<template:page>
    <div class="container">
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">
                <strong><c:out value="${errorMessage}"/></strong>
            </div>
        </c:if>
        <div class="row">
            <div class="col-sm-12 col-md-10 col-md-offset-1">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>Product</th>
                        <th>Quantity</th>
                        <th class="text-center">Price</th>
                        <th class="text-center">Total</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="orderItem" items="${order.orderItems}" varStatus="i">
                        <tr>
                            <td class="col-sm-8 col-md-6">
                                <div class="media">
                                    <a class="thumbnail pull-left"
                                       href="<c:url value="/productDetails/${orderItem.phone.id}"/>">
                                        <img class="media-object"
                                             src="<c:url value="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${orderItem.phone.imageUrl}"/>"
                                             style="width: 72px; height: 72px;"> </a>
                                    <div class="media-body pl-2">
                                        <h5 class="media-heading"><a
                                                href="<c:url value="/productDetails/${orderItem.phone.id}"/>"><c:out
                                                value="${orderItem.phone.model}"/></a>
                                        </h5>
                                        <h6 class="media-heading"> by <c:out value="${orderItem.phone.brand}"/></h6>
                                    </div>
                                </div>
                            </td>
                            <td class="col-sm-1 col-md-1" style="text-align: center">
                                <c:out value="${orderItem.quantity}"/>
                            </td>
                            <td class="col-sm-1 col-md-1 text-center"><strong>$<c:out
                                    value="${orderItem.phone.price}"/></strong></td>
                            <td class="col-sm-1 col-md-1 text-center"><strong>$<c:out
                                    value="${orderItem.total}"/></strong></td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td class="text-right"><h3>Subtotal</h3></td>
                        <td></td>
                        <td></td>
                        <td class="text-right"><h3><strong>$<c:out
                                value="${order.subtotal}"/></strong></h3></td>
                    </tr>
                    <tr>
                        <td class="text-right"><h3>Delivery price</h3></td>
                        <td></td>
                        <td></td>
                        <td class="text-right"><h3><strong>$<c:out
                                value="${order.deliveryPrice}"/></strong></h3></td>
                    </tr>
                    <tr>
                        <td class="text-right"><h3>Total price</h3></td>
                        <td></td>
                        <td></td>
                        <td class="text-right"><h3><strong>$<c:out
                                value="${order.totalPrice}"/></strong></h3></td>
                    </tr>
                    </tbody>
                </table>
                <form:form method="post" modelAttribute="order">
                    <div class="form-group">
                        <label for="firstName">First name*</label>
                        <form:input path="firstName" class="form-control" id="firstName"/>
                        <div class="error-message" id="error-message">
                            <form:errors path="firstName"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="lastName">Last name*</label>
                        <form:input path="lastName" class="form-control" id="lastName"/>
                        <div class="error-message" id="error-message">
                            <form:errors path="lastName"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="deliveryAddress">Delivery address*</label>
                        <form:input path="deliveryAddress" class="form-control" id="deliveryAddress"/>
                        <div class="error-message" id="error-message">
                            <form:errors path="deliveryAddress"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="contactPhoneNo">Phone*</label>
                        <form:input path="contactPhoneNo" class="form-control" id="contactPhoneNo"/>
                        <div class="error-message" id="error-message">
                            <form:errors path="contactPhoneNo"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="additionalInfo">Additional info</label>
                        <form:textarea path="additionalInfo" class="form-control" id="additionalInfo"/>
                        <div class="error-message" id="error-message">
                            <form:errors path="additionalInfo"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-success">
                            Order
                        </button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</template:page>