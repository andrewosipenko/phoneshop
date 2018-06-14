<%@tag pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="cartQuantity"%>
<%@ attribute name="cartSubTotal"%>

<nav class="navbar navbar-dark bg-dark pr-5 pl-5 pd-5">
  <div>
    <a class="navbar-brand" href="#"><h2>Phoneshop</h2></a>
  </div>
  <div>
    <div class="text-right mb-1">
      <c:choose>
        <c:when test="${not empty pageContext.request.remoteUser}">
          <div class="float-right d-inline">
            <span class="text-light"><c:out value="${pageContext.request.remoteUser}"/></span>
            <a href="<c:url value="/admin/orders"/>" class="ml-2">Admin</a>
            <form action="<c:url value="/logout"/>" method="post" class="d-inline-flex mb-0 ml-2">
              <input type="text" hidden name="${_csrf.parameterName}" value="${_csrf.token}">
              <button type="submit" class="btn btn-link p-0">Logout</button>
            </form>
          </div>
        </c:when>
        <c:otherwise>
          <a href="<c:url value="/login"/>">Login</a>
        </c:otherwise>
      </c:choose>
    </div>

    <c:if test="${not empty cartSubTotal}">
      <div>
        <a href="${pageContext.servletContext.contextPath}/cart" class="btn btn-warning">My cart: <span id="cart-amount">${cartQuantity}</span> items <span id="cart-price">${cartSubTotal}</span>$</a>
      </div>
    </c:if>
  </div>
</nav>
