<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
    <title>Cart</title>
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
            <a href="#">Login</a>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <a href="${pageContext.request.contextPath}/productList"><img src="<c:url value="/resources/img/logo.jpg"/>"></a>
        </div>
        <div class="col">
            <form method="GET" action="${pageContext.request.contextPath}/cart">
                <button class="float-right cart btn" type="submit">My cart: <span id="count-items">${cartStatus.countItems}</span> items <span id="price">${cartStatus.price}</span>$</button>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="col bold">Cart</div>
    </div>
    <div class="row">
        <div class="col">
            <form method="GET" action="${pageContext.request.contextPath}/productList">
                <input type="submit" value="Back to product list">
            </form>
        </div>
        <div class="col">
            <form method="GET" action="">
                <input type="submit" class="float-right" value="Order">
            </form>
        </div>
    </div>
    <form:form id="update" method="POST" action="${pageContext.request.contextPath}/cart" modelAttribute="cartList">
    <div class="row">
            <table id="cart-table">
                <thead>
                <tr>
                    <td>Brand</td>
                    <td>Model</td>
                    <td>Color</td>
                    <td>Display size</td>
                    <td>Price</td>
                    <td>Quantity</td>
                    <td>Action</td>
                </tr>
                </thead>
                <c:forEach var="phone" items="${items.keySet()}" varStatus="status">
                    <tr>
                        <td>${phone.brand}</td>
                        <td>${phone.model}</td>
                        <td>${phone.colors.toArray()[0].code}</td>
                        <td>${phone.displaySizeInches}</td>
                        <td>$ ${phone.price}</td>
                        <td>
                            <form:input path="cartPhones[${status.index}].quantity"/>
                            <form:errors path="cartPhones[${status.index}].quantity" element="div" cssClass="error"/>
                            <form:hidden path="cartPhones[${status.index}].phoneId"/>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/cart/delete/${phone.id}">
                                <input type="button" class="btn btn-default add-cart" value="Delete">
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
    </div>
    </form:form>
    <div class="row">
        <div class="col-10"></div>
        <div class="col-2 padding-button">
            <input form="update" name="update" type="submit" class="float-left" value="Update">
            <form method="GET" action="">
                <input type="submit" class="float-right" value="Order">
            </form>
        </div>
    </div>
</div>
</body>
</html>

