<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <link rel="stylesheet" href="webjars/bootstrap/3.2.0/css/bootstrap.min.css"/>
    <script src="webjars/jquery/1.11.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <c:set var="cartItemsAmount" scope="session" value="'My cart: 0 items'"/>
    <title>Product list</title>
    <script type="text/javascript">
        function addToCart(phoneId, quantity) {
            var data = {phoneId: phoneId, quantity: quantity};
            $.ajax({
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                url: "ajaxCart",
                method: "POST",
                dataType: 'json',
                data: JSON.stringify(data)
            })
                .done(function (data, textStatus, jqXHR) {
                    setLabel(phoneId, quantity, data.message);
                    if (data.hasOwnProperty('cartItemsAmount')) {
                        var cartInfo = "My cart: " + data.cartItemsAmount + " items";
                        $('#cart').text(cartInfo);
                    }
                })
                .fail(function (jqXHR, textStatus, errorThrown) {
                    $('#result').text("Something is wrong: " + textStatus + " " + errorThrown);
                });
        }
    </script>
    <script type="text/javascript">
        function setLabel(phoneId, quantity, message) {
            if (message === 'success') {
                document.getElementById("label" + phoneId).innerHTML = '<p style="color: green">Successfully adding ' + quantity + ' products to cart</p>';
            } else {
                document.getElementById("label" + phoneId).innerHTML = '<p style="color: red">' + message + '</p>';
            }
        }
    </script>
</head>
<body>
<div id="cart" style="text-align: right">My cart: ${cartItemsAmount} items</div>
<p>
    Hello from product list!
</p>
<div id="result"></div>
<p>
    Found
    <c:out value="${phones.size()}"/> phones.
<div class="row">
    <div class="col-md-2 col-md-offset-9">
        <form method="post" class="search-form">
            <div class="form-group has-feedback">
                <label for="search" class="sr-only">Search</label>
                <input type="text" class="form-control" name="search" id="search" placeholder="search">
                <span class="glyphicon glyphicon-search form-control-feedback"></span>
                <button type="button" style="alignment: right"
                        onclick="location.href='?search='+document.getElementById('search').value">Search!
                </button>
            </div>
        </form>
    </div>
</div>
<form method="post">
    <ul class="pagination pager">
        <li>
            <a href="?pageNumber=${empty pageNumber ? pageContext.request.getParameter("pageNumber") : pageNumber}&previousPage=true">Previous</a>
        </li>
        <li><a href="?pageNumber=1">1</a></li>
        <li><a href="?pageNumber=2">2</a></li>
        <li><a href="?pageNumber=3">3</a></li>
        <li><a href="?pageNumber=4">4</a></li>
        <li><a href="?pageNumber=5">5</a></li>
        <li><a href="?pageNumber=6">6</a></li>
        <li><a href="?pageNumber=7">7</a></li>
        <li><a href="?pageNumber=8">8</a></li>
        <li><a href="?pageNumber=9">9</a></li>
        <li>
            <a href="?pageNumber=${empty pageNumber ? pageContext.request.getParameter("pageNumber") : pageNumber}&nextPage=true">Next</a>
        </li>
    </ul>
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
                        <input type="text" id=quantity${phone.id} style="text-align: right" value="1"/>
                        <br>
                        <label for=quantity${phone.id} id=label${phone.id}></label>
                    </td>
                    <td>
                        <button type="button"
                                onclick="addToCart(${phone.id}, document.getElementById('quantity${phone.id}').value)">
                            Add
                            to
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <ul class="pagination pager">
            <li><a href="?pageNumber=${pageContext.request.getParameter("pageNumber")}&previousPage=true">Previous</a>
            </li>
            <li><a href="?pageNumber=1">1</a></li>
            <li><a href="?pageNumber=2">2</a></li>
            <li><a href="?pageNumber=3">3</a></li>
            <li><a href="?pageNumber=4">4</a></li>
            <li><a href="?pageNumber=5">5</a></li>
            <li><a href="?pageNumber=6">6</a></li>
            <li><a href="?pageNumber=7">7</a></li>
            <li><a href="?pageNumber=8">8</a></li>
            <li><a href="?pageNumber=9">9</a></li>
            <li><a href="?pageNumber=${pageContext.request.getParameter("pageNumber")}&nextPage=true">Next</a></li>
        </ul>

    </div>
</form>
</p>
</body>
</html>