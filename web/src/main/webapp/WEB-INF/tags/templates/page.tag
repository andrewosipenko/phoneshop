<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
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
            <div class="cart-container">
                <a href="#" class="btn btn-outline-dark">
                    My Cart: <span id="cart-count">0</span> Items (<span id="cart-cost">0</span>$)
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
        <input type="hidden" id="context-path" value="${pageContext.request.contextPath}">
    </body>
</html>