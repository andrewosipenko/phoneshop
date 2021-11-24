<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <title>ProductList</title>
</head>
<body>

<div class="container">
    <%--Login ref--%>
    <div class="row">
        <div class="col col-md-10"></div>
        <div class="col col-md-2">
            <a href="">Login</a>
        </div>
    </div>
    <%--Cart--%>
    <br>
    <div class="row">
        <div class="col col-md-2">
            <h1>Phones</h1>
        </div>
        <div class="col col-md-7"></div>
        <div class="col col-md-3">
            <button class="btn btn-outline-secondary">My cart: 1 item 100$</button>
        </div>
        <hr align="center" width="300" color="Red"/>
    </div>
    <%--Search--%>
    <div class="row">
        <div class="col col-md-9"></div>
        <div class="col col-md-3">
            <div class="input-group mb-3">
                <button class="btn btn-outline-secondary" type="button" id="button-addon1">Search</button>
                <input type="text" class="form-control" placeholder=""
                       aria-describedby="button-addon1">
            </div>
        </div>
    </div>
    <br>
    <%--Info table--%>
    <div class="row">
        <div class="col border col-md-2">
            <h5>Image</h5>
        </div>
        <div class="col border col-md-2 bg-light">
            <h5>Brand</h5>
        </div>
        <div class="col border col-md-2">
            <h5>Model</h5>
        </div>
        <div class="col border col-md-2 bg-light">
            <h5>Color</h5>
        </div>
        <div class="col border col-md-1">
            <h5>Display size</h5>
        </div>
        <div class="col border col-md-1 bg-light">
            <h5>Price</h5>
        </div>
        <div class="col border col-md-1">
            <h5>Quantity</h5>
        </div>
        <div class="col border col-md-1 bg-light">
            <h5>Action</h5>
        </div>
    </div>
    <c:forEach var="phone" items="${phones}">
        <div class="row">
            <div id="image" class="col border img-fluid col-md-2">
                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}"
                     class="img-fluid"
                     alt="image">
            </div>
            <div id="brand" class="col border bg-light col-md-2">
                <text>${phone.brand}</text>
            </div>
            <div id="model" class="col border col-md-2">${phone.model}</div>
            <div id="color" class="col border bg-light col-md-2">${phone.colors}</div>
            <div id="displaySize" class="col border col-md-1">${phone.displaySizeInches}</div>
            <div id="price" class="col border bg-light col-md-1">${phone.price}</div>
            <div id="quantity" class="col border col-md-1">
                <br>
                    <%--quantity field--%>
                <input class="form-control col-md-1" type="text">
            </div>
            <div id="action" class="col border bg-light col-md-1">
                <br>
                    <%--Add button--%>
                <button type="button" class="btn btn-outline-secondary">Add</button>
            </div>
        </div>
    </c:forEach>
</div>
<br>
<%--Info switch--%>
<div class="row">
    <div class="col col-md-8"></div>
    <div class="col col-md-4">
        <div class="btn-group" aria-label="Navigate button">
            <button type="button" class="btn btn-outline-secondary">&lt</button>
            <button type="button" class="btn btn-outline-secondary">1</button>
            <button type="button" class="btn btn-outline-secondary">2</button>
            <button type="button" class="btn btn-outline-secondary">3</button>
            <button type="button" class="btn btn-outline-secondary">&gt</button>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
</body>
</html>

<%--<p>--%>
<%--  Hello from product list!--%>
<%--</p>--%>
<%--<p>--%>
<%--  Found <c:out value="${phones.size()}"/> phones.--%>
<%--<table border="1px">--%>
<%--  <thead>--%>
<%--  <tr>--%>
<%--    <td>Image</td>--%>
<%--    <td>Brand</td>--%>
<%--    <td>Model</td>--%>
<%--    <td>Price</td>--%>
<%--  </tr>--%>
<%--  </thead>--%>
<%--  <c:forEach var="phone" items="${phones}">--%>
<%--    <tr>--%>
<%--      <td>--%>
<%--        <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">--%>
<%--      </td>--%>
<%--      <td>${phone.brand}</td>--%>
<%--      <td>${phone.model}</td>--%>
<%--      <td>$ ${phone.price}</td>--%>
<%--    </tr>--%>
<%--  </c:forEach>--%>
<%--</table>--%>
<%--</p>--%>