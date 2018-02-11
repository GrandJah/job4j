<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Login</title>
</head>
<body>
<form method = "POST" action="${pageContext.request.contextPath}/login">
    Login: <input type="text" name="login" value="login">
    Password: <input type="text" name="password" value="password">
    <p><input type="submit" value="Войти"></p>
</form>
</body>
</html>
