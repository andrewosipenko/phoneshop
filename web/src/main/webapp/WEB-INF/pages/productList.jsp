<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/templates" %>
<template:page>
    <script src="${pageContext.request.contextPath}/resources/js/phoneList.js"></script>
    <p>
        Found <c:out value="${phonesCount}"/> phones.
    </p>
    <c:if test="${phonesCount > 0}">
        <table class="table table-hover">
          <thead>
            <tr>
              <th>Image</th>
              <th><a href="${pageContext.request.contextPath}/productList?order=brand${order eq 'BRAND' ? '_desc' : ''}<c:if test="${not empty query}">&query=<c:out value="${query}"/></c:if>">Brand <i class="fa fa-fw fa-sort"></i></a></th>
              <th><a href="${pageContext.request.contextPath}/productList?order=model${order eq 'MODEL' ? '_desc' : ''}<c:if test="${not empty query}">&query=<c:out value="${query}"/></c:if>">Model <i class="fa fa-fw fa-sort"></i></a></th>
              <th>Color</th>
              <th><a href="${pageContext.request.contextPath}/productList?order=display_size${order eq 'DISPLAY_SIZE' ? '_desc' : ''}<c:if test="${not empty query}">&query=<c:out value="${query}"/></c:if>">Display size <i class="fa fa-fw fa-sort"></i></a></th>
              <th><a href="${pageContext.request.contextPath}/productList?order=price${order eq 'PRICE' ? '_desc' : ''}<c:if test="${not empty query}">&query=<c:out value="${query}"/></c:if>">Price <i class="fa fa-fw fa-sort"></i></a></th>
              <th>Quantity</th>
              <th>Action</th>
            </tr>
          </thead>
          <c:forEach var="phone" items="${phones}">
            <tr>
              <td>
                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
              </td>
              <td>${phone.brand}</td>
              <td>${phone.model}</td>
              <td>
                <c:forEach var="color" items="${phone.colors}">
                  <p>${color.code}</p>
                </c:forEach>
              </td>
              <td>${phone.displaySizeInches}</td>
              <td>$ ${phone.price}</td>
              <td>
                  <input type="text" id="quantity-${phone.id}" value="1" size="10"/>
                  <div class="error-message" id="error-message-${phone.id}"></div>
              </td>
              <td>
                  <button type="button" class="btn btn-secondary" onclick="addToCart(${phone.id})">Add to cart</button>
              </td>
            </tr>
          </c:forEach>
        </table>
        <ul class="pagination justify-content-end">
          <li class="page-item ${page eq 1 ? 'disabled' : ''}">
            <a class="page-link" href="${pageContext.request.contextPath}/productList?page=${page-1}&order=${order}<c:if test="${not empty query}">&query=<c:out value="${query}"/></c:if>" tabindex="-1">Previous</a>
          </li>
          <c:forEach var = "i" begin = "${startPaginationNumber}" end = "${finishPaginationNumber}">
              <li class="page-item ${i eq page ? 'disabled' : ''}"><a class="page-link" href="${pageContext.request.contextPath}/productList?page=${i}&order=${order}<c:if test="${not empty query}">&query=<c:out value="${query}"/></c:if>">${i}</a></li>
          </c:forEach>
          <li class="page-item ${page eq pageCount ? 'disabled' : ''}">
            <a class="page-link" href="${pageContext.request.contextPath}/productList?page=${page+1}&order=${order}<c:if test="${not empty query}">&query=<c:out value="${query}"/></c:if>">Next</a>
          </li>
        </ul>
    </c:if>
</template:page>