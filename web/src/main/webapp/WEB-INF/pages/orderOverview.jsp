<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tags:master pageTitle="OrderOverview" isShowCart="false">
    <div class="content">
        <c:choose>
            <c:when test="${order == null}">
                No order with such id
            </c:when>
            <c:otherwise>
                <h1>Thank you for your order</h1>
                <br>
                <h2>${order.id}</h2>
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
                    <p class="order-overview-info">First name: </p><p class="order-overview-info">${order.firstName}</p><br><br>
                    <p class="order-overview-info">Last name: </p><p class="order-overview-info">${order.lastName}</p><br><br>
                    <p class="order-overview-info">Address: </p><p class="order-overview-info">${order.deliveryAddress}</p><br><br>
                    <p class=order-overview-info>Phone: </p><p class="order-overview-info">${order.contactPhoneNo}</p><br><br>
                    <div class="additional-info-order">${order.additionalInfo}</div><br>
                </div>
            </c:otherwise>
        </c:choose>
        <tags:backToProductList text="back to shopping"/>
    </div>
</tags:master>
