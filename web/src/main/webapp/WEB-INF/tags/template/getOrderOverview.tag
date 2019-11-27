<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ attribute name="order" type="com.es.core.model.order.Order" required="true" %>
<p>
<h5>Order number: ${order.id}</h5>
<h5>Order status: ${order.status}</h5>
</p>
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
    <c:forEach var="orderItem" items="${orderItems}">
        <tr>
            <td>${orderItem.phone.brand}</td>
            <td>${orderItem.phone.model}</td>
            <td>
                <template:getColors phoneColors="${orderItem.phone.colors}"/>
            </td>
            <td>${orderItem.phone.displaySizeInches}"</td>
            <td>${orderItem.quantity}</td>
            <td>${orderItem.phone.price} $</td>
        </tr>
    </c:forEach>
    <tr>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td>Subtotal</td>
        <td>${order.subtotal} $</td>
    </tr>
    <tr>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td>Delivery price</td>
        <td>${order.deliveryPrice} $</td>
    </tr>
    <tr>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td>TOTAL</td>
        <td>${order.totalPrice} $</td>
    </tr>
</table>
<table>
    <tr>
        <td>First name*</td>
        <td>${order.firstName}</td>
    </tr>
    <tr>
        <td>Last name*</td>
        <td>${order.lastName}</td>
    </tr>
    <tr>
        <td>Address*</td>
        <td>${order.deliveryAddress}</td>
    </tr>
    <tr>
        <td>Phone*</td>
        <td>${order.contactPhoneNo}</td>
    </tr>
    <tr>
        <td>Extra information:</td>
        <td>${order.extraInfo}</td>
    </tr>
</table>