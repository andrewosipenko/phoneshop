<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:page>
    <script> <%@ include file="js/addToCart.js" %> </script>
    <div class="row">
        <div class="col-sm-4">
            <a class ="btn btn-success" href="<c:url value="/productList"/>">Back to product list</a>
         <h3>${phone.model}</h3>
            <img width="60%"
                 src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
             <p>
                ${phone.description}
             </p>
            <div>
               <h4>Price: ${phone.price} $ </h4>
              <input class="text-input" id="quantity${phone.id}" value="0"><br>
             <a class="text-danger" id="errorMessage${phone.id}"></a><br>
             <input type="button" onclick="addToCart(${phone.id}, $('#quantity${phone.id}').val())" value="Add to">
            </div>
        </div>
        <div class="col-sm-8">
            <h4>Display</h4>
            <table class="table table-bordered">
                <tbody>
                <tr>
                    <th scope="col">Size</th>
                    <th scope="col">Resolution</th>
                    <th scope="col">Technology</th>
                    <th scope="col">Pixel density</th>
                </tr>
                <tr>
                    <td>${phone.displaySizeInches}"</td>
                    <td>${phone.displayResolution}</td>
                    <td>${phone.displayTechnology}</td>
                    <td>${phone.pixelDensity}</td>
                </tr>
                </tbody>
            </table>
            <hr>
            <h4>Dimensions & weight</h4>
            <table class="table table-bordered">
                <tbody>
                <tr>
                    <th scope="col">Length</th>
                    <th scope="col">Width</th>
                    <th scope="col">Weight</th>
                </tr>
                <tr>
                    <td>${phone.lengthMm}mm</td>
                    <td>${phone.widthMm}mm</td>
                    <td>${phone.weightGr}gr</td>
                </tr>
                </tbody>
            </table>
            <hr>
            <h4>Camera</h4>
            <table class="table table-bordered">
                <tbody>
                <tr>
                    <th scope="col">Font</th>
                    <th scope="col">Back</th>
                </tr>
                <tr>
                    <td>${phone.frontCameraMegapixels} megapixels</td>
                    <td>${phone.backCameraMegapixels} megapixels</td>
                </tr>
                </tbody>
            </table>
            <hr>
            <h4>Battery</h4>
            <table class="table table-bordered">
                <tbody>
                <tr>
                    <th scope="col">Talk time</th>
                    <th scope="col">Stand by time</th>
                    <th scope="col">Battery capacity</th>
                </tr>
                <tr>
                    <td>${phone.talkTimeHours} hours</td>
                    <td>${phone.standByTimeHours} hours</td>
                    <td>${phone.batteryCapacityMah} mAh</td>
                </tr>
                </tbody>
            </table>
            <hr>
            <h4>Other</h4>
            <table class="table table-bordered">
                <tbody>
                <tr>
                    <th scope="col">Colors</th>
                    <th scope="col">Device type</th>
                    <th scope="col">Bluetooth</th>
                </tr>
                <tr>
                    <td>
                        <template:getColors phoneColors="${phone.colors}"/>
                    </td>
                    <td>${phone.deviceType}</td>
                    <td>${phone.bluetooth}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</template:page>