<!doctype html>
<html lang="en">
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
    <head>
        <meta charset="UTF-8">
        <title>Product list</title>

        <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
        <script type="text/javascript" src="/resources/js/AddToCart.js"></script>
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
                <form action="<c:url value="/cart"/>">
                    <button id="buttonCart" type="submit" value="buttonCart">
                        My cart: <a id="cartItemsAmount">${empty sessionScope.get("scopedTarget.cart") ? '0' : sessionScope.get("scopedTarget.cart").cartItemsAmount}</a> items,
                        price: <a id="cartItemsPrice">${empty sessionScope.get("scopedTarget.cart").cartItemsPrice ? '0' : sessionScope.get("scopedTarget.cart").cartItemsPrice}</a> $
                    </button>
                </form>
            </div>
        </div>
        <hr>
        <div id="search" style="alignment: right; text-align: right">
                <form action="<c:url value="/productList"/>" method="get">
                    <input type="search" name="search" placeholder="Search" value="${searchText}">
                    <button id="buttonSearch" type="submit" onclick="">Search</button>
                </form>
        </div>
        <p>
        <div class="table-responsive" id="tablePhones">
            <table id="tableProducts" border="1px" width="100%" cellspacing="0" class="table tablesorter table-striped table-bordered table-hover">
                <thead>
                <tr id="headerTable">
                    <td id="image">Image</td>
                    <td id="brand">
                        <a href="<c:url value="/productList"/>?page=${page}&sort=brand<c:if test="${direction == 'asc'}">&direction=desc</c:if>&search=${searchText}">Brand</a>
                    </td>
                    <td id="model">
                        <a href="<c:url value="/productList"/>?page=${page}&sort=model<c:if test="${direction == 'asc'}">&direction=desc</c:if>&search=${searchText}">Model</a>
                    </td>
                    <td id="color">Color</td>
                    <td id="displaySize">
                        <a href="<c:url value="/productList"/>?page=${page}&sort=displaySizeInches<c:if test="${direction == 'asc'}">&direction=desc</c:if>&search=${searchText}">Display size</a>
                    </td>
                    <td id="price">
                        <a href="<c:url value="/productList"/>?page=${page}&sort=price<c:if test="${direction == 'asc'}">&direction=desc</c:if>&search=${searchText}">Price</a>
                    </td>
                    <td id="quantity">Quantity</td>
                    <td id="action">Action</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="phone" items="${phones}">
                    <tr>
                        <td>
                            <img width="100" height="100" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                        </td>
                        <td>${phone.brand}</td>
                        <td>
                            <a class="hyperlink" href="<c:url value="/productDetails/phoneid=${phone.id}"/>" style="color: #000000">${phone.model}</a>
                        </td>
                        <td>
                            <c:forEach var="color" items="${phone.colors}" varStatus="counter">
                                ${color.code}
                                <c:if test="${counter.count != phone.colors.size()}">
                                    ,
                                </c:if>
                            </c:forEach>
                        </td>
                        <td>${phone.displaySizeInches}"</td>
                        <td>${phone.price}.00$</td>
                        <td style="text-align: center; width: 100px">
                            <input class="text-input" name="quantity" id="quantity${phone.id}" style="text-align: right; width: 90px; margin-top: 18px !important;" value="1"><br>
                            <p class="text-danger" id="errorMessage${phone.id}"></p>
                        </td>
                        <td style="width: 100px; text-align: center; vertical-align: center">
                            <input type="button" onclick="addToCart(${phone.id}, $('#quantity${phone.id}').val())" value="Add to">
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        </p>

        <div>
            <ul class="pagination" style="float: right">
                <li class="page-item">
                    <a class="page-link" href="<c:url value="/productList"/>?page=${page}&action=prev&sort=${sort}<c:if test="${not empty direction}">&direction=${direction}</c:if>&search=${searchText}">Previous</a>
                </li>
                <c:forEach begin="${pageStartNumber}" end="${pageStartNumber + pagesToDisplay - 1}" varStatus="index">
                    <li class="page-item">
                        <a <c:if test="${index.index == page}">class="alert"</c:if> class="page-link" href="<c:url value="/productList"/>?page=${index.index}&sort=${sort}<c:if test="${not empty direction}">&direction=${direction}</c:if>&search=${searchText}">${index.index}</a>
                    </li>
                </c:forEach>
                <li class="page-item">
                    <a class="page-link" href= "<c:url value="/productList"/>?page=${page}&action=next&sort=${sort}<c:if test="${not empty direction}">&direction=${direction}</c:if>&search=${searchText}">Next</a>
                </li>
            </ul>
        </div>
    </body>
</html>
