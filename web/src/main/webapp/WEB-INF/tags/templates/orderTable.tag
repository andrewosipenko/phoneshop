<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@ attribute name="order" type="com.es.core.model.order.Order" required="true" %>
<table class="table table-hover">
    <thead>
    <tr>
        <th>Product</th>
        <th>Quantity</th>
        <th class="text-center">Price</th>
        <th class="text-center">Total</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="orderItem" items="${order.orderItems}" varStatus="i">
        <tr>
            <td class="col-sm-8 col-md-6">
                <div class="media">
                    <a class="thumbnail pull-left"
                       href="<c:url value="/productDetails/${orderItem.phone.id}"/>">
                        <img class="media-object"
                             src="<c:url value="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${orderItem.phone.imageUrl}"/>"
                             style="width: 72px; height: 72px;"> </a>
                    <div class="media-body pl-2">
                        <h5 class="media-heading"><a
                                href="<c:url value="/productDetails/${orderItem.phone.id}"/>"><c:out
                                value="${orderItem.phone.model}"/></a>
                        </h5>
                        <h6 class="media-heading"> by <c:out value="${orderItem.phone.brand}"/></h6>
                    </div>
                </div>
            </td>
            <td class="col-sm-1 col-md-1" style="text-align: center">
                <c:out value="${orderItem.quantity}"/>
            </td>
            <td class="col-sm-1 col-md-1 text-center"><strong>$<c:out
                    value="${not empty orderItem.phone.price ? orderItem.phone.price : '0'}"/></strong></td>
            <td class="col-sm-1 col-md-1 text-center"><strong>$<c:out
                    value="${orderItem.total}"/></strong></td>
        </tr>
    </c:forEach>
    <tr>
        <td class="text-right"><h3>Subtotal</h3></td>
        <td></td>
        <td></td>
        <td class="text-right"><h3><strong>$<c:out
                value="${order.subtotal}"/></strong></h3></td>
    </tr>
    <tr>
        <td class="text-right"><h3>Delivery price</h3></td>
        <td></td>
        <td></td>
        <td class="text-right"><h3><strong>$<c:out
                value="${order.deliveryPrice}"/></strong></h3></td>
    </tr>
    <tr>
        <td class="text-right"><h3>Total price</h3></td>
        <td></td>
        <td></td>
        <td class="text-right"><h3><strong>$<c:out
                value="${order.totalPrice}"/></strong></h3></td>
    </tr>
    </tbody>
</table>
