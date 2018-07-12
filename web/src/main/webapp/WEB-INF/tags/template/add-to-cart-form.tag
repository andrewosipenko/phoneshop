<%@ tag pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ attribute name="phoneId" required="true" %>
<%@ attribute name="value" %>
<%@ attribute type="java.util.List<java.lang.String>" name="errors" %>
<form action="addPhone" name="${phoneId}" class="quantity-form form mb-0">
  <sec:csrfInput/>
  <div class="input-group">
    <input type="number" hidden name="phoneId" value="${phoneId}">
    <input type="text" class="form-control quantity-input ${not empty errors? "is-invalid" : ""}"
           name="quantity" value="${value}" autocomplete="off">
    <div class="invalid-tooltip">
      <c:forEach items="${errors}" var="message"><p class="mb-0">${message}</p></c:forEach>
    </div>
    <input type="submit" hidden>
  </div>
</form>