<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Try</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-12 login">
                    <a href="#">Login</a>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <a href="${pageContext.request.contextPath}/productList"><img src="<c:url value="/resources/img/logo.jpg"/>"></a>
                </div>
                <div class="col">
                    <button class="float-right">My cart: 0 items 0$</button>
                </div>
            </div>
            <div class="row">
                <div class="col">
                     <form class="navbar-form navbar-right" method="GET" action="${pageContext.request.contextPath}/productList">
                         <div class="input-group">
                             <input type="text" class="form-control" placeholder="Search" name="model">
                             <div class="input-group-btn">
                                 <button class="btn btn-default" type="submit">
                                     <i class="glyphicon glyphicon-search"></i>
                                 </button>
                             </div>
                         </div>
                     </form>
                </div>
            </div>
            <div class="row">
                <table border="1px">
                    <thead>
                        <tr>
                            <td>Image</td>
                            <td><a class="sort-link" href="${pageContext.request.contextPath}/productList?model=${param.model}&order=brand ${brandOrder}, phoneId ${brandOrder}">Brand <i class="glyphicon glyphicon-sort"></i></a></td>
                            <td><a class="sort-link" href="${pageContext.request.contextPath}/productList?model=${param.model}&order=model ${modelOrder}, phoneId ${modelOrder}">Model <i class="glyphicon glyphicon-sort"></i></a></td>
                            <td><%--<a class="sort-link" href="${pageContext.request.contextPath}/productList?model=${param.model}&order=color ${colorOrder}, phoneId ${colorOrder}">--%>Color <i class="glyphicon glyphicon-sort"></i><%--</a>--%></td>
                            <td><a class="sort-link" href="${pageContext.request.contextPath}/productList?model=${param.model}&order=displaySizeInches ${displaySizeInchesOrder}, phoneId ${displaySizeInchesOrder}">Display size <i class="glyphicon glyphicon-sort"></i></a></td>
                            <td><a class="sort-link" href="${pageContext.request.contextPath}/productList?model=${param.model}&order=price ${priceOrder}, phoneId ${priceOrder}">Price <i class="glyphicon glyphicon-sort"></i></a></td>
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
                                    ${color.code}
                                </c:forEach>
                            </td>
                            <td>${phone.displaySizeInches}''</td>
                            <td>$ ${phone.price}</td>
                            <td>
                                <input id="quantity${phone.id}" type="text" value="1">
                            </td>
                            <td>
                                <button type="button" class="btn btn-default add-cart" onclick="doAjax${phone.id}()">Add to cart</button>
                            </td>
                        </tr>
                        <script type="text/javascript">
                            function doAjax${phone.id}() {
                                var quantity = $("#quantity${phone.id}").val();

                                $.ajax({
                                    url: '${pageContext.request.contextPath}/ajaxCart',
                                    type: 'POST',
                                    mimeType: 'application/json',
                                    data: ({
                                        phoneId : ${phone.id},
                                        quantity : quantity
                                    })
                                });
                            }
                        </script>
                    </c:forEach>
                </table>
            </div>
            <div class="row">
                <div class="col">
                    <ul class="pagination float-right">
                        <c:forEach var = "i" begin = "1" end = "${pageCount}">
                            <li><a href="${requestScope['javax.servlet.forward.request_uri']}?model=${param.model}&order=${param.order}&page=${i}">${i}</a></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </body>
</html>