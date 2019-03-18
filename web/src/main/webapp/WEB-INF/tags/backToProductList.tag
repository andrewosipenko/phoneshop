<%@attribute name="text" type="java.lang.String" required="true" %>
<form action="${pageContext.servletContext.contextPath}/productList">
    <input type="hidden" value="${1}" name="page">
    <button class="btn button-on-page">${text}</button>
</form>