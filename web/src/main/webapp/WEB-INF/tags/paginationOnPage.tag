<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ attribute name="totalNumber" type="java.lang.Integer" required="true" %>
<%@ attribute name="currentPage" type="java.lang.Integer" required="true" %>

<div class="float-right">
    <c:set var="query" value="${productPage.query}"/>
    <c:set var="sortBy" value="${productPage.sortBy}"/>
    <c:set var="sqlOrderByKeyword" value="${productPage.sqlOrderByKeyword}"/>
    <uL class="pagination justify-content-end">

        <li class="page-item ${currentPage eq 1 ? "disable" : ""}">
            <a class="page-link"
               href="${pageContext.request.contextPath}/productList?<c:if test="${not empty query}">query=${query}&</c:if><c:if test="${not empty sortBy}">sortBy=${sortBy}&</c:if>sqlOrderByKeyword=${sqlOrderByKeyword}&page=${currentPage - 1}"
               aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
                <span class="sr-only">Previous</span>
            </a>
        </li>

        <c:set var="startPage" value="${currentPage <= 5 ? 1 : currentPage - 5}"/>
        <c:set var="endPage" value="${startPage + 9 > totalNumber ? totalNumber : startPage + 9}"/>
        <c:set var="startPage" value="${endPage - 9 <= 0 ? 1 : endPage - 9}"/>
        <c:forEach var="i" begin="${startPage}" end="${endPage}">
            <li class="page-item ${i eq currentPage ? "active" : ""}">
                <a class="page-link"
                   href="${pageContext.request.contextPath}/productList?<c:if test="${not empty query}">query=${query}&</c:if><c:if test="${not empty sortBy}">sortBy=${sortBy}&</c:if>sqlOrderByKeyword=${sqlOrderByKeyword}&page=${i}">${i}</a>
            </li>
        </c:forEach>

        <li class="page-link ${currentPage eq totalNumber ? "disabled" : ""}">
            <a class="page-ling"
               href="${pageContext.request.contextPath}/productList?<c:if test="${not empty query}">query=${query}&</c:if><c:if test="${not empty sortBy}">sortBy=${sortBy}&</c:if>sqlOrderByKeyword=${sqlOrderByKeyword}&page=${currentPage + 1}"
               aria-label="Next">
                <span class="sr-only">Next</span>
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>

    </uL>
</div>
