<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<meta charset="utf-8">
<sec:csrfMetaTags />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<c:url var="contextUrl" value = "/" scope="request"/>
<c:url value="/j_spring_security_logout" var="logoutUrl" />
<div style="display:inline"> ${pageContext.request.userPrincipal.name} | </div>
    <form action="${logoutUrl}" method="post" style="display:inline">
    <input type="submit" value="Logout" />
    <sec:csrfInput/>
    </form>
<br>
<br>