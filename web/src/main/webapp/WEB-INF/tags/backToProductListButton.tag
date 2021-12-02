<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>

<div class="row">
    <div class="col col-md-2">
        <form action="${pageContext.servletContext.contextPath}/productList">
            <input type="submit" value="Back to product list" class="btn btn-secondary"/>
        </form>
    </div>
</div>