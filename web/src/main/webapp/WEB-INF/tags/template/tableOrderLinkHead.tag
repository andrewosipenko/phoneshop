<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@ attribute name="currentOrder" type="com.es.core.model.phone.OrderEnum" %>
<%@ attribute name="nameOfLink" type="java.lang.String" %>
<a href="${pageContext.request.contextPath}/productList?
order=${currentOrder.name}${order eq currentOrder ? '_desc' : ''}
<c:if test="${not empty query}">
&query=<c:url value = "${query}"/>
</c:if>">
    <b>${nameOfLink}</b><i class="fa fa-fw fa-sort"> </i>
    </a>