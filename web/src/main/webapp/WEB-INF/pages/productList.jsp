<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
      integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>

<head>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>

<c:choose>
  <c:when test="${searchFor != null}">
    <h2 align="center">Results of searching '${searchFor}'</h2>
    <h6 align="center"><a href="/productList/1">Go back to main</a></h6>
  </c:when>
  <c:otherwise>
    <div>
    <div style="float: left;">
      <form action="/productList/search/1">
        <input type="text" name="searchFor">
        <input type="submit" value="search">
      </form>
    </div>
    <div style="float: right;">
      <form action="/cart">
        <button style="margin-left: 10px" class="btn btn-primary">My cart: ${cart.totalCount} items ${cart.totalPrice}$</button>
      </form>
        <form action="${pageContext.request.contextPath}/logout?">
            <button class="btn btn-error">Logout</button>
        </form>
    </div>
    </div>
  </c:otherwise>
</c:choose>
<p>
  <table border="1px" align="center">
    <thead>
      <tr>
        <td>Image</td>
        <td>Brand(<a href="?sortField=brand&sortOrder=asc">&lt;</a>, <a href="?sortField=brand&sortOrder=desc">&gt;</a>)
        </td>
        <td>Model(<a href="?sortField=model&sortOrder=asc">&lt;</a>, <a href="?sortField=model&sortOrder=desc">&gt;</a>)
        </td>
        <td>Colors</td>
        <td>Display Size(<a href="?sortField=displaySize&sortOrder=asc">&lt;</a>, <a
                href="?sortField=displaySize&sortOrder=desc">&gt;</a>)
        </td>
        <td>Price(<a href="?sortField=price&sortOrder=asc">&lt;</a>, <a href="?sortField=price&sortOrder=desc">&gt;</a>)
        </td>
        <td>Quantity</td>
        <td>Action</td>
      </tr>
    </thead>
<c:forEach var="stock" items="${stockList}">
      <tr>
        <td>
          <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${stock.phone.imageUrl}"
               style="width: 55%; height: 55%">
        </td>
        <td>${stock.phone.brand}</td>
        <td><a href="/productDetails/${stock.phone.id}">${stock.phone.model}</a></td>
        <td>
          <c:forEach var="color" items="${stock.phone.colors}" varStatus="i">
            <c:choose>
              <c:when test="${(fn:length(stock.phone.colors) - i.count) gt 0}">
                ${color.code},
              </c:when>
              <c:otherwise>
                ${color.code}
              </c:otherwise>
            </c:choose>
          </c:forEach>
        </td>
        <td>${stock.phone.displaySizeInches}"</td>
        <td>${stock.phone.price}$</td>
        <form>
          <td>
            <input type="text" id="quantity${stock.phone.id}" name="quantity">
        <p/>
        <span style="font-size: 75%" id="response${stock.phone.id}"></span>
        </td>
        <td>
          <button type="button" onclick="addToCart_ajax(${stock.phone.id});">Add to</button>
        </td>
        </form>
      </tr>
    </c:forEach>
  </table>
<nav aria-label="Page navigation example">
  <ul class="pagination">
    <c:if test="${sessionScope.get('currentPage_productList') != 1}">
      <li class="page-item"><a class="page-link" href="-1">Previous</a></li>
    </c:if>
    <c:forEach varStatus="i" begin="1" end="10">
      <c:if test="${i.count <= countOfPages}">
        <c:choose>
          <c:when test="${i.count == sessionScope.get('currentPage_productList')}">
            <li class="page-item active"><a class="page-link" href="${i.count}">${i.count}</a></li>
          </c:when>
          <c:otherwise>
            <li class="page-item"><a class="page-link" href="${i.count}">${i.count}</a></li>
          </c:otherwise>
        </c:choose>
      </c:if>
    </c:forEach>
    <c:if test="${sessionScope.get('currentPage_productList') < countOfPages}">
      <li class="page-item"><a class="page-link" href="+1">Next</a></li>
    </c:if>
  </ul>
</nav>
Page #${sessionScope.get("currentPage_productList")}

<script type="text/javascript">
  function addToCart_ajax(phoneId) {
    let quantity = $('#quantity' + phoneId).val();
    let responseField = $('#response' + phoneId);
    $.ajax({
      type: "post",
      url: "${pageContext.request.contextPath}/ajaxCart/",
      data: {'phoneId': phoneId, 'quantity': quantity},
      success: function (response) {
        responseField.text(response);
      },
      error: function (xhr, status, error) {
        var errorMessage = xhr.status + ': ' + xhr.statusText
        alert('Error - ' + errorMessage);
      }
    });
  }
</script>