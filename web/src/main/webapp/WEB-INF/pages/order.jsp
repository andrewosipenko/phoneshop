<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!doctype html>
<html>
<head>
    <link rel="stylesheet" href="webjars/bootstrap/4.1.3/css/bootstrap.min.css"/>
    <script src="webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="webjars/bootstrap/4.1.3/js/bootstrap.min.js"></script>
    <title>Product list</title>
</head>
<body>
<p>
    Hello from Order!
</p>
<form action="<c:url value="cart"/>">
    <button>Back to Cart</button>
</form>
<p>

    <div class="table-responsive">
        <table id="table" class="table table-striped table-bordered table-hover table-sm" cellspacing="0"
               width="100%">
            <thead>
            <tr>
                <th>Brand</th>
                <th>Model</th>
                <th>Color</th>
                <th>Display size</th>
                <th>Price</th>
                <th>Quantity</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="orderItem" items="${order.orderItems}" varStatus="status">
                <tr>
                    <td>${orderItem.phone.brand}</td>
                    <td>
                            ${orderItem.phone.model}
                        <br>
                        <a href="productDetails?phoneId=${orderItem.phone.id}">More...</a>
                    </td>
                    <td>
                        <c:forEach var="color" items="${orderItem.phone.colors}">
                            ${color}
                        </c:forEach>
                    </td>
                    <td>${orderItem.phone.displaySizeInches}"</td>
                    <td>${orderItem.quantity}</td>
                    <td>$ ${orderItem.phone.price.multiply(orderItem.quantity)}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
<div class="row">
    <div class="col-md-8"></div>
    <div class="col-md-3">
        <table class="table table-bordered table-hover" cellspacing="0">
            <tbody>
            <tr>
                <td>Subtotal price:</td>
                <td>${order.subtotal}</td>
            </tr>
            <tr>
                <td>Delivery price:</td>
                <td>${order.deliveryPrice}</td>
            </tr>
            <tr>
                <td>TOTAL:</td>
                <td>${order.totalPrice}</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<form:form method="post" modelAttribute="customer">
    <table>
        <tr>
            <td><form:label path="firstName">First name*</form:label></td>
            <td><form:input path="firstName"/></td>
        </tr>
        <tr>
            <td><form:label path="lastName">Second name*</form:label></td>
            <td><form:input path="lastName"/></td>
        </tr>
        <tr>
            <td><form:label path="address">Address*</form:label></td>
            <td><form:input path="address"/></td>
        </tr>
        <tr>
            <td><form:label path="contactNumber">
                Contact Number*</form:label></td>
            <td><form:input path="contactNumber"/></td>
        </tr>
        <tr>
            <td><form:label path="additionalInformation">
                Additional information</form:label></td>
            <td><form:textarea path="additionalInformation"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>
    </table>
</form:form>
</p>
</body>
</html>