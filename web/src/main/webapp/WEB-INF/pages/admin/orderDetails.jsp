<%@ page pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/template/order" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<template:page>
  <template:header/>

  <div class="m-5">

    <template:nav pageName="Order number: ${order.id}">
      <jsp:attribute name="content">
        <span class="navbar-text h4">Order status: <span class="badge badge-info">${order.status}</span></span>
      </jsp:attribute>
    </template:nav>

    <order:order-table order="${order}"/>

    <div class="row">
      <dl class="row">
        <dt class="col-3">First Name</dt>
        <dd class="col-9">${order.firstName}</dd>

        <dt class="col-3">Last name</dt>
        <dd class="col-9">${order.lastName}</dd>

        <dt class="col-3">Address</dt>
        <dd class="col-9">${order.deliveryAddress}</dd>

        <dt class="col-3">Phone</dt>
        <dd class="col-9">${order.contactPhoneNo}</dd>
      </dl>

      <c:if test="${not empty order.additionalInfo}">
        <div class="col-sm-6">
          <p>${order.additionalInfo}</p>
        </div>
      </c:if>
    </div>

    <div class="row">
      <a href="${pageContext.servletContext.contextPath}/admin/orders" class="btn btn-primary">Back</a>
      <c:if test="${order.status.isNew()}">
        <form action="${pageContext.servletContext.contextPath}/admin/orders/${order.id}" method="post" class="form-inline mb-0">
          <sec:csrfInput/>
          <button name="status" value="DELIVERED" class="btn btn-success ml-2" type="submit">Delivered</button>
          <button name="status" value="REJECTED" class="btn btn-danger ml-2" type="submit">Rejected</button>
        </form>
      </c:if>

    </div>
  </div>
</template:page>