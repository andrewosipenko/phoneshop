<%@attribute name="pageTitle" type="java.lang.String" required="true" %>

<html>
<head>
    <title>${pageTitle}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <link href=${pageContext.request.contextPath}/resources/css/main.css  rel="stylesheet" type="text/css" />
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/ajaxAddCartItem.js"></script>
</head>
<body>
    <jsp:doBody/>
</body>
</html>