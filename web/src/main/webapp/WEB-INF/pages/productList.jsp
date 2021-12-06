<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>
    <script>
        <%@ include file="js/addToCartList.js" %>
    </script>
    <title>ProductList</title>
</head>
<body>
<div class="container">
    <tags:header cart="${cart}"/>
    <%--Search--%>
    <div class="row">
        <div class="col col-md-9">
            <h1>Product list</h1>
        </div>
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
    <c:choose>
        <c:when test="${empty phones}">
            <h1>There is no phone in your cart!</h1>
        </c:when>
        <c:otherwise>
            <fmt:bundle basename="com.es.phoneshop.web.controller.pages.property.PropertyResourceBundle_EN">
                <%--Info table--%>
                <div class="row">
                    <div class="col border col-md-2">
                        <h5>
                            <fmt:message key="image"/>
                        </h5>
                    </div>
                    <div class="col border col-md-2 bg-light">
                        <tags:columnTitleTag sortField="BRAND">
                            <fmt:message key="brand"/>
                        </tags:columnTitleTag>
                    </div>
                    <div class="col border col-md-2">
                        <tags:columnTitleTag sortField="MODEL">
                            <fmt:message key="model"/>
                        </tags:columnTitleTag>
                    </div>
                    <div class="col border col-md-1 bg-light">
                        <h5>
                            <fmt:message key="color"/>
                        </h5>
                    </div>
                    <div class="col border col-md-2">
                        <tags:columnTitleTag sortField="DISPLAY_SIZE">
                            <fmt:message key="displaySize"/>
                        </tags:columnTitleTag>
                    </div>
                    <div class="col border col-md-1 bg-light">
                        <tags:columnTitleTag sortField="PRICE">
                            <fmt:message key="price"/>
                        </tags:columnTitleTag>
                    </div>
                    <div class="col border col-md-1">
                        <h5>
                            <fmt:message key="quantity"/>
                        </h5>
                    </div>
                    <div class="col border col-md-1 bg-light">
                        <h5>
                            <fmt:message key="action"/>
                        </h5>
                    </div>
                </div>
            </fmt:bundle>
        </c:otherwise>
    </c:choose>
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

            <div id="model" class="col border col-md-2">
                <a href="${pageContext.servletContext.contextPath}/productDetails/${phone.id}">
                        ${phone.model}
                </a>
            </div>
            <div id="color" class="col border bg-light col-md-1">${phone.colors}</div>
            <div id="displaySize" class="col border col-md-2">${phone.displaySizeInches}"</div>
            <div id="price" class="col border bg-light col-md-1">${phone.price}$</div>
            <div id="quantity_block" class="col border col-md-1">
                <br>
                    <%--quantity field--%>
                <input name="quantity" id="phoneQuantity${phone.id}" value="1" class="form-control col-md-1"/>
                <input name="phoneId" type="hidden" id="phoneId${phone.id}" value="${phone.id}"/>

                <span style="color: green" id="success-message${phone.id}" class="success-message"></span>
                <span style="color: red" id="error-message${phone.id}" class="error-message"></span>
            </div>
            <div id="action" class="col border bg-light col-md-1">
                <br>
                    <%--Add button--%>
                <input type="submit" value="Add" class="btn btn-outline-secondary"
                       id="add_button${phone.id}" onclick="addToCart(${phone.id})">
            </div>
        </div>
    </c:forEach>
</div>
<br>
<%--Info switch--%>

<c:if test="${not empty phones}">
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
</c:if>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
</body>
</html>