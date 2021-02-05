<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="FORM" uri="http://www.springframework.org/tags/form" %>

<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>

<header>
    <form class="cartInfo">
        PhoneShop cart:
        <p>
            <label class="btn btn-outline-danger" href="${pageContext.servletContext.contextPath}/cart"
                   class="totalQuantity">
                Total quantity : ${cart.totalQuantity} items </label>
        </p>
        <p>
            <label class="btn btn-outline-danger" href="${pageContext.servletContext.contextPath}/cart"
                   class="totalCost"> Total
                cost : ${cart.totalCost} $</label>
        </p>
    </form>
</header>

<form method="get" action="/cart" name="cartForm">
    <input type="button" id="cartValue" value="Cart : ${cart.totalCost}">
</form>

<p>
    Hello from product list!
</p>
<p>
Found
<c:out value="${cart.getCartItems().size()}"/> phones.

<body>

<form:form method="post" modelAttribute="cartPageDTO">
    <table class="table table-bordered">

    <thead class="thead-light">
    <tr>
        <th scope="col" style="text-orientation: upright">Image</th>
        <th scope="col" style="width: 10rem;">Brand</th>
        <th scope="col" style="width: 10rem;">Model</th>
        <th scope="col" style="width: 10rem;">Price</th>
        <th scope="col" style="width: 20rem;">Display Size Inches</th>
        <th scope="col">Color</th>
        <th scope="col">Description</th>
        <th scope="col">Quantity</th>
        <th scope="col">Action</th>
    </tr>
    </thead>

    <input type="hidden" name="_method" value="PUT" id="method-put"/>

    <c:forEach var="cartItemModel" items="${cart.cartItems}" varStatus="status">
        <tr class="productList">
        <td>
            <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${cartItemModel.phone.imageUrl}">
        </td>
        <td>${cartItemModel.getPhone().getBrand()}</td>
        <td>
            <a href="${pageContext.servletContext.contextPath}/productDetailsPage/productId=${cartItemModel.getPhone().getId()}">${cartItemModel.getPhone().getModel()}</a>
            <input type="hidden" name="productId" value="${cartItemModel.getPhone().getId()}">
        </td>
        <td>$ ${cartItemModel.getPhone().getPrice()}</td>
        <td>${cartItemModel.getPhone().getDisplaySizeInches()}</td>
        <td>
        <c:forEach items="${cartItemModel.getPhone().getColors()}" var="colorItem">
            <p>
                    ${colorItem.code}
            </p>
        </c:forEach>
        </td>
        <td>${cartItemModel.getPhone().getDescription()}</td>
        <td>
            <c:set var="idPhone" value="${cartItemModel.phone.id}"/>
            <input style="visibility: hidden" name="cartInfoDTOList[${status.index}].productId" value="${idPhone}"/>
            <input name="cartInfoDTOList[${status.index}].quantityToAdd" value="${cartItemModel.quantity}"/>
            <div style="color: red; font-size: small">
                <form:errors cssClass="errors" path="cartInfoDTOList[${status.index}].quantityToAdd"/>
            </div>
        </td>
        <td>
            <form>
                <button formaction="${pageContext.servletContext.contextPath}/cart/deleteCartItem">Delete
                    <input type="hidden" name="idToDelete" value="${cartItemModel.getPhone().getId()}">
                </button>
            </form>
        </td>
        </tr>
    </c:forEach>
    <td>
        <input type="submit" class="btn btn-success" value="Update"/>
    </td>
    </table>
</form:form>
</p>
</body>
</html>
