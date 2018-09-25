<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

 <meta charset="utf-8">
 <sec:csrfMetaTags />
 <c:url var="contextUrl" value = "/" scope="request"/>
 <c:url value="/j_spring_security_logout" var="logoutUrl" />
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
 <script>var ctx = "${contextUrl}"</script>
 <script language="javascript" type="text/javascript" src="<c:url value="/resources/js/submitForm.js" />"></script>
 <p>
 <a href="${contextUrl}cart"> My cart. </a> Items: <b id="resultItems">${httpSessionCartService.getItemsNum()}</b> Overall price: $ <b id="resultPrice">${httpSessionCartService.getOverallPrice()}</b>
 <br>
 <br>
 <c:choose>
   <c:when test="${not empty pageContext.request.userPrincipal.name}">
    <div style="display:inline"> ${pageContext.request.userPrincipal.name} | </div>
    <form action="${logoutUrl}" method="post" style="display:inline">
    <input type="submit" value="Logout" />
    <sec:csrfInput/>
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