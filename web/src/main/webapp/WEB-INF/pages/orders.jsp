<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags/components" %>

<template:page title="Orders">
    <components:header cartShown="false" titleEnabled="true" title="Orders"/>

    <div class="container mt-3">
        <c:choose>
            <c:when test="${not empty orders}">
                <table class="table table-bordered table-striped">
                    <thead style="background-color: #828082;">
                    <tr class="d-table-row text-light text-center">
                        <th scope="col" style="width: 10%">Order id</th>
                        <th scope="col" style="width: 19%">Customer</th>
                        <th scope="col" style="width: 15%">Phone</th>
                        <th scope="col" style="width: 20%">Delivery address</th>
                        <th scope="col" style="width: 10%">Total price</th>
                        <th scope="col" style="width: 10%">Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${orders}" var="order">
                        <tr>
                            <td style="vertical-align: middle!important"><a href="${pageContext.request.contextPath}/admin/orders/${order.id}"><c:out value="${order.id}"/></a></td>
                            <td style="vertical-align: middle!important"><c:out value="${order.firstName} ${order.lastName}"/></td>
                            <td style="vertical-align: middle!important"><c:out value="${order.contactPhoneNo}"/></td>
                            <td style="vertical-align: middle!important"><c:out value="${order.deliveryAddress}"/></td>
                            <td style="vertical-align: middle!important">$<c:out value="${order.totalPrice}"/></td>
                            <td style="vertical-align: middle!important"><c:out value="${order.status}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <p style="color: red;">No orders placed</p>
            </c:otherwise>
        </c:choose>
    </div>
</template:page>

