<!doctype html>
<html lang="en">
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <head>
        <meta charset="UTF-8">
        <title>Product list</title>
        <link type="text/css" rel="stylesheet" media="screen" href="css/style.css">
        <link rel="stylesheet" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    </head>
    <body>
        <p>
            Hello from product list!
        </p>
        <p>
            Found
            <c:out value="${phones.size()}"/> phones.
            <table border="1px">
                <thead>
                <tr>
                    <td>Image</td>
                    <td>Brand</td>
                    <td>Model</td>
                    <td>Price</td>
                    <td>Colors</td>
                </tr>
                </thead>
                <c:forEach var="phone" items="${phones}">
                    <tr>
                        <td>
                            <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                        </td>
                        <td>${phone.brand}</td>
                        <td>${phone.model}</td>
                        <td>$ ${phone.price}</td>
                        <td>
                            <c:forEach var="color" items="${phone.colors}">
                                ${color.code}
                            </c:forEach>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </p>

    </body>
</html>
