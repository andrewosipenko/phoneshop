<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page>
    <div class="container">
        <form formaction="<c:url value="/productList"/>page=${page}&sort=brand<c:if test="${direction == 'asc'}">&dir=desc</c:if>&search=">
            <input class="form-control input-sm" value="${ not empty searchText ? searchText: 'Search...' }">
        </form>
        <table class="table">
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
                        <img height="30%" width="100%"
                             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                    </td>
                    <td>${phone.brand}</td>
                    <td width="100%">${phone.model}</td>
                    <td>
                        <c:forEach var="color" items="${phone.colors}" varStatus="index">
                            ${color.code}
                            <c:if test="${index.count != phone.colors.size()}">
                                ,
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>${phone.displaySizeInches}</td>
                    <td>$ ${phone.price}</td>
                    <td>
                        <input class="text-input" name="quantity" id="${phone.id}" value="0">
                        <a id="errorMessage${phone.id}"></a>
                    </td>
                    <td>
                        <input type="button" onclick="addToCart(${phone.id}, $('#${phone.id}').val())" value="Add to">
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
    <script>
        function addToCart (phoneId, quantity) {
                $.ajax({
                    url: "<c:url value="ajaxCart"/>",
                    type: 'POST',
                    data: 'phoneId=' + phoneId + '&quantity=' + quantity,
                    success:  functionSuccess,
                    error: function (){
                            functionError(phoneId);
                    }
                });
            }

       function functionSuccess(receivedData) {
           $("#itemsAmount").text(receivedData.itemsAmount);
           $("#subtotal").text(receivedData.subtotal);
       }

       function functionError(phoneId) {
           var selector = '#errorMessage' + phoneId;
           $(selector).text("wrong format");
       }
    </script>
</template:page>