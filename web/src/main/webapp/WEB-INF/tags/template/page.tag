<%@ tag body-content="scriptless" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="scripts" fragment="true" %>
<!DOCUMENT html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="view" content="width=device-width initial-scale=1 shrink-to-fit=no">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/4.0.0/css/bootstrap.min.css" >
  <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/font-awesome/5.0.13/web-fonts-with-css/css/fontawesome-all.min.css">

  <script src="${pageContext.request.contextPath}/webjars/jquery/3.2.1/jquery.min.js"></script>
  <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.0.0/js/bootstrap.min.js"></script>

  <jsp:invoke fragment="head"/>
</head>
<body>
  <jsp:doBody/>
  <jsp:invoke fragment="scripts"/>
</body>
</html>