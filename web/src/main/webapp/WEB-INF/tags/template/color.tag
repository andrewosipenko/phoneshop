<%@ tag pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@ attribute name="size" type="java.lang.Integer" %>
<%@ attribute name="color" type="java.lang.String" %>
<div style="width: ${size}px;
            height: ${size}px;
            display:inline-block;
            border-radius: 100%;
            background-color: ${color};
            box-shadow: 0 0 5px -1px black ;
            <%--background-color: ${ '0'<color.charAt(0) and color.charAt(1)<'9'? "#".concat(color) : color};--%>
            padding: 0 2px">
</div>
