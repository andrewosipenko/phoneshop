<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<template:page >
    <c:if test="${error ne null}">
        <div class="alert alert-danger">
                ${error}
        </div>
    </c:if>
    <div class="jumbotron">
        <div class="row">
            <div class="col">
                <form name='loginForm' action="<c:url value="/login" />" method='POST'>
                    <div class="form-group">
                        <label class="lead" for="user"><span>Username</span></label>
                        <br>
                        <input id="user" type='text' name='username' value=''>
                    </div>
                    <div class="form-group">
                        <label class="lead" for="pass"><span>Password</span></label>
                        <br>
                        <input id="pass" type='password' name='password' />
                    </div>
                    <div class="form-group" style="padding-top: 10px">
                        <input class="btn btn-success btn-lg" name="submit" type="submit" value="Submit" />
                        <input class="btn btn-warning btn-lg" name="reset" type="reset" />
                    </div>
                </form>
            </div>
            <div class="col"></div>
        </div>
    </div>
</template:page>


