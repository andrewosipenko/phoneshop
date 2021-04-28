<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>

<html>
<head>
    <title>${pageTitle}</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Phone List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
</head>

<body style="margin: 30px">
<header>
    <h5><a href="#" class="float-end">Login</a></h5>
    <br>
    <div class="display-1">
        <a style="color: black" class="text-decoration-none"
           href="${pageContext.servletContext.contextPath}/productList">Phonify</a>
        <a href="${pageContext.servletContext.contextPath}/cart" class="btn btn-dark btn-lg float-end"
           style="margin-top: 25px">
            My cart: ${cart.totalQuantity} items ${cart.totalCost} $
        </a>
    </div>
    <hr style="height: 2px">
</header>
<main>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
            crossorigin="anonymous"></script>
    <jsp:doBody/>
</main>
<footer>
    <p>(c) Expert-Soft</p>
</footer>
</body>
</html>