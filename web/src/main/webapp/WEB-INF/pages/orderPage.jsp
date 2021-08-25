<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<tags:page pageTitle="Order page">
    <h3 style="margin: 10px">Order</h3>
    <div class="float-left">
        <a href="${pageContext.request.contextPath}/cart" style="margin-left: 5px" class="btn btn-primary btn-lg"
           role="button">
            <h5>Back to cart</h5>
        </a>
    </div>

    <c:if test="${outOfStock}">
        <p><em>Some items from order have ended. They have been removed from your cart</em></p>
    </c:if>
    <c:set var="carItems" value="${cart.cartItems}"/>
    <c:choose>
        <c:when test="${carItems == null || carItems.size() <= 0}">
            <p><em>No items in order</em></p>
        </c:when>
        <c:otherwise>
            <table class="table table-striped table-bordered table-sm" style="text-align: left">
                <thead>
                <tr>
                    <td style="width: 20%">Brand</td>
                    <td style="width: 13%">Model</td>
                    <td style="width: 14%">Color</td>
                    <td style="width: 10%">Display size</td>
                    <td style="width: 10%">Quantity</td>
                    <td style="width: 10%">Price</td>
                </tr>
                </thead>
                <c:forEach var="cartItem" items="${carItems}">
                    <c:set var="phone" value="${cartItem.phone}"/>
                    <c:set var="phoneId" value="${phone.id}"/>
                    <tr>
                        <td>${phone.brand}</td>
                        <td>${phone.model}</td>
                        <td>
                            <c:forEach var="color" items="${phone.colors}" varStatus="status">
                                <c:out value="${color.code}"/>
                                <c:if test="${not status.last}">, </c:if>
                            </c:forEach>
                        </td>
                        <td>${phone.displaySizeInches}</td>
                        <td>${cartItem.quantity}</td>
                        <td>${phone.price}</td>
                    </tr>
                </c:forEach>
            </table>
            <div class="float-right">
                <table class="table table-striped table-bordered table-sm" style="text-align: left">
                    <tr>
                        <th scope="row">Subtotal</th>
                        <td>${cart.subtotalPrice}$</td>
                    </tr>
                    <tr>
                        <th scope="row">Delivery</th>
                        <td>${cart.deliveryPrice}$</td>
                    </tr>
                    <tr>
                        <th scope="row" style="background-color: #cac123">Total</th>
                        <td>${cart.totalPrice}$</td>
                    </tr>
                </table>
            </div>
            <form:form method="post" action="${pageContext.request.contextPath}/order" modelAttribute="orderForm"
                       cssClass="margin: 15px">
                <br><br>
                <div class="form-group">
                    <label for="firstName">First name</label>
                    <form:input path="firstName" type="text" class="form-control" cssClass="width: 400px" id="firstName"
                                placeholder="First name"/>
                    <form:errors path="firstName" cssClass="error" cssStyle="color:red">
                        <p class="note note-danger">
                            <strong>Error:</strong>
                            <form:errors path="firstName"/>
                        </p>
                    </form:errors>
                </div>
                <div class="form-group">
                    <label for="lastName">Last name</label>
                    <form:input path="lastName" type="text" class="form-control" cssClass="width: 400px" id="lastName"
                                placeholder="Last name"/>
                    <form:errors path="lastName" cssClass="error" cssStyle="color:red">
                        <p class="note note-danger">
                            <strong>Error:</strong>
                            <form:errors path="lastName"/>
                        </p>
                    </form:errors>
                </div>
                <div class="form-group">
                    <label for="address">Address</label>
                    <form:input path="address" type="text" class="form-control" cssClass="width: 400px" id="address"
                                placeholder="Your address"/>
                    <form:errors path="address" cssClass="error" cssStyle="color:red">
                        <p class="note note-danger">
                            <strong>Error:</strong>
                            <form:errors path="address"/>
                        </p>
                    </form:errors>
                </div>
                <div class="form-group">
                    <label for="phone">Phone</label>
                    <form:input path="phone" type="text" class="form-control" cssClass="width: 400px" id="phone"
                                placeholder="Your phone"/>
                    <form:errors path="phone" cssClass="error" cssStyle="color:red">
                        <p class="note note-danger">
                            <strong>Error:</strong>
                            <form:errors path="phone"/>
                        </p>
                    </form:errors>
                </div>
                <div class="form-group">
                    <label for="additionalInfo">Additional info</label>
                    <form:textarea path="additionalInfo" class="form-control" cssStyle="width: 400px"
                                   id="additionalInfo"
                                   rows="3"/>
                    <form:errors path="additionalInfo" cssClass="error" cssStyle="color:red">
                        <p class="note note-danger">
                            <strong>Error:</strong>
                            <form:errors path="additionalInfo"/>
                        </p>
                    </form:errors>
                </div>
                <button class="btn btn-secondary" type="submit">Order</button>
            </form:form>
        </c:otherwise>
    </c:choose>
</tags:page>
