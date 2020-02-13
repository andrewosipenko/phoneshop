<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="sort" required="true" %>

<c:set var="currentSort" value="${param.sort eq sort and param.order eq 'desc'}"/>
<a href="${pageContext.request.contextPath}/productList?sort=${sort}&order=desc&searchQuery=${param.searchQuery}"
   style ="${currentSort? 'font-weight:bold': ''}">&#8593</a>