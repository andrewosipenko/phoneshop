<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="specific" tagdir="/WEB-INF/tags/template/productList" %>
<template:page>
  <jsp:attribute name="scripts">
    <script src="<c:url value="/resources/js/jquery.twbsPagination.min.js"/>"></script>
    <script src="<c:url value="/resources/js/productList.js"/>"></script>
    <script src="<c:url value="/resources/js/addToCartForm.js"/>"></script>
  </jsp:attribute>
  <jsp:body>
    <template:header cartQuantity="${cartQuantity}" cartSubTotal="${cartSubTotal}"/>

    <div class="mt-5 mr-5 ml-5">
      <nav class="navbar navbar-light bg-light justify-content-between">
        <h4>Phone</h4>
        <form class="form-inline mr-4 mt-3">
          <input class="form-control mr-sm-2" type="search" placeholder="Search" name="search" value="${param['search']}">
          <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form>
      </nav>
      <p class="float-right">Found <c:out value="${phonesAmount}"/> phones</p>

      <table class="table">
        <thead>
        <tr>
          <td scope="col">Image</td>
          <td scope="col">Brand <specific:sorter sortBy="brand"/></td>
          <td scope="col">Model <specific:sorter sortBy="model"/></td>
          <td scope="col">Color</td>
          <td scope="col">Display size <specific:sorter sortBy="display"/></td>
          <td scope="col">Price <specific:sorter sortBy="price"/></td>
          <td scope="col">Quantity</td>
          <td scope="col">Action</td>
        </tr>
        </thead>
        <c:forEach var="phone" items="${phones}">
          <tr>
            <td class="align-middle">
              <a href="${pageContext.servletContext.contextPath}/productDetails/${phone.id}">
                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}"
                     height="80">
              </a>
            </td>
            <td class="align-middle">${phone.brand}</td>
            <td class="align-middle">
              <a href="${pageContext.servletContext.contextPath}/productDetails/${phone.id}">${phone.model}</a>
            </td>
            <td class="align-middle">
              <template:colors colors="${phone.colors}"/>
            </td>
            <td class="align-middle">${phone.displaySizeInches}"</td>
            <td class="align-middle">
              <c:choose>
              <c:when test="${not empty phone.price}">$${phone.price}</c:when>
              <c:otherwise>unknown</c:otherwise>
              </c:choose>
            </td>
            <td class="align-middle">
              <template:add-to-cart-form phoneId="${phone.id}" value="1"/>
            </td>
            <td class="align-middle">
              <button class="add-to-cart-btn btn btn-primary" data-id="${phone.id}">Add to</button>
            </td>
          </tr>
        </c:forEach>
      </table>
      <nav class="float-right" id="pagination"
           data-amount="${phonesAmount}"
           data-page="${not empty param['page'] ? param['page'] : 1}"
           data-limit="${param['limit']}"
           data-sortby="${param['sortBy']}"
           data-search="${param['search']}">
      </nav>
    </div>
  </jsp:body>
</template:page>