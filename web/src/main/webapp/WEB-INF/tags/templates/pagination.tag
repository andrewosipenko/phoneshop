<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@ attribute name="pagination" type="com.es.phoneshop.web.bean.Pagination" %>
<%@ attribute name="order" type="com.es.core.model.phone.OrderBy" %>
<%@ attribute name="query" type="java.lang.String" required="false" %>
<ul class="pagination justify-content-end">
    <li class="page-item ${pagination.pageNumber eq pagination.startPaginationNumber ? 'disabled' : ''}">
        <a class="page-link"
           href="${pageContext.request.contextPath}/productList?page=${pagination.pageNumber-1}&order=${order.name}<c:if test="${not empty query}">&query=<c:url value="${query}"/></c:if>"
           tabindex="-1">Previous</a>
    </li>
    <c:forEach var="i" begin="${pagination.startPaginationNumber}" end="${pagination.finishPaginationNumber}">
        <li class="page-item ${i eq pagination.pageNumber ? 'disabled' : ''}"><a class="page-link"
                                                                                 href="${pageContext.request.contextPath}/productList?page=${i}&order=${order.name}<c:if test="${not empty query}">&query=<c:url value="${query}"/></c:if>">${i}</a>
        </li>
    </c:forEach>
    <li class="page-item ${pagination.pageNumber eq pagination.finishPaginationNumber ? 'disabled' : ''}">
        <a class="page-link"
           href="${pageContext.request.contextPath}/productList?page=${pagination.pageNumber+1}&order=${order.name}<c:if test="${not empty query}">&query=<c:url value="${query}"/></c:if>">Next</a>
    </li>
</ul>
