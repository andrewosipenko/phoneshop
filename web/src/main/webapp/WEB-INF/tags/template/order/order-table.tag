<%@ tag pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ attribute name="errorBlock" fragment="true" %>
<%@ attribute name="order" type="com.es.core.model.order.Order" %>

<div class="row">
  <table class="table">
    <thead>
    <tr>
      <td scope="col">Image</td>
      <td scope="col">Brand</td>
      <td scope="col">Model</td>
      <td scope="col">Color</td>
      <td scope="col">Display size</td>
      <td scope="col">Quantity</td>
      <td scope="col">Price</td>
    </tr>
    </thead>
    <c:forEach items="${order.orderItems}" var="item">
      <tr>
        <td class="align-middle">
          <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${item.phone.imageUrl}"
               height="80">
        </td>
        <td class="align-middle">${item.phone.brand}</td>
        <td class="align-middle">${item.phone.model}</td>
        <td class="align-middle">
          <template:colors colors="${item.phone.colors}"/>
        </td>
        <td class="align-middle">${item.phone.displaySizeInches}"</td>
        <td class="align-middle">${item.quantity}</td>
        <td class="align-middle">
          <c:choose>
            <c:when test="${not empty item.phone.price}">$${item.phone.price}</c:when>
            <c:otherwise>unknown</c:otherwise>
          </c:choose>
        </td>
      </tr>
    </c:forEach>
    <tr>
      <td colspan="5">
        <jsp:invoke fragment="errorBlock"/>
      </td>
      <td class="border-warning">Subtotal</td>
      <td class="border-warning">${order.subtotal}$</td>
    </tr>
    <tr>
      <td colspan="5" class="border-0">
      </td>
      <td >Delivery</td>
      <td>${order.deliveryPrice}$</td>
    </tr>
    <tr>
      <td colspan="5" class="border-0"></td>
      <td class="border-warning">TOTAL</td>
      <td class="border-warning">${order.totalPrice}$</td>
    </tr>
  </table>
</div>
