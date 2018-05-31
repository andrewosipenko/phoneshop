<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>

<template:page>
  <jsp:attribute name="head">
    <style>
      td {
        padding: 5px;
        border: 1px solid;
      }
      table{
        width: 300px;
      }
    </style>
  </jsp:attribute>

  <jsp:attribute name="scripts">
    <script src="<c:url value="/resources/js/addToCartForm.js"/>"></script>
  </jsp:attribute>

  <jsp:body>
    <template:header cartQuantity="${cartQuantity}"   cartSubTotal="${cartSubTotal}"/>

    <div class="mr-5 mt-5 ml-5">
      <div class="row mb-3">
        <a class="btn btn-primary" href="${pageContext.servletContext.contextPath}/productList">Back to product list</a>
      </div>
      <div class="row offset-1">
        <h2>${phone.model}</h2>
      </div>
      <div class="row offset-1">
        <div class="col">
          <div>
            <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}" height="300">
          </div>
          <div>
              ${phone.description}
          </div>
          <div id="quantity-input-div" class="mt-5">
            <h3>Price: ${phone.price}$</h3>
            <div class="row">
              <div class="input-group col-3">
                <input type="text" class="form-control quantity-input" id="validationCustomUsername" value="1">
                <div class="invalid-tooltip">
                </div>
              </div>
              <button id="add-to-cart-btn" class="btn btn-primary col-2" data-price="${phone.price}" data-id="${phone.id}">Add to
              </button>
            </div>
          </div>
        </div>

        <div class="col offset-2">
          <div class="row mt-3">
            <h3>Display</h3>
          </div>
          <div class="row">
            <table>
              <tr>
                <td>Size</td>
                <td>${phone.displaySizeInches}"</td>
              </tr>
              <tr>
                <td>Resolution</td>
                <td>${phone.displayResolution}</td>
              </tr>
              <tr>
                <td>Technology</td>
                <td>${phone.displayTechnology}</td>
              </tr>
              <tr>
                <td>Pixel density</td>
                <td>${phone.pixelDensity}</td>
              </tr>
            </table>
          </div>

          <div class="row mt-3">
            <h3>Dimensions and weight</h3>
          </div>
          <div class="row">
            <table>
              <tr>
                <td>Length</td>
                <td>${phone.lengthMm} mm</td>
              </tr>
              <tr>
                <td>Width</td>
                <td>${phone.widthMm} mm</td>
              </tr>
              <tr>
                <td>Weight</td>
                <td>${phone.weightGr} gr</td>
              </tr>
            </table>
          </div>

          <div class="row mt-3">
            <h3>Camera</h3>
          </div>
          <div class="row">
            <table>
              <tr>
                <td>Front</td>
                <td>${phone.frontCameraMegapixels} megapixels</td>
              </tr>
              <tr>
                <td>Back</td>
                <td>${phone.backCameraMegapixels} megapixels</td>
              </tr>
            </table>
          </div>

          <div class="row mt-3">
            <h3>Battery</h3>
          </div>
          <div class="row">
            <table>
              <tr>
                <td>TalkTime</td>
                <td>${phone.talkTimeHours} hours</td>
              </tr>
              <tr>
                <td>Stand by Time</td>
                <td>${phone.standByTimeHours} hours</td>
              </tr>
              <tr>
                <td>Battery capacity</td>
                <td>${phone.batteryCapacityMah}mAh</td>
              </tr>
            </table>
          </div>


          <div class="row mt-3">
            <h3>Other</h3>
          </div>
          <div class="row">
            <table>
              <tr>
                <td>Colors</td>
                <td>
                  <template:colors colors="${phone.colors}"/>
                </td>
              </tr>
              <tr>
                <td>Device type</td>
                <td>${phone.deviceType}</td>
              </tr>
              <tr>
                <td>Bluetooth</td>
                <td>${phone.bluetooth}</td>
              </tr>
            </table>
          </div>

        </div>
      </div>
    </div>
  </jsp:body>
</template:page>