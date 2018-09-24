<meta charset="utf-8">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<c:url var="contextUrl" value = "/" />
<c:url value="/j_spring_security_logout" var="logoutUrl" />
<div style="display:inline"> ${pageContext.request.userPrincipal.name} | </div>
    <form action="${logoutUrl}" method="post" style="display:inline">
    <input type="submit" value="Logout" />
    <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
    </form>
<br>
<br>