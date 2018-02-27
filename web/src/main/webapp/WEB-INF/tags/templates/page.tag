<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@ attribute name="catalogTabIsActive" type="java.lang.Boolean" %>
<jsp:useBean id="cart" scope="session" class="com.es.core.cart.Cart"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
<div class="topnav">
    <a ${catalogTabIsActive ? 'class="active"' : ''} href="${pageContext.request.contextPath}/productList">Catalog</a>
    <div class="cart-container">
        <a href="${pageContext.request.contextPath}/cart" class="btn btn-outline-dark">
            My Cart: <span class="cart-count">${not empty cart ? cart.countItems : 0}</span> Items ($ <span
                class="cart-cost">${not empty cart ? cart.cost : 0}</span>)
        </a>
    </div>
    <div class="search-container">
        <form action="${pageContext.request.contextPath}/productList">
            <input type="text" placeholder="Search.." name="query">
            <button type="submit"><i class="fa fa-search"></i></button>
        </form>
    </div>
</div>
<div class="container">
    <jsp:doBody/>
</div>
<script>
    var context_path = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/resources/js/cart.js"></script>
</body>
</html>