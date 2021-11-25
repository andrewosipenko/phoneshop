<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script> <%@ include file="js/doAjaxPost.js" %> </script>
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
            <a href="${pageContext.request.contextPath}/productList">
                <h1>Phones</h1>
            </a>
        </div>
        <div class="col col-md-7"></div>
        <div class="col col-md-3">
            <button class="btn btn-outline-secondary">
                My cart:
                <text id="totalQuantity">${cart.totalQuantity}</text>
                item
                <text id="totalCost">${cart.totalCost}$</text>
            </button>
        </div>
        <hr align="center" width="300" color="Red"/>
    </div>
    <%--Search--%>
    <div class="row">
        <div class="col col-md-9"></div>
        <div class="col col-md-3">
            <form>
                <div class="input-group mb-3">
                    <button class="btn btn-outline-secondary" id="search-button">Search</button>
                    <input name="searchText" type="text" class="form-control" value="${searchText}"
                           aria-describedby="search-button">
                </div>
            </form>
        </div>
    </div>
    <br>
    <%--Info table--%>
    <div class="row">
        <div class="col border col-md-2">
            <h5>Image</h5>
        </div>
        <div class="col border col-md-2 bg-light">
            <tags:columnTitleTag sortField="BRAND">Brand</tags:columnTitleTag>
        </div>
        <div class="col border col-md-2">
            <tags:columnTitleTag sortField="MODEL">Model</tags:columnTitleTag>
        </div>
        <div class="col border col-md-1 bg-light">
            <h5>Color</h5>
        </div>
        <div class="col border col-md-2">
            <tags:columnTitleTag sortField="DISPLAY_SIZE">Display size</tags:columnTitleTag>
        </div>
        <div class="col border col-md-1 bg-light">
            <tags:columnTitleTag sortField="PRICE">Price</tags:columnTitleTag>
        </div>
        <div class="col border col-md-1">
            <h5>Quantity</h5>
        </div>
        <div class="col border col-md-1 bg-light">
            <h5>Action</h5>
        </div>
    </div>
    <c:forEach var="phone" items="${phones}">
        <form:form modelAttribute="cartAddForm" id="cartAddForm${phone.id}"
                   action="${pageContext.servletContext.contextPath}/ajaxCart">
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
                <div id="color" class="col border bg-light col-md-1">${phone.colors}</div>
                <div id="displaySize" class="col border col-md-2">${phone.displaySizeInches}</div>
                <div id="price" class="col border bg-light col-md-1">${phone.price}$</div>
                <div id="quantity" class="col border col-md-1">
                    <br>
                        <%--quantity field--%>
                    <form:input path="quantity" id="quantity${phone.id}" value="1" cssClass="form-control col-md-1"/>
                    <form:input path="phoneId" type="hidden" id="phoneId${phone.id}" value="${phone.id}"/>
                    <div id="error-message-${phone.id}" style="color: red; font-size: small"></div>
                </div>
                <div id="action" class="col border bg-light col-md-1">
                    <br>
                        <%--Add button--%>
                    <input type="submit" value="Add" class="btn btn-outline-secondary"
                           id="${phone.id}" onclick="doAjaxPost(${phone.id})">
                </div>
            </div>
            <form:errors path="quantity"/>
        </form:form>
    </c:forEach>
</div>
<br>
<%--Info switch--%>
<c:choose>
    <c:when test="${empty phones}">
        <h1>Phones are not found :(</h1>
    </c:when>
    <c:otherwise>
        <div class="row">
            <div class="col col-md-8"></div>
            <div class="col col-md-4">
                <div class="btn-group" aria-label="Navigate button">
                    <input type="hidden" name="pageNumber" value="${not empty pageNumber ? pageNumber : 1}">
                    <a href="?pageNumber=${pageNumber - 1}">
                        <button type="button" class="btn btn-outline-secondary">&lt</button>
                    </a>
                    <c:forEach var="paginationNumber" items="${paginationList}">
                        <c:if test="${paginationNumber > 0}">
                            <a href="?pageNumber=${paginationNumber}&searchText=${searchText}&sortField=${sortField}&sortOrder=${sortOrder}">
                                <button type="button" class="btn btn-outline-secondary"
                                        style="${paginationNumber eq pageNumber ? 'background-color:gray;color:white': ''}">${paginationNumber}</button>
                            </a>
                        </c:if>
                    </c:forEach>
                    <a href="?pageNumber=${pageNumber + 1}">
                        <button type="button" class="btn btn-outline-secondary">&gt</button>
                    </a>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
</body>
</html>