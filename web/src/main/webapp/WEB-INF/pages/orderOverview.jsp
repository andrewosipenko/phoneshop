<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Order confirmation #${order.id}</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="<c:url value="/resources/js/product_list.js"/>"></script>
        <script src="<c:url value="/resources/js/jquery.number.js"/>"></script>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col">
                    <a href="${pageContext.request.contextPath}/productList"><img src="<c:url value="/resources/img/logo.jpg"/>"></a>
                </div>
            </div>
            <div class="row">
                <div class="col bold">Thank you for your order</div>
            </div>
            <div class="row">
                <div class="col bold">Order number: ${order.id}</div>
            </div>
            <div class="row">
                <table id="order-table">
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
                    <c:forEach var="orderItem" items="${order.orderItems}">
                        <tr>
                            <td>${phones.get(orderItem.phoneId).brand}</td>
                            <td>${phones.get(orderItem.phoneId).model}</td>
                            <td>
                                <c:forEach var="color" items="${phones.get(orderItem.phoneId).colors}">
                                    ${color.code}
                                </c:forEach>
                            </td>
                            <td>${phones.get(orderItem.phoneId).displaySizeInches}''</td>
                            <td>${orderItem.quantity}</td>
                            <td><span class="price">${phones.get(orderItem.phoneId).price * orderItem.quantity}</span>$</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td class="empty-td"></td>
                        <td class="empty-td"></td>
                        <td class="empty-td"></td>
                        <td class="empty-td"></td>
                        <td>Subtotal</td>
                        <td><span class="price">${order.subtotal}</span>$</td>
                    </tr>
                    <tr>
                        <td class="empty-td"></td>
                        <td class="empty-td"></td>
                        <td class="empty-td"></td>
                        <td class="empty-td"></td>
                        <td>Delivery</td>
                        <td><span class="price">${order.deliveryPrice}</span>$</td>
                    </tr>
                    <tr>
                        <td class="empty-td"></td>
                        <td class="empty-td"></td>
                        <td class="empty-td"></td>
                        <td class="empty-td"></td>
                        <td>TOTAL</td>
                        <td><span class="price">${order.totalPrice}</span>$</td>
                    </tr>
                </table>
            </div>

            <div class="row">
                <div class="col-3">
                    <table id="person-info">
                        <tr>
                            <td>First name:</td>
                            <td>${order.firstName}</td>
                        </tr>
                        <tr>
                            <td>Last name:</td>
                            <td>${order.lastName}</td>
                        </tr>
                        <tr>
                            <td>Address:</td>
                            <td>${order.deliveryAddress}</td>
                        </tr>
                        <tr>
                            <td>Phone:</td>
                            <td>${order.contactPhoneNo}</td>
                        </tr>
                        <tr>
                            <td colspan="2" align="left">${order.additionInfo}</td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <form method="GET" action="${pageContext.request.contextPath}/productList">
                        <input type="submit" value="Back to shopping">
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>