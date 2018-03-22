<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@ attribute name="orderOfLink" type="com.es.core.model.phone.OrderBy" %>
<%@ attribute name="nameLink" type="java.lang.String" %>
<a href="${pageContext.request.contextPath}/productList?order=${orderOfLink.name}${order eq orderOfLink ? '_desc' : ''}<c:if test="${not empty query}">&query=<c:url value = "${query}"/></c:if>"><span>${nameLink}</span><i
        class="fa fa-fw fa-sort"></i></a>