<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="sort" required="true" %>

<c:set var="currentSort" value="${param.sort eq sort and param.order eq 'asc'}"/>
<a href="${pageContext.request.contextPath}/productList?sort=${sort}&order=asc&searchQuery=${param.searchQuery}"
   style ="${currentSort? 'font-weight:bold': ''}">&#8595</a>