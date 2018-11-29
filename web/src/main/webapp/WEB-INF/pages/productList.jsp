<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <link rel="stylesheet" href="webjars/bootstrap/4.1.3/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="webjars/bootstrap-sortable/1.11.1/Contents/bootstrap-sortable.css">
    <script src="webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="webjars/bootstrap-sortable/1.11.1/Scripts/bootstrap-sortable.js"></script>
    <script src="webjars/bootstrap/4.1.3/js/bootstrap.min.js"></script>
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
<div class="row justify-content-end">
    <div class="col-md-2">
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
    <nav aria-label="Page navigation">
        <ul class="pagination justify-content-end">
            <li class="page-item">
                <a class="page-link"
                   href="?pageNumber=${empty pageNumber ? pageContext.request.getParameter("pageNumber") : pageNumber}&previousPage=true">Previous</a>
            </li>
            <li class="page-item"><a class="page-link" href="?pageNumber=1">1</a></li>
            <li class="page-item"><a class="page-link" href="?pageNumber=2">2</a></li>
            <li class="page-item"><a class="page-link" href="?pageNumber=3">3</a></li>
            <li class="page-item"><a class="page-link" href="?pageNumber=4">4</a></li>
            <li class="page-item"><a class="page-link" href="?pageNumber=5">5</a></li>
            <li class="page-item"><a class="page-link" href="?pageNumber=6">6</a></li>
            <li class="page-item"><a class="page-link" href="?pageNumber=7">7</a></li>
            <li class="page-item"><a class="page-link" href="?pageNumber=8">8</a></li>
            <li class="page-item"><a class="page-link" href="?pageNumber=9">9</a></li>
            <li class="page-item">
                <a class="page-link"
                   href="?pageNumber=${empty pageNumber ? pageContext.request.getParameter("pageNumber") : pageNumber}&nextPage=true">Next</a>
            </li>
        </ul>
    </nav>
    <div class="table-responsive">
        <table id="table" class="table table-striped table-bordered table-hover table-sm sortable" cellspacing="0" width="100%">
            <thead>
            <tr>
                <th>Image</th>
                <th data-defaultsign="reversed">Brand</th>
                <th>Model</th>
                <th>Color</th>
                <th>Display size</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Action</th>
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
    </div>
    <nav aria-label="Page navigation">
        <ul class="pagination justify-content-end">
            <li class="page-item">
                <a class="page-link"
                   href="?pageNumber=${empty pageNumber ? pageContext.request.getParameter("pageNumber") : pageNumber}&previousPage=true">Previous</a>
            </li>
            <li class="page-item"><a class="page-link" href="?pageNumber=1">1</a></li>
            <li class="page-item"><a class="page-link" href="?pageNumber=2">2</a></li>
            <li class="page-item"><a class="page-link" href="?pageNumber=3">3</a></li>
            <li class="page-item"><a class="page-link" href="?pageNumber=4">4</a></li>
            <li class="page-item"><a class="page-link" href="?pageNumber=5">5</a></li>
            <li class="page-item"><a class="page-link" href="?pageNumber=6">6</a></li>
            <li class="page-item"><a class="page-link" href="?pageNumber=7">7</a></li>
            <li class="page-item"><a class="page-link" href="?pageNumber=8">8</a></li>
            <li class="page-item"><a class="page-link" href="?pageNumber=9">9</a></li>
            <li class="page-item">
                <a class="page-link"
                   href="?pageNumber=${empty pageNumber ? pageContext.request.getParameter("pageNumber") : pageNumber}&nextPage=true">Next</a>
            </li>
        </ul>
    </nav>
</form>
</p>
</body>
</html>