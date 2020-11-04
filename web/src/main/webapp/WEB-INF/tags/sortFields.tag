<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="sort" required="true" %>
<%@ attribute name="order" required="true" %>


<a href="?sort=${sort}&order=${order}&query=${param.query}"
   style="${sort eq param.sort and order eq param.order ? 'font-weight: bold' : ''}">
    <img src="
    ${sort eq param.sort and order eq param.order and order eq 'ASC'
    ? 'https://img.icons8.com/plasticine/18/000000/long-arrow-down.png'
    : order eq 'ASC'
    ?'https://img.icons8.com/carbon-copy/18/000000/long-arrow-down.png'
    : sort eq param.sort and order eq param.order and order eq 'DESC'
    ?'https://img.icons8.com/plasticine/18/000000/long-arrow-up.png'
    :'https://img.icons8.com/carbon-copy/18/000000/long-arrow-up.png'
    }" alt="${order}"/>
</a>