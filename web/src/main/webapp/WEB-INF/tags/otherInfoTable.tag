<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="phone" required="true" type="com.es.core.model.phone.Phone" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table class="table table-bordered">
    <tr>
        <td style="width: 30%">Colors</td>
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
        <td>Device type</td>
        <td>
            <c:choose>
                <c:when test="${not empty phone.deviceType}">
                    ${phone.deviceType}
                </c:when>
                <c:otherwise>
                    ---
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <td>Bluetooth</td>
        <td>
            <c:choose>
                <c:when test="${not empty phone.bluetooth}">
                    ${phone.bluetooth}
                </c:when>
                <c:otherwise>
                    ---
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
</table>