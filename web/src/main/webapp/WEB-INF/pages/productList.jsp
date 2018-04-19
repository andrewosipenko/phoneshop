<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page>
    <script> <%@ include file="js/addToCart.js" %> </script>
    <div class="container">
        <form action="<c:url value="/productList"/>" method="get">
        <div class="row">
            <div class="col-lg-6">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="Search for..." value="${searchText}" name="search">
                    <span class="input-group-btn">
                            <button class="btn btn-secondary" type="submit">Search</button>
                        </span>
                </div>
            </div>
        </div>
        </form>
        <table class="table table-striped">
            <thead>
            <tr>
                <td>Image</td>
                <td>
                    <a href="<c:url value="/productList"/>?page=${page}&sort=brand<c:if test="${direction == 'asc'}">&dir=desc</c:if>&search=${searchText}">Brand</a>
                </td>
                <td>
                    <a href="<c:url value="/productList"/>?page=${page}&sort=model<c:if test="${direction == 'asc'}">&dir=desc</c:if>&search=${searchText}">Model</a>
                </td>
                <td>Color</td>
                <td>
                    <a href="<c:url value="/productList"/>?page=${page}&sort=displaySizeInches<c:if test="${direction == 'asc'}">&dir=desc</c:if>&search=${searchText}">Display size</a>
                </td>
                <td>
                    <a href="<c:url value="/productList"/>?page=${page}&sort=price<c:if test="${direction == 'asc'}">&dir=desc</c:if>&search=${searchText}">Price</a>
                </td>
                <td>Quantity</td>
                <td>Action</td>
            </tr>
            </thead>
            <c:forEach var="phone" items="${phones}">
                <tr>
                    <td>
                        <img width="90"
                             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                    </td>
                    <td>${phone.brand}</td>
                    <td><a class="hyperlink" href="<c:url value="/productDetails/phoneId=${phone.id}"/>">${phone.model}</a></td>
                    <td>
                        <template:getColors phoneColors="${phone.colors}"/>
                    </td>
                    <td>${phone.displaySizeInches}</td>
                    <td>$ ${phone.price}</td>
                    <td>
                        <input class="text-input" name="quantity" id="quantity${phone.id}" value="0"><br>
                        <a class="text-danger" id="errorMessage${phone.id}"></a>
                    </td>
                    <td>
                        <input type="button" onclick="addToCart(${phone.id}, $('#quantity${phone.id}').val())" value="Add to">
                    </td>
                </tr>
            </c:forEach>
        </table>
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <li class="page-item">
                    <a class="page-link" href="<c:url value="/productList"/>?page=${page}&action=prev&sort=${sort}<c:if test="${not empty direction}">&dir=${direction}</c:if>&search=${searchText}">Previous</a>
                </li>
                <c:forEach begin="${pageBeginNumber}" end="${pageBeginNumber + pagesToDisplay - 1}" varStatus="index">
                    <li class="page-item">
                        <a <c:if test="${index.index == page}">class="alert"</c:if> class="page-link" href="<c:url value="/productList"/>?page=${index.index}&sort=${sort}<c:if test="${not empty direction}">&dir=${direction}</c:if>&search=${searchText}">${index.index}</a>
                    </li>
                </c:forEach>
                <li class="page-item">
                    <a class="page-link" href= "<c:url value="/productList"/>?page=${page}&action=next&sort=${sort}<c:if test="${not empty direction}">&dir=${direction}</c:if>&search=${searchText}">Next</a>
                </li>
            </ul>
        </nav>
    </div>
</template:page>