<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>
</head>
<body>
<h4>Login Page</h4>
<form name='f' method='POST' action='login'>
    <table>
        <tr>
            <td>User:</td>
            <td>
                <input type='text' name='username' value=''>
            </td>
        </tr>
        <tr>
            <td>Password:</td>
            <td>
                <input type='password' name='password'/>
            </td>
        </tr>
        <tr>
            <td>
                <input name="submit" type="submit" value="submit"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>