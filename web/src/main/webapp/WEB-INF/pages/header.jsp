 <meta charset="utf-8">
 <meta name="_csrf" content="${_csrf.token}"/>
 <meta name="_csrf_header" content="${_csrf.headerName}"/>
 <c:url var="contextUrl" value = "/" />
 <c:url value="/j_spring_security_logout" var="logoutUrl" />
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
 <script>var ctx = "${contextUrl}"</script>
 <script language="javascript" type="text/javascript" src="<c:url value="/resources/js/submitForm.js" />"></script>
 <p>
 <a href="${contextUrl}cart"> My cart. </a> Items: <b id="resultItems">${httpSessionCartService.getItemsNum()}</b> Overall price: $ <b id="resultPrice">${httpSessionCartService.getOverallPrice()}</b>
 <br>
 <br>
 <c:choose>
   <c:when test="${pageContext.request.userPrincipal.name != null}">
    <div style="display:inline"> ${pageContext.request.userPrincipal.name} | </div>
    <form action="${logoutUrl}" method="post" style="display:inline">
    <input type="submit" value="Logout" />
    <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
    </form>
       <sec:authorize access="hasRole('ROLE_ADMIN')">
       | <a href="${contextUrl}admin/orders">Admin area</a>
       </sec:authorize>
   </c:when>
   <c:otherwise>
     <a href="${contextUrl}login">Login</a>
   </c:otherwise>
 </c:choose>
 <br/>
 <br/>