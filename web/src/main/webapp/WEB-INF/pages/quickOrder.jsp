<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tags:master pageTitle="Quick order" isShowCart="true">
    <form:form method="post" action="${pageContext.servletContext.contextPath}/quickOrder/add" modelAttribute="quickOrderInfo">
        <c:forEach begin="${0}" end="${inputsCount}" varStatus="inputStatus">
            <form:input path="phonesId" name="phonesId${inputStatus.index}"/>
            <form:input path="quantities" name="quantities${inputStatus.index}"/><br>
        </c:forEach>
        <button>Add</button>
    </form:form>
</tags:master>
