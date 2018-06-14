<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<template:page>
  <template:header/>

  <div class="m-5 ">
    <template:nav pageName="Orders"/>

    <div class="row">
      <table class="table">
        <thead>
        <tr>
          <td>Order number</td>
          <td>Customer</td>
          <td>Phone</td>
          <td>Address</td>
          <td>Date</td>
          <td>Total price</td>
          <td>Status</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orders}" var="order">
          <tr>
            <td><a href="${pageContext.servletContext.contextPath}/admin/orders/${order.id}">${order.id}</a></td>
            <td>${order.firstName} ${order.lastName}</td>
            <td>${order.contactPhoneNo}</td>
            <td>${order.deliveryAddress}</td>
            <td>${order.orderDate}</td>
            <td>${order.totalPrice}$</td>
            <td><span class="badge badge-info">${order.status}</span></td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>

  </div>
</template:page>