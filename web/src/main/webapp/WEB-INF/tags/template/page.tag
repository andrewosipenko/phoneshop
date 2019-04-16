<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
    <link rel="stylesheet" href="/resources/bootstrap-4.1.3/css/bootstrap.min.css">
    <script src="/resources/bootstrap-4.1.3/js/bootstrap.min.js"></script>
    <script src="http://code.jquery.com/jquery-2.2.4.js" type="text/javascript"></script>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js" crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <div id="cartAndHeader">
        <div>
            <a href="<c:url value="/productList"/>">
                <img src="/resources/images/logo.jpg" width="250px" height="150px">
            </a>
        </div>
        <div id="login">
            <c:if test="${login == null}">
                <a class ="btn" href="<c:url value="/login"/>">Sign in</a>
            </c:if>
            <c:if test="${login != null}">
                ${login}
                <a class ="btn" href="<c:url value="/security_logout"/>">Log out</a>
            </c:if>
            <c:if test="${login == 'admin'}">
                <a class ="btn" href="<c:url value="/admin/orders"/>">Admin pages</a>
            </c:if>
            <form action="<c:url value="/cart"/>">
                <button id="buttonCart" type="submit" value="buttonCart">
                    My cart: <a id="cartAmount">${empty sessionScope.get("scopedTarget.cart").cartAmount ? '0' : sessionScope.get("scopedTarget.cart").cartAmount}</a>
                    items, price: <a id="subtotal">${empty sessionScope.get("scopedTarget.cart").subtotal ? '0' : sessionScope.get("scopedTarget.cart").subtotal}</a>$
                </button>
            </form>
        </div>
    </div>
    <hr>
    <jsp:doBody/>
</div>
</body>
</html>
