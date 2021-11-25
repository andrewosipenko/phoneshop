<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<tags:page pageTitle="Admin Order Page">

    <c:choose>
        <c:when test="${order == null || order.orderItems == null || order.orderItems.size() <= 0}">
            <p><em>No items in order</em></p>
        </c:when>
        <c:otherwise>
            <div class="clearfix" style="margin: 10px">
                <div class="float-left">
                    <h4 style="margin: 10px">Order number: ${order.id}</h4>
                </div>
                <div class="float-right">
                    <h4 style="margin: 10px">Order status: ${order.status}</h4>
                </div>
            </div>
            <c:set var="orderItems" value="${order.orderItems}"/>
            <table class="table table-striped table-bordered table-sm" style="text-align: left">
                <thead>
                <tr>
                    <td style="width: 20%">Brand</td>
                    <td style="width: 13%">Model</td>
                    <td style="width: 14%">Color</td>
                    <td style="width: 10%">Display size</td>
                    <td style="width: 10%">Quantity</td>
                    <td style="width: 10%">Price</td>
                </tr>
                </thead>
                <c:forEach var="orderItem" items="${orderItems}">
                    <c:set var="phone" value="${orderItem.phone}"/>
                    <c:set var="phoneId" value="${phone.id}"/>
                    <tr>
                        <td>${phone.brand}</td>
                        <td>${phone.model}</td>
                        <td>
                            <c:forEach var="color" items="${phone.colors}" varStatus="status">
                                <c:out value="${color.code}"/>
                                <c:if test="${not status.last}">, </c:if>
                            </c:forEach>
                        </td>
                        <td>${phone.displaySizeInches}"</td>
                        <td>${orderItem.quantity}</td>
                        <td>${phone.price}$</td>
                    </tr>
                </c:forEach>
            </table>

            <div class="float-right">
                <table class="table table-striped table-bordered table-sm" style="text-align: left">
                    <col width="195">
                    <col width="200">
                    <tr>
                        <th scope="row">Subtotal</th>
                        <td>${order.subtotal}$</td>
                    </tr>
                    <tr>
                        <th scope="row">Delivery</th>
                        <td>${order.deliveryPrice}$</td>
                    </tr>
                    <tr>
                        <th scope="row">TOTAL</th>
                        <td>${order.totalPrice}$</td>
                    </tr>
                </table>
            </div>

            <br><br><br><br><br><br>
            <p style="margin: 20px" class="text-justify">First name: ${order.firstName}</p>
            <p style="margin: 20px" class="text-justify">Last name: ${order.lastName}</p>
            <p style="margin: 20px" class="text-justify">Address: ${order.deliveryAddress}</p>
            <p style="margin: 20px" class="text-justify">Phone: ${order.contactPhoneNo}</p>
            <p style="margin: 20px; max-width: 40%; word-wrap:break-word;"
               class="text-justify">${order.additionalInfo}</p>
            <form:form method="POST">
                <a href="<c:url value="/admin/orders"/> " style="margin-left: 5px" class="btn btn-secondary btn-lg" role="button">
                    Back
                </a>
                <button type="submit" name="orderStatus" value="DELIVERED" class="btn btn-secondary btn-lg">
                    Delivered
                </button>
                <button type="submit" name="orderStatus" value="REJECTED" class="btn btn-secondary btn-lg">
                    Rejected
                </button>
            </form:form>
        </c:otherwise>
    </c:choose>
</tags:page>
