<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/templates" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<template:page>
    <div class="container">
        <div class="row">
            <div class="col-sm-12 col-md-10 col-md-offset-1">
                <template:orderOverview order="${order}"/>
            </div>
        </div>
        <form method="post">
            <a href="<c:url value="/admin/orders"/>" class="btn btn-primary">
                Back
            </a>
            <sec:csrfInput/>
            <c:if test="${order.status eq 'NEW'}">
                <button type="submit" name="status" value="delivered" class="btn btn-success">
                    Delivered
                </button>
                <button type="submit" name="status" value="rejected" class="btn btn-danger">
                    Rejected
                </button>
            </c:if>
        </form>
    </div>
    </div>
    </div>
</template:page>