<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="phone" required="true" type="com.es.core.model.phone.Phone" %>

<table class="table table-bordered">
    <tr>
        <td style="width: 30%">
            <spring:message code="titleTable.battery.talkTime"/>
        </td>
        <td>
            <c:choose>
                <c:when test="${not empty phone.talkTimeHours.intValue()}">
                    ${phone.talkTimeHours.intValue()} hours
                </c:when>
                <c:otherwise>
                    ---
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <td>
            <spring:message code="titleTable.battery.standByTime"/>
        </td>
        <td>
            <c:choose>
                <c:when test="${not empty phone.standByTimeHours.intValue()}">
                    ${phone.standByTimeHours.intValue()} hours
                </c:when>
                <c:otherwise>
                    ---
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <td>
            <spring:message code="titleTable.battery.batteryCapacity"/>
        </td>
        <td>
            <c:choose>
                <c:when test="${not empty phone.batteryCapacityMah}">
                    ${phone.batteryCapacityMah} mAh
                </c:when>
                <c:otherwise>
                    ---
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
</table>