<!doctype html>
<html lang="en">
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
    <head>
        <meta charset="UTF-8">
        <title>Product list</title>

        <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
        <script type="text/javascript" src="/resources/js/sortTable.js"></script>
        <link rel="stylesheet" href="/resources/bootstrap-4.1.3/css/bootstrap.min.css">
        <script src="/resources/bootstrap-4.1.3/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="/resources/tablesorter/jquery-latest.js"></script>
        <script type="text/javascript" src="/resources/tablesorter/jquery.tablesorter.js"></script>
    </head>
    <body>
        <div id="cartAndHeader" style="column-count: 2">
            <div>
                <h1>
                    Hello from product list!
                </h1>
            </div>
            <div style="text-align: right">
                <p>
                    <a href="#" >Login</a>
                </p>
                <form action="">
                    <button id="buttonCart" type="button" value="buttonCart" onclick="">My cart: ${cartItemsAmount} items ${cartItemsPrice}$</button>
                </form>
            </div>
        </div>
        <hr>
        <div id="searchAndSize" style="column-count: 2">
            <div>
                <h3>
                    Found ${phones.size()} phones
                </h3>
            </div>
            <div id="inputSearch" style="text-align: right">
                <form action="">
                    <input type="search" name="search" placeholder="Search">
                    <button id="buttonSearch" type="button" onclick="">Search</button>
                </form>
            </div>
        </div>
        <p>
        <div class="table-responsive" id="tablePhones">
            <table id="tableProducts" border="1px" width="100%" cellspacing="0" class="table tablesorter table-striped table-bordered table-hover">
                <thead>
                <tr id="headerTable">
                    <th id="image">Image</th>
                    <th id="brand">Brand</th>
                    <th id="model">Model</th>
                    <th id="color">Color</th>
                    <th id="displaySize">Display size</th>
                    <th id="price">Price</th>
                    <th id="quantity">Quantity</th>
                    <th id="action">Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="phone" items="${phones}">
                    <tr>
                        <td>
                            <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                        </td>
                        <td>${phone.brand}</td>
                        <td>
                            <a href="productDetails?phoneId=${phone.id}">${phone.model}</a>
                        </td>
                        <td>
                            <c:forEach var="color" items="${phone.colors}">
                                ${color.code}
                            </c:forEach>
                        </td>
                        <td>${phone.displaySizeInches}"</td>
                        <td>${phone.price}.00$</td>
                        <td style="text-align: center; width: 100px">
                            <input type="text" id="quantity${phone.id}" style="text-align: right; width: 90px" value="1">
                        </td>
                        <td style="width: 100px; text-align: center">
                            <button id="buttonAddTo${phone.id}" type="button" onclick="" style="width: 90px">Add to</button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <script>
                $(document).ready(function(){
                    $("#tableProducts").tablesorter({headers: { 0 : { sorter : false}, 3 : { sorter : false}, 6 : { sorter : false}, 7 : { sorter : false},}});
                });
            </script>
        </div>
        </p>
        <form method="">
            <div>
                <ul class="pagination justify-content-end">
                    <li class="page-item"><a class="page-link" href="">Previous</a></li>
                    <li class="page-item"><a class="page-link" href="?page=1">1</a></li>
                    <li class="page-item"><a class="page-link" href="?page=2">2</a></li>
                    <li class="page-item"><a class="page-link" href="?page=3">3</a></li>
                    <li class="page-item"><a class="page-link" href="?page=4">4</a></li>
                    <li class="page-item"><a class="page-link" href="?page=5">5</a></li>
                    <li class="page-item"><a class="page-link" href="?page=6">6</a></li>
                    <li class="page-item"><a class="page-link" href="?page=7">7</a></li>
                    <li class="page-item"><a class="page-link" href="?page=8">8</a></li>
                    <li class="page-item"><a class="page-link" href="?page=9">9</a></li>
                    <li class="page-item"><a class="page-link" href="">Next</a></li>
                </ul>
            </div>
        </form>

    </body>
</html>
