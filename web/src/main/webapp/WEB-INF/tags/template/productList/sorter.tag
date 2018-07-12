<%@ tag pageEncoding="UTF-8" body-content="scriptless" trimDirectiveWhitespaces="true" %>
<%@ attribute name="sortBy" %>
<%@ attribute name="order" %>

<a style="color:${param['sortBy'] == sortBy? 'black' : 'gray'}; text-decoration: none"
   href="${requestScope['javax.servlet.forward.request_uri']}?sortBy=${sortBy}${not empty param['limit'] ? '&limit='.concat(param['limit']) : ''}${not empty param['page'] ? '&page='.concat(param['page']) : ''}${not empty param['search'] ? '&search='.concat(param['search']) : ''}">
  <i class="fas fa-long-arrow-alt-up"></i>
</a>
<a style="color:${param['sortBy'] == sortBy.concat("-desc")? 'black' : 'gray'}; text-decoration: none"
   href="${requestScope['javax.servlet.forward.request_uri']}?sortBy=${sortBy}-desc${not empty param['limit'] ? '&limit='.concat(param['limit']) : ''}${not empty param['page'] ? '&page='.concat(param['page']) : ''}${not empty param['search'] ? '&search='.concat(param['search']) : ''}">
  <i class="fas fa-long-arrow-alt-down"></i>
</a>