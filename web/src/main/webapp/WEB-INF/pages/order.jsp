<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/templates" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<template:page>
    <div class="row">
        <div class="col-sm-12 col-md-10 col-md-offset-1">
            <template:orderTable order="${order}"/>
            <form:form method="post" modelAttribute="order">
                <div class="form-group">
                    <label for="firstName">First name*</label>
                    <form:input path="firstName" class="form-control" id="firstName"/>
                    <div class="error-message" id="error-message">
                        <form:errors path="firstName"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="lastName">Last name*</label>
                    <form:input path="lastName" class="form-control" id="lastName"/>
                    <div class="error-message" id="error-message">
                        <form:errors path="lastName"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="deliveryAddress">Delivery address*</label>
                    <form:input path="deliveryAddress" class="form-control" id="deliveryAddress"/>
                    <div class="error-message" id="error-message">
                        <form:errors path="deliveryAddress"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="contactPhoneNo">Phone*</label>
                    <form:input path="contactPhoneNo" class="form-control" id="contactPhoneNo"/>
                    <div class="error-message" id="error-message">
                        <form:errors path="contactPhoneNo"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="additionalInfo">Additional info</label>
                    <form:textarea path="additionalInfo" class="form-control" id="additionalInfo"/>
                    <div class="error-message" id="error-message">
                        <form:errors path="additionalInfo"/>
                    </div>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-success">
                        Order
                    </button>
                </div>
                <sec:csrfInput/>
            </form:form>
        </div>
    </div>
</template:page>