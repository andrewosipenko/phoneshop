<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/template/order" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<template:page>
  <jsp:body>
    <template:header/>

    <div class="m-5">
      <template:nav pageName="Order"/>

      <div class="row mt-3">
        <a class="btn btn-primary col-2" href="${pageContext.servletContext.contextPath}/cart">Back to cart</a>
      </div>


      <order:order-table order="${order}">
        <jsp:attribute name="errorBlock">
          <c:if test="${requestScope['org.springframework.validation.BindingResult.order'].hasFieldErrors('orderItems')}">
            <div class="alert alert-danger mb-0">Some products were removed form your order as they are out of stock. <a href="${pageContext.servletContext.contextPath}/cart" class="text-danger">Go to cart</a> if you want to edit
              order.</div>
          </c:if>
        </jsp:attribute>
      </order:order-table>

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