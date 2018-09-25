<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
  <title>Phone shop</title>
  <jsp:include page="header.jsp"/>
</head>
<body>
  <c:choose>
    <c:when test="${search != null}">
      <c:out value="Result list for '${search}'" />
      </br>
    </c:when>
  </c:choose>
  <a href="${contextUrl}productList">
    <c:out value="Reset search" />
  </a>
  <form action = "${contextUrl}productList" method="GET">
    <input type = "text" name = "search"/>
    <input type = "submit" value = "Search!" />
  </form>
  <table border="1px" class="table">
    <thead>
      <tr>
        <td>Image</td>
        <td>Brand
          <a href="?order=brand+asc${searchParameter}">
            ↑
          </a>
          <a href="?order=brand+desc${searchParameter}">
            ↓
          </a>
        </td>
        <td>Model
          <a href="?order=model+asc${searchParameter}">
            ↑
          </a>
          <a href="?order=model+desc${searchParameter}">
            ↓
          </a>
        </td>
        <td>Colors</td>
        <td>Display size
          <a href="?order=displaySizeInches+asc${searchParameter}">
            ↑
          </a>
          <a href="?order=displaySizeInches+desc${searchParameter}">
            ↓
          </a>
        </td>
        <td>Price
          <a href="?order=price+asc${searchParameter}">
            ↑
          </a>
          <a href="?order=price+desc${searchParameter}">
            ↓
          </a>
        </td>
        <td>Quantity</td>
        <td>Action</td>
        <td>More info</td>
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
            <c:out value="${color.code}" />
            <br/>
            <br/>
          </c:forEach>
        </td>
        <td>${phone.displaySizeInches} ″</td>
        <td>$ ${phone.price}</td>
        <td>
          <form class="addPhone">
            <input name="quantity" type="text">
            <br>
            <div style="text-align: center;">
            <span style="color:red;" id="error${phone.id}"></span>
            </div>
            <input name="phoneId" value="${phone.id}" type="hidden">
        </td>
        <td>
          <input value="Add to cart" onclick="submitForm(this.form)" type="button">
          </form>
        </td>
        <td>
          <form action = "${contextUrl}productDetails/${phone.id}" method="GET">
            <input type = "submit" value = "Details"/>
          </form>
        </td>
      </tr>
    </c:forEach>
  </table>
  <a href="${pageUrl}&page=1">
    <c:out value="<<" />
  </a>
  <c:forEach var="num" items="${pagesNum}">
    <a href="${pageUrl}&page=${num}">
    <c:if test="${num == page}">
      <b>
    </c:if>
      <c:out value="${num}" />
    <c:if test="${num == page}">
      </b>
    </c:if>
    </a>
  </c:forEach>
  <a href="${pageUrl}&page=${total}">
    <c:out value=">>" />
  </a>
</p>
</body>
</html>