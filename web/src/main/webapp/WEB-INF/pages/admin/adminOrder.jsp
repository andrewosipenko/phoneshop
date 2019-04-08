<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tags:master pageTitle="order" isShowCart="false">
    <div class="content">
        <a class="link-button button-on-page" href="${pageContext.servletContext.contextPath}/admin/orders">Back to orders</a>
        <div class="clearfix"/>
        <h2 class="left-content">Order number: ${order.id}</h2>
        <h2 class="right-content">Order status: ${order.status}</h2>
        <div class="clearfix"/>
        <table class="table table-bordered cart-table">
            <thead>
            <tr>
                <td>Brand</td>
                <td>Model</td>
                <td>Display size</td>
                <td>Price</td>
                <td>Quantity</td>
            </tr>
            </thead>
            <c:forEach var="orderItem" items="${order.orderItems}">
                <tr class="content-align">
                    <td>${orderItem.phone.brand}</td>
                    <td>${orderItem.phone.model}</td>
                    <td>${orderItem.phone.displaySizeInches}"</td>
                    <td>${orderItem.phone.price}$</td>
                    <td>${orderItem.quantity}</td>
                </tr>
            </c:forEach>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td>Subtotal</td>
                <td>${order.subtotal}$</td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td>Delivery</td>
                <td>${order.deliveryPrice}$</td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td>TOTAL</td>
                <td>${order.totalPrice}$</td>
            </tr>
        </table>
        <br>
        <div class="order-input">
            <p class="order-overview-info">First name: </p>
            <p class="order-overview-info">${order.firstName}</p><br><br>
            <p class="order-overview-info">Last name: </p>
            <p class="order-overview-info">${order.lastName}</p><br><br>
            <p class="order-overview-info">Address: </p>
            <p class="order-overview-info">${order.deliveryAddress}</p><br><br>
            <p class=order-overview-info>Phone: </p>
            <p class="order-overview-info">${order.contactPhoneNo}</p><br><br>
            <div class="additional-info-order">${order.additionalInfo}</div>
            <br>
        </div>
        <form method="post">
            <input type="hidden" name="_method" value="PUT"/>
            <button class="btn button-on-page" name="status" value="delivered">Delivered</button>
            <button class="btn button-on-page" name="status" value="rejected">Rejected</button>
        </form>
    </div>
</tags:master>