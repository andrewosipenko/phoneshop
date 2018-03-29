<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page >
  <c:if test="${productPage.totalCount ne 0}">
    <div class="lead">
      <table class="table table-hover">
        <thead>
        <tr>
          <th>Image</th>
          <th> <template:tableOrderLinkHead nameOfLink="Brand" currentOrder="BRAND"/></th>
          <th><template:tableOrderLinkHead nameOfLink="Model" currentOrder="MODEL"/></th>
          <th>Color</th>
          <th><template:tableOrderLinkHead nameOfLink="Display size" currentOrder="DISPLAY_SIZE"/></th>
          <th><template:tableOrderLinkHead nameOfLink="Price" currentOrder="PRICE"/></th>
          <th>Quantity</th>
          <td>Action</td>
        </tr>
        </thead>
        <c:forEach var="phone" items="${productPage.curentPagePhoneList}">
          <tr>
            <td>
              <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
            </td>
            <td><c:out value="${phone.brand}" /></td>
            <td><c:out value="${phone.model}" /></td>
            <td>
              <c:forEach var="color" items="${phone.colors}">
                <p><c:out value="${color.code}"/></p>
              </c:forEach>
            </td>
            <td><c:out value="${phone.displaySizeInches}" /></td>
            <td><c:out value="${phone.price}" /></td>
            <td>TODO</td>
            <td>
              <button type="button" class="btn btn-secondary add-to-cart">Add to cart</button>
            </td>
          </tr>
        </c:forEach>
      </table>
    </div>
    <template:pagination targetPage="productList" productPage="${productPage}" order="${order}" query="${query}">
    </template:pagination >
  </c:if>
</template:page>

