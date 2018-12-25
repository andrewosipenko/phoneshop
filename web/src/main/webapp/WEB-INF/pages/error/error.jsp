<!doctype html>
<html lang="en">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="UTF-8">
    <title>Product list</title>
</head>
<body>
    <div class = "container">
        <h3>
            Seems you've done something illegal.
        </h3>
        <p class="error">
            <c:if test="${errorMessage ne null}">
                Error message: ${errorMessage}
            </c:if>
        </p>
        <p>
            Go back to <a class="hyperlink" href="/productList"> product list</a> page.
        </p>
    </div>
</body>
</html>