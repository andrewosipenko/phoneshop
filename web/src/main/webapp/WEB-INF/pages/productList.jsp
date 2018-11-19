<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <link rel="stylesheet" href="webjars/bootstrap/3.2.0/css/bootstrap.min.css"/>
    <title>Product list</title>
</head>
<body>
<p>
    Hello from product list!
</p>
<p>
    Found
    <c:out value="${phones.size()}"/> phones.
<form action="/ajaxCart" method="post">
<div class="table-responsive">
    <table class="table table-bordered">
    <thead>
    <tr>
        <td>Image</td>
        <td>Brand</td>
        <td>Model</td>
        <td>Color</td>
        <td>Display size</td>
        <td>Price</td>
        <td>Quantity</td>
        <td>Action</td>
    </tr>
    </thead>
        <tbody>
    <c:forEach var="phone" items="${phones}">
        <tr>
            <td>
                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
            </td>
            <td>${phone.brand}</td>
            <td>${phone.model}</td>
            <td>
                <c:forEach var="color" items="${phone.colors}">
                    ${color}
                </c:forEach>
            </td>
            <td>${phone.displaySizeInches}"</td>
            <td>$ ${phone.price}</td>
            <td>
                <input type = "text" name = "quantity" id = quantity style = "text-align: right" value = "1"/>
            </td>
            <td>
                <button id = "button">Add to</button>
            </td>
        </tr>
    </c:forEach>
        </tbody>
</table>
</div>
</form>
</p>
</body>
</html>