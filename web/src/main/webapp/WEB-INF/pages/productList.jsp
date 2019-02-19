<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<tags:master pageTitle="Product list">
    <p id="test"></p>
    <form method="get">
        <input type="hidden" value="${page}" name="page">
        <input name="query" value="${query}">
        <button>Search</button>
    </form>
        <table class="table table-bordered">
            <thead>
            <tr>
                <td>Image</td>
                <td>
                    Brand
                    <tags:link page="${page}" text="asc" order="ASC" sort="brand" query="${query}"/>
                    <tags:link page="${page}" text="desc" order="DESC" sort="brand" query="${query}"/>
                </td>
                <td>
                    Model
                    <tags:link page="${page}" text="asc" order="ASC" sort="model" query="${query}"/>
                    <tags:link page="${page}" text="desc" order="DESC" sort="model" query="${query}"/>
                </td>
                <td>Color</td>
                <td>
                    Display size
                    <tags:link page="${page}" text="asc" order="ASC" sort="displaySizeInches" query="${query}"/>
                    <tags:link page="${page}" text="desc" order="DESC" sort="displaySizeInches" query="${query}"/>
                </td>
                <td>
                    Price
                    <tags:link page="${page}" text="asc" order="ASC" sort="price" query="${query}"/>
                    <tags:link page="${page}" text="desc" order="DESC" sort="price" query="${query}"/>
                </td>
                <td>Quantity</td>
                <td>Action</td>
            </tr>
            </thead>
            <c:forEach var="phone" items="${phones}" varStatus="status">
                <tr class="content-align">
                    <td>
                        <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}"
                             width="70" height="70">
                    </td>
                    <td>${phone.brand}</td>
                    <td>${phone.model}</td>
                    <td>
                        <c:forEach var="color" items="${phone.colors}">
                            ${color.code}
                            <br>
                        </c:forEach>
                    </td>
                    <td>${phone.displaySizeInches}"</td>
                    <td>${phone.price}$</td>
                    <td class="td-center-align">
                        <input class="input-text" type="number" id="quantity${phone.id}"/>
                    </td>
                    <td class="td-center-align">
                        <button onclick="ajaxAddCartItem(${status.index}, ${phone.id})">Add to</button>
                    </td>
                </tr>
            </c:forEach>
        </table>

   <table class="pagination-table" border="1" cellpadding="5" cellspacing="5">
        <tr>
            <c:if test="${page != 1}">
                <td><tags:link page="${page - 1}" text="<<" order="${order}" query="${query}" sort="${sort}"/></td>
            </c:if>

            <c:forEach begin="${page < 5 ? 1 : page - 4}"
                       end="${numberOfPage < 10 ? numberOfPage : page < 5 ? 10 : page < numberOfPage - 5 ? page + 5 : numberOfPage}" var="i">
                <c:choose>
                    <c:when test="${page == i}">
                        <td class="active-page"><tags:link page="${i}" text="${i}" order="${order}" query="${query}" sort="${sort}"/></td>
                    </c:when>
                    <c:otherwise>
                        <td><tags:link page="${i}" text="${i}" order="${order}" query="${query}" sort="${sort}"/></td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${page lt numberOfPage}">
                <td><tags:link page="${page + 1}" text=">>" order="${order}" query="${query}" sort="${sort}"/></td>

            </c:if>
        </tr>
    </table>
</tags:master>