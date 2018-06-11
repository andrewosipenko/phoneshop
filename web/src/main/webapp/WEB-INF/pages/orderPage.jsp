<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/template/order" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<template:page>
  <jsp:body>
    <template:header/>

    <div class="mt-5 mr-5 ml-5">
      <nav class="navbar navbar-light bg-light">
        <h4>Order</h4>
      </nav>

      <div class="row mt-3 mb-3">
        <a class="btn btn-primary col-2" href="${pageContext.servletContext.contextPath}/cart">Back to cart</a>
      </div>


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
          <c:forEach items="${order.orderItems}" var="cartEntry">
            <tr>
              <td class="align-middle">
                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${cartEntry.phone.imageUrl}"
                     height="80">
              </td>
              <td class="align-middle">${cartEntry.phone.brand}</td>
              <td class="align-middle">${cartEntry.phone.model}</td>
              <td class="align-middle">
                <template:colors colors="${cartEntry.phone.colors}"/>
              </td>
              <td class="align-middle">${cartEntry.phone.displaySizeInches}"</td>
              <td class="align-middle">${cartEntry.quantity}</td>
              <td class="align-middle">
                <c:choose>
                  <c:when test="${not empty cartEntry.phone.price}">$${cartEntry.phone.price}</c:when>
                  <c:otherwise>unknown</c:otherwise>
                </c:choose>
              </td>
            </tr>
          </c:forEach>
          <tr>
            <td colspan="5">
              <c:if test="${requestScope['org.springframework.validation.BindingResult.order'].hasFieldErrors('orderItems')}">
                <div class="alert alert-danger mb-0">Some products were removed form your order as they are out of stock. <a href="${pageContext.servletContext.contextPath}/cart" class="text-danger">Go to cart</a> if you want to edit
                  order.</div>
              </c:if>
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

      <div>
        <form:form action="${pageContext.servletContext.contextPath}/order" method="post" modelAttribute="order">
          <order:input-group path="firstName" id="firstName" label="First name" placeholder="First name"/>
          <order:input-group path="lastName" id="lastName" label="Last name" placeholder="Last name"/>
          <order:input-group path="deliveryAddress" id="address" label="Address" placeholder="Delivery address"/>
          <order:input-group path="contactPhoneNo" id="phone" label="Phone" placeholder="+"/>

          <div class="form-group row">
            <div class="col-5">
              <form:textarea path="additionalInfo" placeholder="Additional information" class="col"/>
            </div>
          </div>
          <input type="submit" class="btn btn-primary" value="Order">
        </form:form>
      </div>
    </div>

  </jsp:body>
</template:page>