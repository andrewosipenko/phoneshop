<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Orders</title>
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
                <div class="col-12 login">
                    <c:if test="${pageContext.request.userPrincipal.name == null}"><a href="${pageContext.request.contextPath}/login">Login</a></c:if>
                    <c:if test="${pageContext.request.userPrincipal.name != null}">
                        ${pageContext.request.userPrincipal.name} | <a href="${pageContext.request.contextPath}/admin/orders">ADMIN</a> | <a href="${pageContext.request.contextPath}/logout">Logout</a>
                    </c:if>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <a href="${pageContext.request.contextPath}/productList"><img src="<c:url value="/resources/img/logo.jpg"/>"></a>
                </div>
            </div>
            <div class="row">
                <div class="col bold">Orders</div>
            </div>
            <div class="row">
                <table id="order-table">
                    <thead>
                    <tr>
                        <td>Order number</td>
                        <td>Customer</td>
                        <td>Phone</td>
                        <td>Address</td>
                        <td>Date</td>
                        <td>Total price</td>
                        <td>Status</td>
                    </tr>
                    </thead>
                    <c:forEach var="order" items="${orders}">
                        <tr>
                            <td><a class="order-href" href="${pageContext.request.contextPath}/admin/orders/${order.id}">${order.id}</a></td>
                            <td>${order.firstName} ${order.lastName}</td>
                            <td>${order.contactPhoneNo}</td>
                            <td>${order.deliveryAddress}</td>
                            <td><fmt:formatDate value="${order.date}" pattern="dd.MM.yyyy HH:mm"/></td>
                            <td><span class="price">${order.totalPrice}</span>$</td>
                            <td>${order.status.name()}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </body>
</html>