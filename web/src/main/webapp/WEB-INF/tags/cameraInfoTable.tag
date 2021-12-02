<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="phone" required="true" type="com.es.core.model.phone.Phone" %>

<table class="table table-bordered">
    <tr>
        <td style="width: 30%">Front</td>
        <td>
            <c:choose>
                <c:when test="${not empty phone.frontCameraMegapixels}">
                    ${phone.frontCameraMegapixels}
                </c:when>
                <c:otherwise>
                    ---
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <td>Back</td>
        <td>
            <c:choose>
                <c:when test="${not empty phone.backCameraMegapixels}">
                    ${phone.backCameraMegapixels}
                </c:when>
                <c:otherwise>
                    ---
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
</table>