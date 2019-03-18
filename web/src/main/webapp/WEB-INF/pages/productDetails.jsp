<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tags:master pageTitle="productDetails" isShowCart="true">
    <div class="content">
        <tags:backToProductList text="back to product list"/>
        <div class="clearfix"/>
        <br>
        <div class="column column2">
            <h2>Display</h2>
            <table class="table table-bordered">
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

            <br>

            <h2>Dimensions & weight</h2>
            <table class="table table-bordered">
                <tr>
                    <td>Length</td>
                    <td>${phone.lengthMm} mm</td>
                </tr>
                <tr>
                    <td>Width</td>
                    <td>${phone.widthMm} mm</td>
                </tr>
                <tr>
                    <td>Color</td>
                    <td><c:forEach var="color" items="${phone.colors}">
                            ${color.code}
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td>Weight</td>
                    <td>${phone.widthMm} mm</td>
                </tr>
            </table>

            <br>

            <h2>Camera</h2>
            <table class="table table-bordered">
                <tr>
                    <td>Front</td>
                    <td>${phone.frontCameraMegapixels} megapixels</td>
                </tr>
                <tr>
                    <td>Back</td>
                    <td>${phone.backCameraMegapixels} megapixels</td>
                </tr>
            </table>

            <br>

            <h2>Battery</h2>
            <table class="table table-bordered">
                <tr>
                    <td>Talk time</td>
                    <td>${phone.talkTimeHours} hours</td>
                </tr>
                <tr>
                    <td>Stand by type</td>
                    <td>${phone.standByTimeHours} hours</td>
                </tr>
                <tr>
                    <td>Battery capacity</td>
                    <td>${phone.batteryCapacityMah}mAh</td>
                </tr>
            </table>

            <br>

            <h2>Other</h2>
            <table class="table table-bordered">
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
        <h1>${phone.model}</h1>
        <div class="column column1">
            <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}"
                 class="image-in-page"/>
            <div class="description">${phone.description}</div>
            <div class="area-add-to-cart">
                <p>Price: ${phone.price}$</p>
                <br>
                <input class="input-text" type="number" id="quantity${phone.id}"/>
                <button class="buttonAddCartItem" value="${phone.id}">Add to cart</button>
                <div class="error-message" id="errorField${phone.id}"/>
            </div>
        </div>
    </div>

</tags:master>
