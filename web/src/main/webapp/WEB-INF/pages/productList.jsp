<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page>
  <jsp:body>
    <nav class="navbar navbar-dark bg-dark pr-5 pl-5 pd-5">
      <div>
        <a class="navbar-brand" href="#"><h2>Phoneshop</h2></a>
      </div>
      <div>
        <div class="float-right" >
          <a href="#">Login</a>
        </div>
        <div>
          <button class="btn btn-warning">My cart: 0 items 0$</button>
        </div>
      </div>
    </nav>

    <div class="mt-5 mr-5 ml-5">
      <nav class="navbar navbar-light bg-light justify-content-between">
        <h4>Phone</h4>
        <form class="form-inline mr-4 mt-3">
          <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
          <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form>
      </nav>
        <%--Found <c:out value="${phones.size()}"/> phones.--%>

      <table class="table">
        <thead>
        <tr>
          <td scope="col">Image</td>
          <td scope="col">Brand</td>
          <td scope="col">Model</td>
          <td scope="col">Color</td>
          <td scope="col">Display size</td>
          <td scope="col">Price</td>
          <td scope="col">Quantity</td>
          <td scope="col">Action</td>
        </tr>
        </thead>
        <c:forEach var="phone" items="${phones}">
          <tr>
            <td class="align-middle">
              <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}"
                   height="80">
            </td>
            <td class="align-middle">${phone.brand}</td>
            <td class="align-middle">${phone.model}</td>
            <td class="align-middle">
              <c:forEach var="color" items="${phone.colors}" varStatus="status">
                <div style="width: 15px; height: 15px; display:inline-block; border-radius: 100%; background-color: ${color.getCode()}; padding: 0 2px"></div>
              </c:forEach>
            </td>
            <td class="align-middle">${phone.displaySizeInches}"</td>
            <td class="align-middle">
              <c:choose>
              <c:when test="${not empty phone.price}">$ ${phone.price}</c:when>
              <c:otherwise>unknown</c:otherwise>
              </c:choose>
            <td class="align-middle">
              <input type="text" value="1"/>
            </td>
            <td class="align-middle">
              <button class="btn btn-primary">Add to</button>
            </td>
          </tr>
        </c:forEach>
      </table>
      <nav class="float-right">
        <ul class="pagination">
          <li class="page-item">
            <a class="page-link" href="#" aria-label="Previous">
              <span aria-hidden="true">&laquo;</span>
              <span class="sr-only">Previous</span>
            </a>
          </li>
          <li class="page-item"><a class="page-link" href="#">1</a></li>
          <li class="page-item"><a class="page-link" href="#">2</a></li>
          <li class="page-item"><a class="page-link" href="#">3</a></li>
          <li class="page-item">
            <a class="page-link" href="#" aria-label="Next">
              <span aria-hidden="true">&raquo;</span>
              <span class="sr-only">Next</span>
            </a>
          </li>
        </ul>
      </nav>
    </div>
  </jsp:body>
</template:page>