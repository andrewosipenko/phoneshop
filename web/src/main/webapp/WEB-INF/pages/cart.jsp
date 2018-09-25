<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
  <title>My cart</title>
  <jsp:include page="header.jsp"/>
</head>
<body>
<h2>Cart</h2>
<p>
  <a href="${contextUrl}productList"> Back to product list</a>
</p>
<table class="table" border="1px">
    <thead>
      <tr>
        <td>Brand</td>
        <td>Model</td>
        <td>Colors</td>
        <td>Display size</td>
        <td>Price</td>
        <td>Quantity</td>
        <td>Action</td>
      </tr>
    </thead>
    <form:form method="POST" action="${contextUrl}cart/update" modelAttribute="cartUpdateForm">
    <c:forEach var="phoneEntry" items="${phoneEntries}" varStatus="loop">
      <tr>
        <td>${phoneEntry.getKey().brand}</td>
        <td>${phoneEntry.getKey().model}</td>
        <td>
          <c:forEach var="color" items="${phoneEntry.getKey().colors}">
            <c:out value="${color.code}  " />
          </c:forEach>
        </td>
        <td>${phoneEntry.getKey().displaySizeInches} inches</td>
        <td>$ ${phoneEntry.getKey().price}</td>
        <td>
          <form:input path="entities[${loop.index}].quantity"/>
          <br>
          <form:errors path="entities[${loop.index}].quantity" style="color:red" />
          <form:hidden path="entities[${loop.index}].phoneId"/>
        </td>
        <td>
           <form:button name="delete" value="${phoneEntry.getKey().id}">Delete</form:button>
        </td>
      </tr>
    </c:forEach>
</table>
<div align="right" style="width:45%">
  <c:if test="${!phoneEntries.isEmpty()}">
    <input type="submit" name="update" value = "Update" />
  </c:if>
    </form:form>
  <c:if test="${!phoneEntries.isEmpty()}">
    <form action="${contextUrl}order">
    <input type="submit" value="Order" />
    </form>
  </c:if>
  </div>
</body>
</html>
