<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="phone" required="true" type="com.es.core.model.phone.Phone" %>

<table class="table table-bordered">
    <tr>
        <td style="width: 30%">Size</td>
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
        <td>Resolution</td>
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
        <td>Technology</td>
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
        <td>Pixel density</td>
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