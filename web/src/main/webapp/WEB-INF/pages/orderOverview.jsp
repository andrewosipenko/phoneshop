<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/templates" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<template:page>
    <div class="container">
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
                    <c:forEach var="orderItem" items="${order.orderItems}">
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
                                    value="${not empty orderItem.phone.price ? orderItem.phone.price : 0}"/></strong></td>
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
                <table class="table table-hover">
                    <tbody>
                        <tr>
                            <td>
                                <p>First name</p>
                            </td>
                            <td>
                                <p><c:out value="${order.firstName}"/></p>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <p>Last name</p>
                            </td>
                            <td>
                                <p><c:out value="${order.lastName}"/></p>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <p>Delivery address</p>
                            </td>
                            <td>
                                <p><c:out value="${order.deliveryAddress}"/></p>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <p>Phone</p>
                            </td>
                            <td>
                                <p><c:out value="${order.contactPhoneNo}"/></p>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <p>Additional info</p>
                            </td>
                            <td>
                                <p><c:out value="${order.additionalInfo}"/></p>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <a href="<c:url value="/productList"/>" class="btn btn-primary">
                    Back to shopping
                </a>
            </div>
        </div>
    </div>
</template:page>