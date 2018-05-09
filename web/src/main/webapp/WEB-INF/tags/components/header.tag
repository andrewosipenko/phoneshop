<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@ attribute name="cartShown" type="java.lang.Boolean" required="false" %>
<%@ attribute name="productListButtonEnabled" type="java.lang.Boolean" required="false" %>
<%@ attribute name="titleEnabled" type="java.lang.Boolean" required="false" %>
<%@ attribute name="title" type="java.lang.String" required="false" %>

<c:if test="${cartShown}">
    <script>
        $(document).ready(function() {
            $.get({
                url: "${pageContext.request.contextPath}/ajaxCart",
                success: function(status) {
                    $('#phonesTotal').html(status.phonesTotal);
                    $('#costTotal').html(status.subtotal);
                }
            });
        });
    </script>
</c:if>

<div class="bg-light w-100">
    <div class="container p-3">
        <div class="page-header text-primary">
            <a style="text-decoration: none; color: inherit;" href="${pageContext.request.contextPath}"><h1 class="d-inline">Phone Shop</h1></a>
            <c:if test="${cartShown}">
                <a class="btn btn-primary d-inline-block float-right mt-2" href="${pageContext.request.contextPath}/cart">
                    My cart: <span id="phonesTotal"></span> items $<span id="costTotal"></span>
                </a>
            </c:if>
        </div>
    </div>
</div>
<c:if test="${titleEnabled}">
    <div class="container mt-3 py-1">
        <div class="d-inline-block">
            <h3><c:out value="${title}"/></h3>
        </div>
    </div>
</c:if>
<c:if test="${productListButtonEnabled}">
    <div class="container mt-1">
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/productList">Back to product list</a>
    </div>
</c:if>

