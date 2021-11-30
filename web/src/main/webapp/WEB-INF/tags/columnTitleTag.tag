<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="sortField" required="true" %>
<%@ attribute name="searchText" %>
<h5>
    <jsp:doBody/>
    <a href="?sortField=${sortField}&sortOrder=ASCENDING&searchText=${searchText}">&#8593;</a>
    <a href="?sortField=${sortField}&sortOrder=DESCENDING&searchText=${searchText}">&#8595;</a>
</h5>