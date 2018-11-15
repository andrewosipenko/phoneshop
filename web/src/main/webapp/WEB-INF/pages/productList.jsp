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
<div class="table-responsive">
    <table class="table table-bordered">
    <thead>
    <tr>
        <td>Image</td>
        <td>Brand</td>
        <td>Model</td>
        <td>Price</td>
        <td>Colors</td>
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
            <td>$ ${phone.price}</td>
            <td>${phone.colors}</td>
        </tr>
    </c:forEach>
        </tbody>
</table>
</div>
</p>
</body>
</html>