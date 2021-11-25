<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="sort" required="true" %>
<%@ attribute name="order" required="true" %>

<a href="?sortField=${sort}&sortOrder=${order}&query=${param.query}"
class="sort-link ${sort eq param.sortField and order eq param.sortOrder ? 'current-sort' : ''}"><jsp:doBody/></a>