<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/templates" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
                    <form:form method="post" modelAttribute="cartUpdateInfo">
                        <input type="hidden" name="_method" value="PUT"/>
                        <input type="hidden" name="phoneId"/>
                        <c:forEach var="cartItem" items="${cartUpdateInfo.cartItems}" varStatus="i">
                            <tr>
                                <td class="col-sm-8 col-md-6">
                                    <div class="media">
                                        <a class="thumbnail pull-left"
                                           href="<c:url value="/productDetails/${cartItem.phoneId}"/>">
                                            <img class="media-object"
                                                 src="<c:url value="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${cartItem.imageUrl}"/>"
                                                 style="width: 72px; height: 72px;"> </a>
                                        <div class="media-body pl-2">
                                            <h5 class="media-heading"><a
                                                    href="<c:url value="/productDetails/${cartItem.phoneId}"/>"><c:out value="${cartItem.model}"/></a>
                                            </h5>
                                            <h6 class="media-heading"> by <c:out value="${cartItem.brand}"/></h6>
                                        </div>
                                    </div>
                                </td>
                                <td class="col-sm-1 col-md-1" style="text-align: center">
                                    <form:hidden path="cartItems[${i.index}].phoneId"/>
                                    <form:input path="cartItems[${i.index}].quantity" class="form-control"/>
                                    <div class="error-message" id="error-message">
                                        <form:errors path="cartItems[${i.index}].quantity"/>
                                    </div>
                                </td>
                                <td class="col-sm-1 col-md-1 text-center"><strong>$<c:out value="${cartItem.price}"/></strong></td>
                                <td class="col-sm-1 col-md-1 text-center"><strong>$<c:out value="${cartItem.total}"/></strong></td>
                                <td class="col-sm-1 col-md-1">
                                    <button type="submit" name="remove" class="btn btn-danger"
                                            onclick="return onDeletePhone(<c:out value="${cartItem.phoneId}"/>);">
                                        Remove
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td>  </td>
                            <td>  </td>
                            <td><h3>Total</h3></td>
                            <td class="text-right"><h3><strong>$<c:out value="${not empty cart ? cart.cost : 0}"/></strong></h3></td>
                            <td>  </td>
                        </tr>
                        <tr>
                            <td>  </td>
                            <td>  </td>
                            <td>  </td>
                            <td>
                                <button name="update" type="submit" class="btn btn-primary">
                                    Update
                                </button>
                            </td>
                            <td>
                                <c:if test="${not empty cartUpdateInfo.cartItems}">
                                    <button type="submit" class="btn btn-success">
                                        Order
                                    </button>
                                </c:if>
                            </td>
                        </tr>
                        <script>
                            function onDeletePhone(id) {
                                $('input[name=_method]').remove();
                                $('input[name=phoneId]').val(id);
                                return true;
                            }
                        </script>
                    </form:form>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template:page>