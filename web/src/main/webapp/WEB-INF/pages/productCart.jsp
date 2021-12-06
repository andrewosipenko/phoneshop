<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>
    <title>
        <spring:message code="titlePage.productCart"/>
    </title>
</head>
<body>
<div class="container">
    <tags:header cart="${cart}"/>
    <h1>
        <spring:message code="titlePage.productCart"/>
    </h1>
    <tags:backToProductListButton/>
    <c:choose>
        <c:when test="${empty cart.cartItems}">
            <h1>
                <spring:message code="error.noPhoneInCart"/>
            </h1>
        </c:when>
        <c:otherwise>
            <%--Info table--%>
            <div class="row">
                <div class="col border col-md-2">
                    <h5>
                        <spring:message code="titleTable.image"/>
                    </h5>
                </div>
                <div class="col border col-md-2 bg-light">
                    <h5>
                        <spring:message code="titleTable.brand"/>
                    </h5>
                </div>
                <div class="col border col-md-2">
                    <h5>
                        <spring:message code="titleTable.model"/>
                    </h5>
                </div>
                <div class="col border col-md-1 bg-light">
                    <h5>
                        <spring:message code="titleTable.color"/>
                    </h5>
                </div>
                <div class="col border col-md-2">
                    <h5>
                        <spring:message code="titleTable.displaySize"/>
                    </h5>
                </div>
                <div class="col border col-md-1 bg-light">
                    <h5>
                        <spring:message code="titleTable.price"/>
                    </h5>
                </div>
                <div class="col border col-md-1">
                    <h5>
                        <spring:message code="titleTable.quantity"/>
                    </h5>
                </div>
                <div class="col border col-md-1 bg-light">
                    <h5>
                        <spring:message code="titleTable.action"/>
                    </h5>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
    <form:form action="${pageContext.servletContext.contextPath}/cart/update" method="post"
               modelAttribute="updateCartForm" id="cartUpdateForm">
        <c:forEach var="cartItem" items="${cart.cartItems}">
            <div class="row">
                <div id="image" class="col border img-fluid col-md-2">
                    <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${cartItem.phone.imageUrl}"
                         class="img-fluid"
                         alt="image">
                </div>
                <div id="brand" class="col border bg-light col-md-2">
                    <text>${cartItem.phone.brand}</text>
                </div>

                <div id="model" class="col border col-md-2">
                    <a href="${pageContext.servletContext.contextPath}/productDetails/${cartItem.phone.id}">
                            ${cartItem.phone.model}
                    </a>
                </div>
                <div id="color" class="col border bg-light col-md-1">${cartItem.phone.colors}</div>
                <div id="displaySize" class="col border col-md-2">${cartItem.phone.displaySizeInches}"</div>
                <div id="price" class="col border bg-light col-md-1">${cartItem.phone.price}$</div>
                <div id="quantity_block" class="col border col-md-1">
                    <br>
                        <%--quantity field--%>

                    <form:input path="quantityFormMap[${cartItem.phone.id}].quantity"
                                id="quantity${cartItem.phone.id}"
                                value="${updateCartForm.quantityFormMap[cartItem.phone.id].quantity}"
                                form="cartUpdateForm"
                                class="form-control col-md-1"/>
                    <form:errors path="quantityFormMap[${cartItem.phone.id}].quantity"
                                 style="color: red; font-size: small"/>
                </div>
                <div id="action" class="col border bg-light col-md-1">
                    <br>
                    <a class="btn btn-outline-secondary"
                       href="${pageContext.servletContext.contextPath}/cart/deleteCartItem?phoneId=${cartItem.phone.id}">Delete</a>
                </div>
            </div>
        </c:forEach>
    </form:form>
    <br>
    <div class="row">
        <div class="col col-md-10"></div>
        <div class="col col-md-1">
            <input type="submit" value="Update" class="btn btn-outline-secondary" form="cartUpdateForm"/>
        </div>
        <div class="col col-md-1">
            <input type="submit" value="Order" class="btn btn-outline-secondary"/>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
</body>
</html>