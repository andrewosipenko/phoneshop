<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>ProductList</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body>
<p>
    Hello from product list!
</p>
Found
<c:out value="${phones.size()}"/> phones.
<form>
    <input name="query" value="${param.query}">
    <button>Search</button>
</form>
<table class="table table-bordered">
    <thead>
    <tr>
        <td>Image</td>
        <td>
            Brand
            <tags:sortLink sort="brand" order="asc">
                <img width="10px" height="17px"
                     src="${pageContext.servletContext.contextPath}/images/long-arrow-alt-up-solid.svg">
            </tags:sortLink>
            <tags:sortLink sort="brand" order="desc">
                <img width="10px" height="17px"
                     src="${pageContext.servletContext.contextPath}/images/long-arrow-alt-down-solid.svg">
            </tags:sortLink>
        </td>
        <td>
            Model
            <tags:sortLink sort="model" order="asc">
                <img width="10px" height="17px"
                     src="${pageContext.servletContext.contextPath}/images/long-arrow-alt-up-solid.svg">
            </tags:sortLink>
            <tags:sortLink sort="model" order="desc">
                <img width="10px" height="17px"
                     src="${pageContext.servletContext.contextPath}/images/long-arrow-alt-down-solid.svg">
            </tags:sortLink>
        </td>
        <td>Color</td>
        <td>
            Display size
            <tags:sortLink sort="display" order="asc">
                <img width="10px" height="17px"
                     src="${pageContext.servletContext.contextPath}/images/long-arrow-alt-up-solid.svg">
            </tags:sortLink>
            <tags:sortLink sort="display" order="desc">
                <img width="10px" height="17px"
                     src="${pageContext.servletContext.contextPath}/images/long-arrow-alt-down-solid.svg">
            </tags:sortLink>
        </td>
        <td>
            Price
            <tags:sortLink sort="price" order="asc">
                <img width="10px" height="17px"
                     src="${pageContext.servletContext.contextPath}/images/long-arrow-alt-up-solid.svg">
            </tags:sortLink>
            <tags:sortLink sort="price" order="desc">
                <img width="10px" height="17px"
                     src="${pageContext.servletContext.contextPath}/images/long-arrow-alt-down-solid.svg">
            </tags:sortLink>
        </td>
        <td>Quantity</td>
        <td>Action</td>
    </tr>
    </thead>
    <c:forEach var="phone" items="${phones}">
        <tr>
            <td>
                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
            </td>
            <td>${phone.brand}</td>
            <td>${phone.model}</td>
            <td>
                <c:forEach var="color" items="${phone.colors}">
                    ${color.code},
                </c:forEach>
            </td>
            <td>${phone.displaySizeInches}"</td>
            <td>${phone.price}$</td>
            <td>
                <input name="quantity"/>
            </td>
            <td>
                <button>
                    Add to cart
                </button>
            </td>
        </tr>
    </c:forEach>
</table>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
</body>
</html>