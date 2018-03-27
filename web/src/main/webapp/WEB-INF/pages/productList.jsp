<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page >
  <c:choose>
  <c:when test="${productPage.totalCount ne 0}">
    <div class="container">
      <table class="table table-hover">
        <thead>
        <tr>
          <th>Image</th>
          <th> <template:tableOrderLinkHead nameOfLink="Brand" currentOrder="BRAND"/></th>
          <th><template:tableOrderLinkHead nameOfLink="Model" currentOrder="MODEL"/></th>
          <th>Color</th>
          <th><template:tableOrderLinkHead nameOfLink="Display" currentOrder="DISPLAY_SIZE"/></th>
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
            <td>
              <input type="text" name="amount" value="1" id="${phone.id}">
              <div id="error-message" style="color: red"></div>
            </td>
            <td>
                <button type="button" onclick="addToCart(${phone.id}, $('#${phone.id}').val())"
                        class="btn btn-secondary add-to-cart">Add To Cart</button>
            </td>
          </tr>
        </c:forEach>
      </table>
    </div>
    <template:pagination targetPage="productList" productPage="${productPage}" order="${order}" query="${query}">
    </template:pagination >
  </c:when>
  <c:otherwise>
    <div class="container">
      <div class="jumbotron">
        <h3>No phones found</h3>
        <a href="<c:url value="/productList"/>">
          <p>Go to main page</p>
        </a>
      </div>
    </div>
  </c:otherwise>
  </c:choose>
  <script>
      function addToCart(phoneId, quantity) {
          $.ajax({
                  url: "<c:url value="/ajaxCart"/>",
                  type: "POST",
                  data: JSON.stringify({
                      phoneId: phoneId,
                      quantity: quantity,
                      errorMessage: ""
                  }),
                  contentType: "application/json;charset=UTF-8",
                  dataType: "json",
                  success: updateCartInfo,
                  error: setErrorMessage
              }
          );
      }
      function updateCartInfo(cartInfo) {
          $('#itemsCount').text(cartInfo.itemsCount);
          $('#cartCost').text(cartInfo.cost);
          $('#error-message').text("");
      }
      function setErrorMessage(response) {
          var cartInfo = JSON.parse(response.responseText);
          $('#error-message').text(cartInfo.errorMessage);
      }
  </script>
</template:page>
