<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<tags:page pageTitle="Login Page">
  <form method="post">
    <c:if test="${param.error != null}">
      <p class="error-message" style="color:red">
        Invalid username or password.
      </p>
    </c:if>

    <div class="form-group">
      <label for="username"><b>Username</b></label>
      <input type="text" placeholder="Enter Username" name="username" id="username" required>
    </div>

    <div class="form-group">
      <label for="password"><b>Password</b></label>
      <input type="password" placeholder="Enter Password" name="password" id="password" required>
    </div>

    <sec:csrfInput/>
    <button type="submit" class="btn btn-secondary btn-lg">Log in</button>
  </form>
</tags:page>
