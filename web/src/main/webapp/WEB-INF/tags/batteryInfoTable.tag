<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="phone" required="true" type="com.es.core.model.phone.Phone" %>

<table class="table table-bordered">
    <tr>
        <td style="width: 30%">Talk time</td>
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
        <td>Stand by time</td>
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
        <td>Battery capacity</td>
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