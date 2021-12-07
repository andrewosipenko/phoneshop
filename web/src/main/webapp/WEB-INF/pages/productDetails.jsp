<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>
    <script>
        <%@ include file="js/addToCart.js" %>
    </script>
    <title>
        <spring:message code="titlePage.productDetails"/>
    </title>
</head>
<body>
<div class="container">
    <tags:header cart="${cart}"/>
    <tags:backToProductListButton/>
    <div class="row">
        <div class="col col-md-4">
            <h3>${phone.brand} ${phone.model}</h3>
        </div>
    </div>
    <div class="row">
        <div class="col col-md-6">
            <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}"
                 class="img-fluid" alt="Phone img">
        </div>
        <div class="col col-md-5">
            <h3>
                <spring:message code="titleTable.display"/>
            </h3>
            <tags:displayInfoTable phone="${phone}"/>
        </div>
    </div>
    <div class="row">
        <div class="col col-md-5">
            ${phone.description}
        </div>
        <div class="col col-md-1"></div>
        <div class="col col-md-5">
            <h3>
                <spring:message code="titleTable.dimensionAndWeight"/>
            </h3>
            <tags:dimensionsWeightInfoTable phone="${phone}"/>
        </div>
    </div>
    <div class="row">
        <div class="col col-md-3 border-1">
            <h4>Price: ${phone.price}$</h4>
            <br>
            <div class="input-group">
                <button class="btn btn-outline-secondary" id="search-button" onclick="addToCart(${phoneId})">
                    <spring:message code="button.addToCart"/>
                </button>
                <input id="phoneQuantity" name="quantity" type="text" class="form-control" value="1"
                       aria-describedby="search-button">
            </div>
            <div class="row">
                <div class="col-md-4"></div>
                <div class="col-md-8">
                    <div style="color: green" id="success-message"></div>
                    <div style="color: red" id="error-message"></div>
                </div>
            </div>
        </div>
        <div class="col col-md-3"></div>
        <div class="col col-md-5">
            <h3>
                <spring:message code="titleTable.camera"/>
            </h3>
            <tags:cameraInfoTable phone="${phone}"/>
        </div>
    </div>

    <div class="row">
        <div class="col col-md-6"></div>
        <div class="col col-md-5">
            <h3>
                <spring:message code="titleTable.battery"/>
            </h3>
            <tags:batteryInfoTable phone="${phone}"/>
        </div>
    </div>
    <div class="row">
        <div class="col col-md-6"></div>
        <div class="col col-md-5">
            <h3>
                <spring:message code="titleTable.others"/>
            </h3>
            <tags:otherInfoTable phone="${phone}"/>
        </div>
    </div>
</div>
<br>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
</body>
</html>