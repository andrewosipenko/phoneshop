<%@ tag pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ attribute name="path" required="true" %>
<%@ attribute name="id" required="true" %>
<%@ attribute name="label" required="true" %>
<%@ attribute name="placeholder"%>

<div class="form-group row pb-3">
  <label for="${id}" class="col-2">${label}</label>
  <div class="input-group col-3">
    <form:input path="${path}" id="${id}" cssClass="formControl col"
                cssErrorClass="form-control col is-invalid" placeholder="${placeholder}"/>
    <div class="invalid-tooltip">
      <form:errors path="${path}"/>
    </div>
  </div>
</div>