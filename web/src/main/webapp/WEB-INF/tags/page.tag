<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="pageTitle" required="true" %>

<html>

<head>
    <title>${pageTitle}</title>
    <!-- connection jquery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
    <!-- icon -->
    <link rel="apple-touch-icon" href="static/favicon/apple-touch-icon.png" type="image/x-icon">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" rel="stylesheet"/>
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet"/>
    <!-- MDB -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/3.6.0/mdb.min.css" rel="stylesheet"/>

    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
</head>
<body>

<div class="cover-container d-flex w-100 h-100 mx-auto flex-column">
    <header class="mb-auto">
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container-fluid">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/productList">Phonify</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse"
                        data-target="#navbarSupportedContent"
                        aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav mr-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page"
                               href="${pageContext.request.contextPath}/productList">Home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
                        </li>
                    </ul>
                </div>
                <form class="d-flex" action="<c:url value="/productList"/>">
                    <input class="form-control mr-2" type="search" placeholder="Search" aria-label="Search"
                           name="query">
                    <button class="btn btn-outline-success" type="submit">Search</button>
                </form>
            </div>
        </nav>
        <div class="float-right">
            <c:if test="${infoCart != null}">
                <c:set var="infoCart" value="${infoCart}"/>
                <a href="${pageContext.request.contextPath}/cart" class="btn btn-outline-dark" id="updateCartInfo">
                    My cart: <span id="totalCount">${infoCart.totalCount}</span> items <span
                        id="subtotalPrice">${infoCart.subtotalPrice}</span> $
                </a>
            </c:if>
        </div>
    </header>

    <main>
        <div class="container mt-2 px-lg-2">
            <jsp:doBody/>
        </div>
    </main>

    <footer class="mt-auto footer bg-dark text-center text-white ">
        <!-- Grid container -->
        <div class="container p-4 pb-0">
            <!-- Section: Social media -->
            <section class="mb-4">
                <!-- Facebook -->
                <a class="btn btn-outline-light btn-floating m-1"
                   href="https://www.facebook.com/expert.software.development" role="button">
                    <i class="fab fa-facebook-f"></i></a>
                <!-- VK -->
                <a class="btn btn-outline-light btn-floating m-1" href="https://vk.com/expert_soft" role="button">
                    <i class="fab fa-vk"></i></a>
                <!-- Expert soft -->
                <a class="btn btn-outline-light btn-floating m-1" href="https://expert-soft.by/" role="button">
                    <i class="fab fa-chrome"></i></a>
                <!-- Instagram -->
                <a class="btn btn-outline-light btn-floating m-1" href="https://www.instagram.com/expertsoftcom/"
                   role="button">
                    <i class="fab fa-instagram"></i></a>
                <!-- Linkedin -->
                <a class="btn btn-outline-light btn-floating m-1"
                   href="https://www.linkedin.com/company/expert-software-development/mycompany/" role="button">
                    <i class="fab fa-linkedin-in"></i></a>
                <!-- Github -->
                <a class="btn btn-outline-light btn-floating m-1" href="https://github.com/MRw-Overlord/phoneshop"
                   role="button">
                    <i class="fab fa-github"></i></a>
            </section>
            <!-- Section: Social media -->

        </div>
        <!-- Grid container -->

        <!-- Copyright -->
        <div class="text-center p-3" style="background-color: rgba(0, 0, 0, 0.2);">&#169; 2021 Copyright:
            <a class="text-white" href="https://expert-soft.by/">Expert Software Development</a>
        </div>
        <!-- Copyright -->
    </footer>
</div>
</body>
</html>