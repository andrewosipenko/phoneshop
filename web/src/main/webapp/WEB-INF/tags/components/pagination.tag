<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@ attribute name="currentPage" type="java.lang.Integer" required="true" %>
<%@ attribute name="pagesTotal" type="java.lang.Integer" required="true" %>

<script>
    var gotoPage = function (page) {
        $('#gotoPageInput').val(page);
        $('#gotoPageForm').submit();
    }
</script>
<div class="container">
    <div class="float-left">
        <form method="get" id="gotoPageForm">
            <label>
                <c:if test="${not empty param.search}">
                    <input type="hidden" name="search" value="${param.search}"/>
                </c:if>
                <c:if test="${not empty param.sortBy}">
                    <input type="hidden" name="sortBy" value="${param.sortBy}"/>
                </c:if>
                <button class="btn btn-secondary" type="submit">Goto page</button>
                <input id="gotoPageInput" class="text-field" type="number" min="1" max="${pagesTotal}" name="page" value="${currentPage}"/>
            </label>
        </form>
    </div>
    <nav aria-label="..." class="float-right">
        <ul class="pagination">
            <li class="page-item ${currentPage eq 1 ? "disabled" : ""}">
                <a class="page-link" onclick="gotoPage(${currentPage-1})">Previous</a>
            </li>
            <c:choose>
                <c:when test="${pagesTotal le 10}">
                    <c:forEach var="page" begin="1" end="${pagesTotal}">
                        <li class="page-item ${page eq currentPage ? "active" : ""}"><a class="page-link" onclick="gotoPage(${page})">${page}</a></li>
                    </c:forEach>
                </c:when>
                <c:when test="${pagesTotal gt 10 && (currentPage le 3 || currentPage gt pagesTotal-3)}">
                    <c:forEach var="page" begin="1" end="3">
                        <li class="page-item ${page eq currentPage ? "active" : ""}"><a class="page-link" onclick="gotoPage(${page})">${page}</a></li>
                    </c:forEach>
                    <li class="page-item disabled"><a class="page-link" href="#">...</a></li>
                    <c:forEach var="page" begin="${pagesTotal-2}" end="${pagesTotal}">
                        <li class="page-item ${page eq currentPage ? "active" : ""}"><a class="page-link" onclick="gotoPage(${page})">${page}</a></li>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <li class="page-item"><a class="page-link" onclick="gotoPage(1)">1</a></li>
                    <li class="page-item"><a class="page-link" onclick="gotoPage(2)">2</a></li>
                    <li class="page-item disabled"><a class="page-link" href="#">...</a></li>
                    <li class="page-item active"><a class="page-link" onclick="gotoPage(${currentPage})">${currentPage}</a></li>
                    <li class="page-item disabled"><a class="page-link" href="#">...</a></li>
                    <li class="page-item"><a class="page-link" onclick="gotoPage(${pagesTotal-1})">${pagesTotal-1}</a></li>
                    <li class="page-item"><a class="page-link" onclick="gotoPage(${pagesTotal})">${pagesTotal}</a></li>
                </c:otherwise>
            </c:choose>
            <li class="page-item ${currentPage eq pagesTotal ? "disabled" : ""}">
                <a class="page-link" onclick="gotoPage(${currentPage+1})">Next</a>
            </li>
        </ul>
    </nav>
</div>
