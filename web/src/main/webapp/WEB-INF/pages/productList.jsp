<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Phonify</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet">
        <link href="<c:url value="/resources/css/jquery.mxpage.css"/>" rel="stylesheet">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="<c:url value="/resources/js/jquery.mxpage.js"/>"></script>
        <script src="<c:url value="/resources/js/pagination.js"/>"></script>
        <script src="<c:url value="/resources/js/product_list.js"/>"></script>
        <script src="<c:url value="/resources/js/jquery.number.js"/>"></script>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-12 login">
                    <c:if test="${pageContext.request.userPrincipal.name == null}"><a href="${pageContext.request.contextPath}/login">Login</a></c:if>
                    <c:if test="${pageContext.request.userPrincipal.name != null}">
                        ${pageContext.request.userPrincipal.name} | <a href="${pageContext.request.contextPath}/admin/orders">ADMIN</a> | <a href="${pageContext.request.contextPath}/logout">Logout</a>
                    </c:if>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <a href="${pageContext.request.contextPath}"><img src="<c:url value="/resources/img/logo.jpg"/>"></a>
                </div>
                <div class="col">
                    <form method="GET" action="${pageContext.request.contextPath}/cart">
                        <button class="float-right cart btn" type="submit">My cart: <span id="count-items">${cartStatus.countItems}</span> items <span id="price" class="price">${cartStatus.price}</span>$</button>
                    </form>
                </div>
            </div>
            <div class="row">
                <div class="col">
                     <form class="navbar-form navbar-right" method="GET" action="${pageContext.request.contextPath}/productList">
                         <div class="input-group">
                             <input type="text" class="form-control" placeholder="Search" name="model">
                             <div class="input-group-btn">
                                 <button class="btn btn-default" type="submit">
                                     <i class="glyphicon glyphicon-search"></i>
                                 </button>
                             </div>
                         </div>
                     </form>
                </div>
            </div>
            <div class="row">
                <table>
                    <thead>
                        <tr class="table-header">
                            <td class="table-header">Image</td>
                            <td><a class="sort-link" href="javascript:redirectToOldUrlWithNewParam('order', changeOrder('brand'))">Brand <i class="glyphicon glyphicon-sort"></i></a></td>
                            <td><a class="sort-link" href="javascript:redirectToOldUrlWithNewParam('order', changeOrder('model'))">Model <i class="glyphicon glyphicon-sort"></i></a></td>
                            <td>Colors</td>
                            <td><a class="sort-link" href="javascript:redirectToOldUrlWithNewParam('order', changeOrder('displaySizeInches'))">Display size <i class="glyphicon glyphicon-sort"></i></a></td>
                            <td><a class="sort-link" href="javascript:redirectToOldUrlWithNewParam('order', changeOrder('price'))">Price <i class="glyphicon glyphicon-sort"></i></a></td>
                            <td>Quantity</td>
                            <td>Action</td>
                        </tr>
                    </thead>
                    <c:forEach var="phone" items="${phones}">
                        <tr>
                            <td>
                                <a href="${pageContext.request.contextPath}/productDetails/${phone.id}">
                                    <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                                </a>
                            </td>
                            <td>${phone.brand}</td>
                            <td>${phone.model}</td>
                            <td>
                                <c:forEach var="color" items="${phone.colors}">
                                    ${color.code}
                                </c:forEach>
                            </td>
                            <td>${phone.displaySizeInches}''</td>
                            <td><span class="price">${phone.price} </span>$</td>
                            <td>
                                <input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
                                <input id="quantity-${phone.id}" type="text" value="1">
                                <br>
                                <span id="quantity-${phone.id}-wrong-format" class="error"></span>
                            </td>
                            <td>
                                <button type="button" class="btn btn-default add-cart" onclick="addToCart(${phone.id})">Add to cart</button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <div class="row">
                <div class="col">
                    <div class="pages">
                        <input type="hidden" id="countPages" value="${pageCount}">
                        <input type="hidden" id="currentPage" value="${param.page}">
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>