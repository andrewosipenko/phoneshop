<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags/components" %>

<html>
<head>
    <title>Product List</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script> <%@ include file="scripts/productList.js" %> </script>
</head>
<body>
    <components:header/>

    <form method="get" id="sortByForm" hidden>
        <c:if test="${not empty param.search}">
            <input type="hidden" name="search" value="${param.search}"/>
        </c:if>
        <input type="hidden" name="sortBy" id="sortByInput"/>
    </form>

    <div class="container mt-3 py-1">
        <div class="d-inline-block">
            <h3>Phones</h3>
        </div>
        <div class="d-inline-block float-right">
            <form class="form-inline my-2 my-lg-0" method="get" id="searchForm">
                <c:if test="${not empty param.sortBy}">
                    <input type="hidden" name="sortBy" value="${param.sortBy}"/>
                </c:if>
                <input class="form-control mr-sm-2" type="search" placeholder="Search..." aria-label="Search" name="search" value="${param.search}"/>
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>
        </div>
    </div>

    <div class="container">
        <table class="table table-bordered table-striped">
            <thead style="background-color: #828082;">
            <tr class="d-table-row text-light text-center">
                <th scope="col">Image</th>
                <th scope="col"><a style="display: block; cursor: pointer;" onclick="sortBy('brand')">Brand <components:tableColumnArrow test="brand"/></a></th>
                <th scope="col"><a style="display: block; cursor: pointer;" onclick="sortBy('model')">Model <components:tableColumnArrow test="model"/></a></th>
                <th scope="col">Colors</th>
                <th scope="col"><a style="display: block; cursor: pointer;" onclick="sortBy('display_size')">Display size <components:tableColumnArrow test="display_size"/></a></th>
                <th scope="col"><a style="display: block; cursor: pointer;" onclick="sortBy('price')">Price <components:tableColumnArrow test="price"/></a></th>
                <th scope="col">Quantity</th>
                <th scope="col">Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${phones}" var="phone">
                <tr>
                    <td class="p-0 m-0" style="width:1px;"><img width="100px" height="100px" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}"></td>
                    <td style="vertical-align: middle!important"><c:out value="${phone.brand}"/></td>
                    <td style="vertical-align: middle!important"><c:out value="${phone.model}"/></td>
                    <td style="vertical-align: middle!important"><c:forEach items="${phone.colors}" var="color" varStatus="loop"><c:out value="${color.code}"/><c:if test="${not loop.last}">, </c:if></c:forEach></td>
                    <td style="vertical-align: middle!important"><c:out value="${phone.displaySizeInches}''"/></td>
                    <td style="vertical-align: middle!important">$<c:out value="${phone.price}"/></td>
                    <td style="vertical-align: middle!important">
                        <input type="text" class="form-control" id="phone${phone.id}Quantity" value="0" style="width:70px;"/>
                    </td>
                    <td class="text-center" style="vertical-align: middle!important"><button class="btn btn-info">Add to cart</button></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <components:pagination currentPage="${currentPage}" pagesTotal="${pagesTotal}"/>
</body>
</html>
