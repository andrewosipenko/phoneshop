<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags/components" %>

<template:page title="Product List">
    <script> <%@ include file="scripts/addToCart.js" %> </script>
    <script> <%@ include file="scripts/sortTable.js" %> </script>

    <components:header cartShown="true"/>

    <div class="container mt-3 py-1">
        <div class="d-inline-block">
            <h3>Phones</h3>
        </div>
        <components:search/>
    </div>

    <form method="get" id="sortByForm" hidden>
        <c:if test="${not empty param.search}">
            <input type="hidden" name="search" value="${param.search}"/>
        </c:if>
        <input type="hidden" name="sortBy" id="sortByInput"/>
    </form>

    <div class="container mt-2">
        <table class="table table-bordered table-striped">
            <thead style="background-color: #828082;">
            <tr class="d-table-row text-light text-center">
                <th scope="col" style="width: 0%">Image</th>
                <th scope="col" style="width: 10%"><a style="display: block; cursor: pointer;" onclick="sortBy('brand')">Brand <components:tableColumnArrow test="brand"/></a></th>
                <th scope="col" style="width: 19%"><a style="display: block; cursor: pointer;" onclick="sortBy('model')">Model <components:tableColumnArrow test="model"/></a></th>
                <th scope="col" style="width: 20%">Colors</th>
                <th scope="col" style="width: 15%"><a style="display: block; cursor: pointer;" onclick="sortBy('display_size')">Display size<components:tableColumnArrow test="display_size"/></a></th>
                <th scope="col" style="width: 9%"><a style="display: block; cursor: pointer;" onclick="sortBy('price')">Price <components:tableColumnArrow test="price"/></a></th>
                <th scope="col" style="width: 9%">Quantity</th>
                <th scope="col" colspan="2">Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${phones}" var="phone">
                <form:form modelAttribute="addToCartForm" id="addToCart${phone.id}Form">
                    <input type="hidden" name="phoneId" value="${phone.id}"/>
                    <tr>
                        <td class="p-0 m-0" style="width:1px;"><img width="100px" height="100px" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}"></td>
                        <td style="vertical-align: middle!important"><c:out value="${phone.brand}"/></td>
                        <td style="vertical-align: middle!important"><c:out value="${phone.model}"/></td>
                        <td style="vertical-align: middle!important"><c:forEach items="${phone.colors}" var="color" varStatus="loop"><c:out value="${color.code}"/><c:if test="${not loop.last}">, </c:if></c:forEach></td>
                        <td style="vertical-align: middle!important"><c:out value="${phone.displaySizeInches}''"/></td>
                        <td style="vertical-align: middle!important">$<c:out value="${phone.price}"/></td>
                        <td style="vertical-align: middle!important">
                            <form:input cssClass="form-control" cssStyle="width:70px;" path="quantity" id="phone${phone.id}Quantity"/>
                            <p style="position: absolute; margin-top: 7px; color:red; display: none;" id="quantity${phone.id}ErrorMessage"></p>
                        </td>
                        <td class="text-center" style="vertical-align: middle!important"><a style="text-decoration: underline;" href="${pageContext.request.contextPath}/productDetails?phoneId=${phone.id}">Details</a></td>
                        <td class="text-center" style="vertical-align: middle!important"><button class="btn btn-info" type="button" onclick="addToCart(${phone.id})">Add to cart</button></td>
                    </tr>
                </form:form>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <components:pagination currentPage="${currentPage}" pagesTotal="${pagesTotal}"/>
</template:page>
