<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <link rel="stylesheet" href="webjars/bootstrap/3.2.0/css/bootstrap.min.css"/>
    <script src="webjars/jquery/1.11.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <title>Product list</title>
    <script type="text/javascript">
    function doAjax(phoneId, quantity) {
    var data = {phoneId: phoneId, quantity: quantity};
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url : "ajaxCart",
            method : "POST",
            dataType : 'json',
            data : JSON.stringify(data),
            success : function(data) {
                $('#result').html(data);
            }
        });
    }

    </script>
</head>
<body>
<p>
    Hello from product list!
</p>
<div id="result"></div>
<p>
    Found
    <c:out value="${phones.size()}"/> phones.

<form method="post">
    <div class="table-responsive">
        <table class="table table-bordered">
            <thead>
            <tr>
                <td>Image</td>
                <td>Brand</td>
                <td>Model</td>
                <td>Color</td>
                <td>Display size</td>
                <td>Price</td>
                <td>Quantity</td>
                <td>Action</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="phone" items="${phones}">
                <tr>
                    <td>
                        <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                    </td>
                    <td>${phone.brand}</td>
                    <td>${phone.model}</td>
                    <td>
                        <c:forEach var="color" items="${phone.colors}">
                            ${color}
                        </c:forEach>
                    </td>
                    <td>${phone.displaySizeInches}"</td>
                    <td>$ ${phone.price}</td>
                    <td>
                        <input type="text" id=quantity${phone.id} style="text-align: right" value="1"/>
                    </td>
                    <td>
                        <button type="button" onclick="doAjax(${phone.id}, document.getElementById('quantity${phone.id}').value)">Add to</button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</form>
</p>
</body>
</html>