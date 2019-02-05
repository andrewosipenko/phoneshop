<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<template:page>
    <div class="container">
        <h4>Admin orders</h4>
        <c:if test="${commentStatusMessage ne null}">
            <h4>
                    ${commentStatusMessage}
            </h4>
        </c:if>
        <c:choose>
            <c:when test="${not empty comments}">
                <table class="table table-striped">
                    <thead>
                    <th>Comment number</th>
                    <th>Phone Id</th>
                    <th>Author name</th>
                    <th>Rating</th>
                    <th>Text of comment</th>
                    </thead>
                    <tbody>
                    <c:forEach var="comment" items="${comments}">
                        <tr>
                            <td>
                                <a href="<c:url value="/admin/orders/${comment.id}"/>">${comment.id}</a>
                            </td>
                            <td>
                                <a href="<c:url value="/admin/orders/${comment.phoneId}"/>">${comment.phoneId}</a>
                            </td>
                            <td>
                                    ${comment}
                            </td>
                            <td>
                                    ${comment}
                            </td>
                            <td>
                                    ${comment}
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <p>
                    There are no orders!
                </p>
            </c:otherwise>
        </c:choose>
    </div>
</template:page>