<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: sashayukhimchuk
  Date: 2019-07-23
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <h2 align="center">Cart</h2>
<div style="width: 100%;">
    <div style="float: left;">
        <form action="/productList/1">
        <button type="submit">Back to product list</button>
        </form>
    </div>
    <div style="float: right">
        <button>My cart: ${cart.totalCount} items ${cart.totalPrice}$</button>
    </div>
</div>
<hr/>
    <c:choose>
        <c:when test="${cart.totalCount > 0}">
            <table border="1px" align="center">
                <thead>
                <tr>
                    <td>Image</td>
                    <td>Brand</a>
                    </td>
                    <td>Model</a>
                    </td>
                    <td>Colors</td>
                    <td>Display Size</a>
                    </td>
                    <td>Price</a>
                    </td>
                    <td>Quantity</td>
                    <td>Action</td>
                </tr>
                </thead>
                <c:forEach var="entry" items="${phonesAndCount}">
                    <tr>
                        <td>
                            <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${entry.key.imageUrl}"
                                 style="width: 55%; height: 55%">
                        </td>
                        <td>${entry.key.brand}</td>
                        <td><a href="/productDetails/${entry.key.id}">${entry.key.model}</a></td>
                        <td>
                            <c:forEach var="color" items="${entry.key.colors}" varStatus="i">
                                <c:choose>
                                    <c:when test="${(fn:length(entry.key.colors) - i.count) gt 0}">
                                        ${color.code},
                                    </c:when>
                                    <c:otherwise>
                                        ${color.code}
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </td>
                        <td>${entry.key.displaySizeInches}"</td>
                        <td>${entry.key.price}$</td>
                        <form action="/cart/delete" method="post">
                            <td>
                                <input type="hidden" name="phoneId" value="${entry.key.id}">
                                <input type="text" value="${entry.value}">
                                <p/>
                            </td>
                            <td>
                                <button type="submit">Delete</button>
                            </td>
                        </form>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            <h2 align="center">Cart is empty</h2>
        </c:otherwise>
    </c:choose>

</body>
</html>
