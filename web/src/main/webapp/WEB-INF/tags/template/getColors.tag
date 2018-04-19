<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ attribute name="phoneColors" type="java.util.Collection" required="true" %>
<c:forEach var="color" items="${phoneColors}" varStatus="index">
    ${color.code}
    <c:if test="${index.count != phoneColors.size()}">
        ,
    </c:if>
</c:forEach>