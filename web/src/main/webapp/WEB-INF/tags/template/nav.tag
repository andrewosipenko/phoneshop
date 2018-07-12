<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ attribute name="content" fragment="true" %>
<%@ attribute name="pageName"%>
<nav class="navbar navbar-light bg-light mb-3">
  <h4>${pageName}</h4>
  <jsp:invoke fragment="content"/>
</nav>