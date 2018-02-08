<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/templates" %>
<template:page>
  <p>
    Found <c:out value="${phones.size()}"/> phones.
    <table class="table table-hover">
      <thead>
        <tr>
          <th>Image</th>
          <th>Brand <i class="fa fa-fw fa-sort"></i></th>
          <th>Model <i class="fa fa-fw fa-sort"></i></th>
          <th>Color</th>
          <th>Display size <i class="fa fa-fw fa-sort"></i></th>
          <th>Price <i class="fa fa-fw fa-sort"></i></th>
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
          <td>Quantity</td>
          <td>Action</td>
        </tr>
      </c:forEach>
    </table>
  </p>
</template:page>