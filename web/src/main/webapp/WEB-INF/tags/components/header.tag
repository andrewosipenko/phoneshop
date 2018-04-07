<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@ attribute name="cartShown" type="java.lang.Boolean" required="false" %>

<c:if test="${cartShown}">
    <script>
        $(document).ready(function() {
            $.get({
                url: "${pageContext.request.contextPath}/ajaxCart",
                success: function(status) {
                    $('#phonesTotal').html(status.phonesTotal);
                    $('#costTotal').html(status.costTotal);
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
                <a class="btn btn-primary d-inline-block float-right mt-2" href="#">
                    My cart: <span id="phonesTotal"></span> items $<span id="costTotal"></span>
                </a>
            </c:if>
        </div>
    </div>
</div>

