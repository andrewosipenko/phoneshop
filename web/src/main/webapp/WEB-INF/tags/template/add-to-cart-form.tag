<%@ tag pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@ attribute name="phoneId" required="true" %>
<%@ attribute name="value" %>
<%@ attribute name="errorMessage" %>
<form action="addPhone" name="${phoneId}" class="quantity-form form mb-0">
  <div class="input-group">
    <input type="number" hidden name="phoneId" value="${phoneId}">
    <input type="text" class="form-control quantity-input ${not empty errorMessage? "is-invalid" : ""}"
           name="quantity" value="${value}">
    <div class="invalid-tooltip">
      ${errorMessage}
    </div>
    <input type="submit" hidden>
  </div>
</form>