<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@ attribute name="test" type="java.lang.String" required="true" %>

<c:choose>
    <c:when test="${param.sortBy eq test}">
        <i class="fa fa-fw fa-sort-asc"></i>
    </c:when>
    <c:when test="${param.sortBy eq test.concat('_desc')}">
        <i class="fa fa-fw fa-sort-desc"></i>
    </c:when>
    <c:otherwise>
        <i class="fa fa-fw fa-sort"></i>
    </c:otherwise>
</c:choose>
