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
        <spring:message code="titlePage.orderOverview"/>
    </title>
</head>
<body>
<div class="container">
    <tags:headerLogin cart="${cart}" isCartAvailable="false"/>
    <h1>
        <spring:message code="titlePage.orderOverview"/>
    </h1>
    <tags:backToProductListButton/>
    <c:choose>
        <c:when test="${empty order}">
            <h1>
                <spring:message code="error.orderCreation"/>
            </h1>
        </c:when>
        <c:otherwise>
            <div class="row">
                <div class="col border col-md-2">
                    <h5>
                        <spring:message code="titleTable.brand"/>
                    </h5>
                </div>
                <div class="col border col-md-2 bg-light">
                    <h5>
                        <spring:message code="titleTable.model"/>
                    </h5>
                </div>
                <div class="col border col-md-2">
                    <h5>
                        <spring:message code="titleTable.color"/>
                    </h5>
                </div>
                <div class="col border col-md-2 bg-light">
                    <h5>
                        <spring:message code="titleTable.displaySize"/>
                    </h5>
                </div>
                <div class="col border col-md-2">
                    <h5>
                        <spring:message code="titleTable.quantity"/>
                    </h5>
                </div>
                <div class="col border col-md-2 bg-light">
                    <h5>
                        <spring:message code="titleTable.price"/>
                    </h5>
                </div>
            </div>
            <c:forEach var="orderItem" items="${order.orderItems}">
                <div class="row">
                    <div id="brand" class="col border col-md-2">
                        <text>${orderItem.phone.brand}</text>
                    </div>

                    <div id="model" class="col border bg-light col-md-2">
                        <a href="${pageContext.servletContext.contextPath}/productDetails/${orderItem.phone.id}">
                                ${orderItem.phone.model}
                        </a>
                    </div>
                    <div id="color" class="col border col-md-2">${orderItem.phone.colors}</div>
                    <div id="displaySize"
                         class="col border bg-light col-md-2">${orderItem.phone.displaySizeInches}"
                    </div>
                    <div id="price" class="col border col-md-2">${orderItem.quantity}</div>
                    <div id="quantity" class="col border bg-light col-md-2">${orderItem.phone.price}$</div>
                </div>
            </c:forEach>
            <div class="row">
                <div class="col col-md-8"></div>
                <div class="col-md-2 border">
                    <spring:message code="order.info.subtotal"/>
                </div>
                <div class="col-md-2 border bg-light">${order.subtotal}$</div>
            </div>
            <div class="row">
                <div class="col col-md-8"></div>
                <div class="col-md-2 border">
                    <spring:message code="order.info.deliveryPrice"/>
                </div>
                <div class="col-md-2 border bg-light">${order.deliveryPrice}$</div>
            </div>
            <div class="row">
                <div class="col col-md-8"></div>
                <div class="col-md-2 border">
                    <spring:message code="order.info.total"/>
                </div>
                <div class="col-md-2 border bg-light">${order.totalPrice}$</div>
            </div>
            <br>
            <div class="row">
                <div class="col col-md-1">
                    <spring:message code="orderOverview.contactInfo.firstName"/>
                </div>
                <div class="col col-md-2">
                        ${order.firstName}
                </div>
            </div>
            <br>
            <div class="row">
                <div class="col col-md-1">
                    <spring:message code="orderOverview.contactInfo.lastName"/>
                </div>
                <div class="col col-md-2">
                        ${order.lastName}
                </div>
            </div>
            <br>
            <div class="row">
                <div class="col col-md-1">
                    <spring:message code="orderOverview.contactInfo.address"/>
                </div>
                <div class="col col-md-2">
                        ${order.deliveryAddress}
                </div>
            </div>
            <br>
            <div class="row">
                <div class="col col-md-1">
                    <spring:message code="orderOverview.contactInfo.phone"/>
                </div>
                <div class="col col-md-2">
                        ${order.contactPhoneNo}
                </div>
            </div>
            <br>
            <div class="row">
                <div class="col col-md-1">
                    <spring:message code="orderOverview.contactInfo.additionalInfo"/>
                </div>
                <div class="col col-md-3">
                        ${order.additionalInfo}
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
</body>
</html>