<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:page pageTitle="Product List Page">

    <script type="text/javascript">
        <%@ include file="/static/js/addToCart.js"%>
    </script>

    <c:choose>
        <c:when test="${productPage.phoneList == null || productPage.phoneList.size() <= 0}">
            <p>
                <em>Nothing found</em>
            </p>
        </c:when>
        <c:otherwise>
            <table class=" table table-striped table-bordered table-hover table-sm" style="text-align: center">
                <caption>Product List</caption>
                <thead class="thead-dark">
                <tr>
                    <td style="width: 20%">Image</td>
                    <td style="width: 13%"><tags:sortingHeader headerName="Brand" sortByLabel="brand"/></td>
                    <td style="width: 13%"><tags:sortingHeader headerName="Model" sortByLabel="model"/></td>
                    <td style="width: 14%">Color</td>
                    <td style="width: 10%"><tags:sortingHeader headerName="Display size"
                                                               sortByLabel="displaySizeInches"/></td>
                    <td style="width: 10%"><tags:sortingHeader headerName="Price" sortByLabel="price"/></td>
                    <td style="width: 10%">Quantity</td>
                    <td style="width: 10%">Action</td>
                </tr>
                </thead>
                <c:forEach var="phone" items="${productPage.phoneList}">
                    <c:set var="phoneId" value="${phone.id}"/>
                    <tr>
                        <td>
                            <a href="${pageContext.request.contextPath}/productDetails/${phoneId}">
                                <!-- todo: implement this -->
                                <img style="max-height: 200px"
                                     src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                            </a>
                        </td>
                        <td>${phone.brand}</td>
                        <td>${phone.model}</td>
                        <td>
                            <c:forEach var="color" items="${phone.colors}" varStatus="index">
                                <c:out value="${color.code}"/>
                                <c:if test="${not index.last}">, </c:if>
                            </c:forEach>
                        </td>
                        <td>${phone.displaySizeInches}</td>
                        <td>${phone.price}$</td>
                        <td>
                            <input type="text" class="form-control" placeholder="Quantity" name="quantity" id="quantity-${phoneId}"
                                   value="1">
                            <span id="quantity-message-${phoneId}" style="display: none"></span>
                        </td>
                        <td>
                            <button class="btn btn-outline-dark"
                                    onclick="addToCart(${phoneId}, '${pageContext.request.contextPath}/ajaxCart')">Add to cart
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <tags:paginationOnPage totalNumber="${productPage.totalNumOFPages}"
                                   currentPage="${productPage.currentPage}"/>
        </c:otherwise>
    </c:choose>
</tags:page>