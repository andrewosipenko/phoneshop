<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/templates" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<template:page>
    <div class="container">
        <div class="row">
            <div class="col-sm-12 col-md-10 col-md-offset-1">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>Product</th>
                            <th>Quantity</th>
                            <th class="text-center">Price</th>
                            <th class="text-center">Total</th>
                            <th> </th>
                        </tr>
                    </thead>
                    <tbody>
                        <%--<form method="post">
                            <input type="hidden" name="_method" value="PUT"/>--%>
                            <c:forEach var="cartItem" items="${cartItemList}">
                                <tr>
                                    <td class="col-sm-8 col-md-6">
                                        <div class="media">
                                            <a class="thumbnail pull-left" href="${pageContext.request.contextPath}/productDetails/${cartItem.phone.id}"> <img class="media-object" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${cartItem.phone.imageUrl}" style="width: 72px; height: 72px;"> </a>
                                            <div class="media-body pl-2">
                                                <h5 class="media-heading"><a href="${pageContext.request.contextPath}/productDetails/${cartItem.phone.id}">${cartItem.phone.model}</a></h5>
                                                <h6 class="media-heading"> by ${cartItem.phone.brand}</h6>
                                            </div>
                                        </div>
                                    </td>
                                    <td class="col-sm-1 col-md-1" style="text-align: center">
                                        <input type="text" class="form-control" id="quantity" value="${cartItem.quantity}">
                                        <div class="error-message" id="error-message"></div>
                                    </td>
                                    <td class="col-sm-1 col-md-1 text-center"><strong>$${cartItem.phone.price}</strong></td>
                                    <td class="col-sm-1 col-md-1 text-center"><strong>$${cartItem.total}</strong></td>
                                    <td class="col-sm-1 col-md-1">
                                        <form method="post">
                                            <input type="hidden" name="phoneId" value="${cartItem.phone.id}">
                                            <button type="submit" name="remove" class="btn btn-danger">
                                                Remove
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td>   </td>
                                <td>   </td>
                                <td><h3>Total</h3></td>
                                <td class="text-right"><h3><strong>$${not empty cartCost ? cartCost : 0}</strong></h3></td>
                                <td>   </td>
                            </tr>
                            <tr>
                                <td>   </td>
                                <td>   </td>
                                <td>   </td>
                                <td>
                                    <button name="update" type="submit" class="btn btn-primary">
                                        Update
                                    </button>
                                </td>
                                <td>
                                    <c:if test="${not empty cartItemList}">
                                        <button type="submit" class="btn btn-success">
                                            Order
                                        </button>
                                    </c:if>
                                </td>
                            </tr>
                            <%-- </form>--%>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template:page>