<%@ attribute name="firstName" required="true" type="java.lang.String" %>
<%@ attribute name="lastName" required="true" type="java.lang.String" %>

<p>${firstName.concat(" ").concat(lastName)}</p>