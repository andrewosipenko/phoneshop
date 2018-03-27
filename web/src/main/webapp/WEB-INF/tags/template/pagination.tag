<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@ attribute name="productPage" type="com.es.phoneshop.web.bean.pagination.CurrentPhonePage" %>
<%@ attribute name="order" type="com.es.core.model.phone.OrderEnum"%>
<%@ attribute name="query" type="java.lang.String" required="false" %>
<%@ attribute name="targetPage" type="java.lang.String" required="true" %>
<nav aria-label="...">
    <ul class="pagination">
        <li class="page-item ${productPage.currentPageNumber eq 1 ? 'disabled' : ''}">
            <a class="page-link"
               href="${pageContext.request.contextPath}/${targetPage}?page=${productPage.currentPageNumber-1}
<c:if test="${not empty order}">&order=${order.sql}</c:if>
<c:if test="${not empty query}">&query=<c:url value = "${query}"/></c:if>"
               tabindex="-1">Previous</a>
        </li>
        <c:forEach var="i" begin="${productPage.firstShownPageNumber}" end="${productPage.lastShownPageNumber}">
            <li class="page-item ${i eq productPage.currentPageNumber ? 'disabled' : ''}">
                <a class="page-link" href="${pageContext.request.contextPath}/${targetPage}?page=${i}
<c:if test="${not empty order}">&order=${order.sql}</c:if>
<c:if test="${not empty query}">&query=<c:url value = "${query}"/></c:if>">${i}</a>
            </li>
        </c:forEach>
        <li class="page-item ${productPage.currentPageNumber eq productPage.lastPageNumber ? 'disabled' : ''}">
            <a class="page-link"
               href="${pageContext.request.contextPath}/${targetPage}?page=${productPage.currentPageNumber+1}
<c:if test="${not empty order}">&order=${order.sql}</c:if>
<c:if test="${not empty query}">&query=<c:url value = "${query}"/></c:if>">Next</a>
        </li>
    </ul>
</nav>