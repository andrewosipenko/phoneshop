<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/templates" %>
<template:page catalogTabIsActive="${true}">
    <p>
        Found <c:out value="${productPage.count}"/> phones.
    </p>
    <c:if test="${productPage.count > 0}">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Image</th>
                <th><template:sortLink nameLink="Brand" orderOfLink="BRAND"/></th>
                <th><template:sortLink nameLink="Model" orderOfLink="MODEL"/></th>
                <th>Color</th>
                <th><template:sortLink nameLink="Display size" orderOfLink="DISPLAY_SIZE"/></th>
                <th><template:sortLink nameLink="Price" orderOfLink="PRICE"/></th>
                <th>Quantity</th>
                <th>Action</th>
            </tr>
            </thead>
            <c:forEach var="phone" items="${productPage.phoneList}">
                <tr>
                    <td>
                        <a href="${pageContext.request.contextPath}/productDetails/${phone.id}">
                            <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                        </a>
                    </td>
                    <td>${phone.brand}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/productDetails/${phone.id}">
                                ${phone.model}
                        </a>
                    </td>
                    <td>
                        <c:forEach var="color" items="${phone.colors}">
                            <p>${color.code}</p>
                        </c:forEach>
                    </td>
                    <td>${phone.displaySizeInches}</td>
                    <td>$${phone.price}</td>
                    <td>
                        <input type="text" class="phone-quantity" value="1" size="10"/>
                        <input type="hidden" class="phone-id" value="${phone.id}"/>
                        <div class="error-message"></div>
                    </td>
                    <td>
                        <button type="button" class="btn btn-secondary add-to-cart">Add to cart</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <template:pagination order="${order}" pagination="${productPage.pagination}" query="${query}"/>
    </c:if>
</template:page>