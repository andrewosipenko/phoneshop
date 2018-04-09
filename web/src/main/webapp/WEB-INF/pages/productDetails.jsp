<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags/components" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<template:page title="Product Details">
    <script> <%@ include file="scripts/addToCart.js"%> </script>

    <components:header cartShown="true"/>

    <div class="container mt-3">
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/productList">Back to product List</a>
    </div>

    <div class="container mt-3">
        <div class="float-left">
            <div>
                <div>
                    <h4><c:out value="${phone.model}"/></h4>
                    <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}"/>
                </div>
            </div>
            <div class="mt-3" style="width: 350px;">
                <p><c:out value="${phone.description}"/></p>
            </div>
            <div class="mt-3 px-2 py-2 border py-3 pl-3" style="width: 250px;">
                <form:form modelAttribute="addToCartForm" id="addToCart${phone.id}Form">
                    <input type="hidden" name="phoneId" value="${phone.id}"/>
                    <p style="font-size: 20px;">Price: <b>$<c:out value="${phone.price}"/></b></p>
                    <form:input cssClass="form-control d-inline-block" cssStyle="width:70px;" path="quantity" id="phone${phone.id}Quantity"/>
                    <button class="btn btn-info d-inline-block ml-3" type="button" onclick="addToCart(${phone.id})">Add to cart</button>
                    <p style="position: absolute; margin-top: 7px; color:red; display: none;" id="quantity${phone.id}ErrorMessage"></p>
                </form:form>
            </div>
        </div>
        <div class="float-right" style="width: 40%;">
            <h4><b>Display</b></h4>
            <table class="table table-bordered">
                <tbody>
                    <tr><td>Size: </td><td><c:out value="${phone.displaySizeInches}"/>''</td></tr>
                    <tr><td>Resolution: </td><td><c:out value="${phone.displayResolution}"/></td></tr>
                    <tr><td>Technology: </td><td><c:out value="${phone.displayTechnology}"/></td></tr>
                    <tr><td>Pixel Density: </td><td><c:out value="${phone.pixelDensity}"/></td></tr>
                </tbody>
            </table>
            <br/>
            <h4><b>Dimensions & weight</b></h4>
            <table class="table table-bordered">
                <tbody>
                    <tr><td>Length: </td><td><c:out value="${phone.lengthMm.intValue()}"/> mm</td></tr>
                    <tr><td>Width: </td><td><c:out value="${phone.widthMm.intValue()}"/> mm</td></tr>
                    <tr><td>Weight: </td><td><c:out value="${phone.weightGr.intValue()}"/> g</td></tr>
                </tbody>
            </table>
            <br/>
            <h4><b>Camera</b></h4>
            <table class="table table-bordered">
                <tbody>
                    <tr><td>Front: </td><td><c:out value="${phone.frontCameraMegapixels}"/> MP</td></tr>
                    <tr><td>Back: </td><td><c:out value="${phone.backCameraMegapixels}"/> MP</td></tr>
                </tbody>
            </table>
            <br/>
            <h4><b>Battery</b></h4>
            <table class="table table-bordered">
                <tbody>
                <tr><td>Talk time: </td><td><c:out value="${phone.talkTimeHours.intValue()}"/> h</td></tr>
                <tr><td>Stand by time: </td><td><c:out value="${phone.standByTimeHours.intValue()}"/> h</td></tr>
                <tr><td>Battery capacity: </td><td><c:out value="${phone.batteryCapacityMah}"/> mAh</td></tr>
                </tbody>
            </table>
            <br/>
            <h4><b>Other</b></h4>
            <table class="table table-bordered">
                <tbody>
                <tr><td>Colors: </td><td><c:forEach items="${phone.colors}" var="color" varStatus="loop"><c:out value="${color.code}"/><c:if test="${not loop.last}">, </c:if></c:forEach></td></tr>
                <tr><td>Device type: </td><td><c:out value="${phone.deviceType}"/></td></tr>
                <tr><td>Bluetooth: </td><td><c:out value="${phone.bluetooth}"/></td></tr>
                </tbody>
            </table>
        </div>
    </div>
</template:page>

