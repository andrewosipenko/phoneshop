<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@ attribute name="pagination" type="com.es.phoneshop.web.bean.Pagination" required="true" %>
<%@ attribute name="targetPage" type="java.lang.String" required="true" %>
<%@ attribute name="order" type="com.es.core.model.phone.OrderBy" required="false" %>
<%@ attribute name="query" type="java.lang.String" required="false" %>
<ul class="pagination justify-content-end">
    <li class="page-item ${pagination.pageNumber eq pagination.startPaginationNumber ? 'disabled' : ''}">
        <a class="page-link"
           href="${pageContext.request.contextPath}/${targetPage}?page=${pagination.pageNumber-1}<c:if test="${not empty order}">&order=${order.name}</c:if><c:if test="${not empty query}">&query=<c:url value="${query}"/></c:if>"
           tabindex="-1">Previous</a>
    </li>
    <c:forEach var="i" begin="${pagination.startPaginationNumber}" end="${pagination.finishPaginationNumber}">
        <li class="page-item ${i eq pagination.pageNumber ? 'disabled' : ''}"><a class="page-link"
                                                                                 href="${pageContext.request.contextPath}/${targetPage}?page=${i}<c:if test="${not empty order}">&order=${order.name}</c:if><c:if test="${not empty query}">&query=<c:url value="${query}"/></c:if>">${i}</a>
        </li>
    </c:forEach>
    <li class="page-item ${pagination.pageNumber eq pagination.finishPaginationNumber ? 'disabled' : ''}">
        <a class="page-link"
           href="${pageContext.request.contextPath}/${targetPage}?page=${pagination.pageNumber+1}<c:if test="${not empty order}">&order=${order.name}</c:if><c:if test="${not empty query}">&query=<c:url value="${query}"/></c:if>">Next</a>
    </li>
</ul>
