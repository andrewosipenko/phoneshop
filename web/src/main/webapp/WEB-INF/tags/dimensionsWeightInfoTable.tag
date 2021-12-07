<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="phone" required="true" type="com.es.core.model.phone.Phone" %>

<table class="table table-bordered">
    <tr>
        <td style="width: 30%">
            <spring:message code="titleTable.dimensionAndWeight.length"/>
        </td>
        <td>
            <c:choose>
                <c:when test="${not empty phone.lengthMm}">
                    ${phone.lengthMm} mm
                </c:when>
                <c:otherwise>
                    ---
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <td>
            <spring:message code="titleTable.dimensionAndWeight.width"/>
        </td>
        <td>
            <c:choose>
                <c:when test="${not empty phone.widthMm}">
                    ${phone.widthMm} mm
                </c:when>
                <c:otherwise>
                    ---
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <td>
            <spring:message code="titleTable.dimensionAndWeight.colors"/>
        </td>
        <td>
            <c:choose>
                <c:when test="${not empty phone.colors}">
                    <c:forEach var="color" items="${phone.colors}">
                        ${color}&ensp;
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    ---
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <td>
            <spring:message code="titleTable.dimensionAndWeight.weight"/>
        </td>
        <td>
            <c:choose>
                <c:when test="${not empty phone.weightGr}">
                    ${phone.weightGr} gr
                </c:when>
                <c:otherwise>
                    ---
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
</table>