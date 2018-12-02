<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
    <link rel="stylesheet" href="webjars/bootstrap/4.1.3/css/bootstrap.min.css"/>
    <script src="webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="webjars/bootstrap/4.1.3/js/bootstrap.min.js"></script>
    <title>Product list</title>
</head>
<body>
<div id="cart" style="text-align: right">My cart: ${cartItemsAmount} items $${cartItemsPrice}</div>
<p>
    Hello from Cart!
</p>
<form action="<c:url value="productList"/>">
    <button>Back to Product List</button>
</form>
<p>
<form method="post">
    <div class="table-responsive">
        <table id="table" class="table table-striped table-bordered table-hover table-sm sortable" cellspacing="0"
               width="100%">
            <thead>
            <tr>
                <th>Brand</th>
                <th>Model</th>
                <th>Color</th>
                <th>Display size</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="phone" items="${phones}" varStatus="status">
                <tr>
                    <td>${phone.brand}</td>
                    <td>
                            ${phone.model}
                        <br>
                        <a href="productDetails?phoneId=${phone.id}">More...</a>
                    </td>
                    <td>
                        <c:forEach var="color" items="${phone.colors}">
                            ${color}
                        </c:forEach>
                    </td>
                    <td>${phone.displaySizeInches}"</td>
                    <td>$ ${phone.price}</td>
                    <td>
                        <input type="text" id=quantity${phone.id} style="text-align: right" value="${cartItems.get(status.index).quantity}"/>
                        <br>
                        <label for=quantity${phone.id} id=label${phone.id}></label>
                    </td>
                    <td>
                        <button type="button">Delete</button>
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