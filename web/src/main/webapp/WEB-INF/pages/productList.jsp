<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="specific" tagdir="/WEB-INF/tags/template/productList" %>
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
      <nav class="float-right" id="pagination">
      </nav>
    </div>

    <script>
      $(function(){
          var limit = ${not empty param['limit']? param['limit'] : 10};
          var page = ${not empty param['page']? param['page'] : 1};
          var phonesAmount = ${phonesAmount};
          var pagesAmount =  Math.ceil(phonesAmount/limit);
          var startPage = (pagesAmount>5 && page>3) ? page-2 : 1;
          var endPage = (pagesAmount>5 && page<pagesAmount-2) ? startPage+4 : pagesAmount;

          var paginationNav = $('#pagination');

          paginationNav.append(
              '<ul class="pagination"></ul>'
          );
          var ul = paginationNav.find(".pagination");

          if (startPage > 1) {
              //append "<<" button
              ul.append(
                  '<li class="page-item"><a class="page-link" data-page="'+1+'" aria-label="Beginning" >&laquo;</a></li>'
              );
          }

          for (var i = startPage; i<=endPage; i++){
              ul.append(
                  '<li class="page-item' +( (page === i)? ' active' : '' )+ '"><a class="page-link" data-page="'+i+'" >'+i+'</a></li>'
              );
          }

          if (endPage < pagesAmount) {
              //append ">>" button
              ul.append(
                  '<li class="page-item"><a class="page-link" data-page="'+pagesAmount+'" aria-label="End" >&raquo;</a></li>'
              );
          }

          $('#pagination').find('.page-link').on('click', function(){
              var newPage = $(this).data('page');
              console.log(page);

              var link = "${requestScope['javax.servlet.forward.request_uri']}?"
                  +"page=" + newPage
                  +"${not empty param['limit'] ? '&limit='.concat(param['limit']) : ''}"
                  +"${not empty param['sortBy'] ? '&sortBy='.concat(param['sortBy']) : ''}"
                  +"${not empty param['search'] ? '&search='.concat(param['search']) : ''}";
              window.location.replace(link);
          });
      });
    </script>
  </jsp:body>
</template:page>