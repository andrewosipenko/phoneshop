<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<tags:master pageTitle="Product list">

    <nav class="navbar navbar-light bg-light">
        <a class="navbar-brand">Phones</a>
        <form class="form-inline">
            <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form>
    </nav>

    <table class="table">
        <thead>
        <tr>
            <th scope="col">Image</th>
            <th scope="col">Brand</th>
            <th scope="col">Model</th>
            <th scope="col">Color</th>
            <th scope="col">Display size</th>
            <th scope="col">Price</th>
            <th scope="col">Quantity</th>
            <th scope="col">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="phone" items="${phones}" varStatus="statusPhones">
            <tr>
                <th scope="row">
                    <img class="product-tile"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                </th>
                <td>${phone.brand}</td>
                <td>${phone.model}</td>
                <td>
                    <c:forEach var="color" items="${phone.colors}" varStatus="statusColors">
                        <c:out value="${color.code}"/>
                        <c:if test="${not statusColors.last}">
                            <c:out value=","/>
                        </c:if>
                    </c:forEach>
                </td>
                <td>${phone.displaySizeInches}</td>
                <td class="price">${phone.price}</td>
                <td style="max-width: 120px">
                    <input id="quantity-${phone.id}"
                           class="quantityInput quantity"
                           type="text"
                           name="quantity"
                           value="1"/>
                    <div id="quantityInputMessage-${phone.id}">
                    </div>
                </td>
                <td>
                    <button id="btn-addPhoneToCart-${phone.id}" onclick="addPhoneToCart(${phone.id})"
                            class="btn btn-light">
                        Add to cart
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</tags:master>