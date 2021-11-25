<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="headerName" type="java.lang.String" required="true" %>
<%@ attribute name="sortByLabel" type="java.lang.String" required="true" %>

<c:set var="query" value="${productPage.query}"/>
<c:set var="sortBy" value="${productPage.sortBy}"/>
<c:set var="sqlOrderByKeyword" value="${productPage.sqlOrderByKeyword}"/>
<c:set var="currentPage" value="${productPage.currentPage}"/>

<a class="text-dark" style="text-decoration: none"
   href="${pageContext.request.contextPath}/productList?<c:if test="${not empty query}">query=${query}&</c:if>sortBy=${sortByLabel}&sqlOrderByKeyword=${(sortBy eq sortByLabel && sqlOrderByKeyword eq "ASC") ? "DESC" : "ASC"}&page=${currentPage}">
    ${headerName} &#9650;&#9660;>
</a>
