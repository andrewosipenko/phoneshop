<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>
    <title>
        <spring:message code="titlePage.admin"/>
    </title>
</head>
<body>
<div class="container">
    <tags:headerLogin isCartAvailable="false"/>
    <h1>
        <spring:message code="titleTable.admin"/>
    </h1>
    <c:choose>
        <c:when test="${empty orders}">
            <h1>
                <spring:message code="error.noOrder"/>
            </h1>
        </c:when>
        <c:otherwise>
            <%--Info table--%>
            <div class="row">
                <div class="col border col-md-1">
                    <h5>
                        <spring:message code="titleTable.admin.orderNumber"/>
                    </h5>
                </div>
                <div class="col border col-md-2 bg-light">
                    <h5>
                        <spring:message code="titleTable.admin.Customer"/>
                    </h5>
                </div>
                <div class="col border col-md-2">
                    <h5>
                        <spring:message code="titleTable.admin.phone"/>
                    </h5>
                </div>
                <div class="col border col-md-2 bg-light">
                    <h5>
                        <spring:message code="titleTable.admin.address"/>
                    </h5>
                </div>
                <div class="col border col-md-2">
                    <h5>
                        <spring:message code="titleTable.admin.date"/>
                    </h5>
                </div>
                <div class="col border col-md-1 bg-light">
                    <h5>
                        <spring:message code="titleTable.admin.totalPrice"/>
                    </h5>
                </div>
                <div class="col border col-md-2">
                    <h5>
                        <spring:message code="titleTable.admin.status"/>
                    </h5>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
    <c:forEach var="order" items="${orders}">
        <div class="row">
            <div id="order_number" class="col border col-md-1">
                <a href="${pageContext.servletContext.contextPath}/admin/orders/${order.secureId}">${order.id}</a>
            </div>

            <div id="model" class="col border bg-light col-md-2">
                    ${order.firstName} ${order.lastName}
            </div>
            <div id="color" class="col border col-md-2">${order.contactPhoneNo}</div>
            <div id="displaySize" class="col border bg-light col-md-2">${order.deliveryAddress}"</div>
            <div id="price" class="col border col-md-2">${order.date}</div>
            <div id="quantity_block" class="col border bg-light col-md-1">${order.totalPrice}$</div>
            <div id="action" class="col border col-md-2">${order.status}</div>
        </div>
    </c:forEach>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
</body>
</html>