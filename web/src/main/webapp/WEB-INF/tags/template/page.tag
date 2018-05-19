<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en" >
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.4.3/css/mdb.min.css">

    <script src="https://code.jquery.com/jquery-3.2.1.min.js" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.4.3/js/mdb.min.js"></script>
</head>
<body>
<security:csrfMetaTags />
<div class="container">
    <div class="row">
        <div class="col-16 col-md-12 ">
            <nav class="navbar navbar-toggleable-md navbar-inverse bg-primary">
                <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <a class="navbar-brand mr-5" href="<c:url value="/productList"/>">Phonify</a>
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav ml-auto">
                            <li class="nav-item">
                                <form action="<c:url value="/cart"/>">
                                <button type="submit" class ="btn btn-success">
                                   My cart: <a id="itemsAmount">${empty sessionScope.get("scopedTarget.cart") ? '0' : sessionScope.get("scopedTarget.cart").itemsAmount}</a> items,
                                   price: <a id="subtotal">${empty sessionScope.get("scopedTarget.cart").subtotal ? '0' : sessionScope.get("scopedTarget.cart").subtotal}</a> $
                                </button>
                                </form>
                            </li>
                        </ul>
                    </div>
                    <div>
                        <security:authorize url="/admin/**">
                            <security:authentication property="principal.username"/>
                            <a class ="btn btn-success" href="<c:url value="/admin/orders"/>">Admin</a>
                            <a class ="btn btn-success" href="<c:url value="/logout"/>">Log out</a>
                        </security:authorize>
                        <security:authorize access="not isAuthenticated()">
                        <a class ="btn btn-success" href="<c:url value="/login"/>">Log in</a>
                        </security:authorize>
                    </div>
            </nav>
            <jsp:doBody/>
        </div>
    </div>
</div>
</body>
</html>