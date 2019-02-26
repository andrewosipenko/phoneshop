<%@ attribute name="page" required="true" type="java.lang.Integer" %>
<%@ attribute name="sort" required="false" type="java.lang.String" %>
<%@ attribute name="text" required="true" type="java.lang.String" %>
<%@ attribute name="order" required="false" type="java.lang.String" %>
<%@ attribute name="query" required="false" type="java.lang.String" %>

<a href="${pageContext.servletContext.contextPath}/productList?${page == null ? "" : "page=".concat(page)}${sort.length() == 0 ? "" : "&sort=".concat(sort)}${order.length() == 0 ? "" : "&order=".concat(order)}${query.length() == 0 ? "" : "&text=".concat(query)}">
    ${text}
</a>
