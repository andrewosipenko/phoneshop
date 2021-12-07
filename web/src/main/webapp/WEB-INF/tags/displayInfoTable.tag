<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="phone" required="true" type="com.es.core.model.phone.Phone" %>

<table class="table table-bordered">
    <tr>
        <td style="width: 30%">
            <spring:message code="titleTable.display.size"/>
        </td>
        <td>
            <c:choose>
                <c:when test="${not empty phone.displaySizeInches}">
                    ${phone.displaySizeInches}"
                </c:when>
                <c:otherwise>
                    ---
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <td>
            <spring:message code="titleTable.display.resolution"/>
        </td>
        <td>
            <c:choose>
                <c:when test="${not empty phone.displayResolution}">
                    ${phone.displayResolution}
                </c:when>
                <c:otherwise>
                    ---
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <td>
            <spring:message code="titleTable.display.technology"/>
        </td>
        <td>
            <c:choose>
                <c:when test="${not empty phone.displayTechnology}">
                    ${phone.displayTechnology}
                </c:when>
                <c:otherwise>
                    ---
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <td>
            <spring:message code="titleTable.display.pixelDensity"/>
        </td>
        <td>
            <c:choose>
                <c:when test="${not empty phone.pixelDensity}">
                    ${phone.pixelDensity}
                </c:when>
                <c:otherwise>
                    ---
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
</table>