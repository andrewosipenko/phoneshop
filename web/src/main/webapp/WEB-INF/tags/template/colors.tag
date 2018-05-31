<%@ tag pageEncoding="utf-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute type="java.util.Set<com.es.core.model.phone.Color>" name="colors"%>

<c:forEach var="color" items="${colors}">
  <template:color size="15" color="${color.code}"/>
</c:forEach>