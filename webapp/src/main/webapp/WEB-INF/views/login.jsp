<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link rel="stylesheet" href="resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="resources/css/glyph.css">
    <link rel="stylesheet" href="resources/css/main.css">
    <title>Login</title>
</head>
<body>
<div class="container-fluid" style="margin-top: 30px">
    <div class="row">
        <div class="col-sm-4"></div>
        <div class="col-sm-4">
            <form method = "POST" action="${pageContext.request.contextPath}/login">

                <div class="input-group">
                    <div class="input-group-prepend"><span class="input-group-text"><i class="glyphicon glyphicon-user" aria-hidden="true"></i></span></div>
                    <input type="text" pattern="[A-Za-z]{5,15}" min="5" max="15" name="login" value="login">
                </div>

                <div class="input-group">
                    <div class="input-group-prepend"><span class="input-group-text"><i class="glyphicon glyphicon-comment" aria-hidden="true"></i></span></div>
                    <input type="password" name="password" value="password">
                </div>

                <button class="btn btn-primary" type="submit">Войти</button>
            </form>
        </div>
        <div class="col-sm-2"></div>
    </div>
</div>
<script src="resources/js/jquery-3.3.1.min.js"> </script>
<script src="resources/js/bootstrap.min.js"> </script>
<script src="resources/js/main.js"> </script>
</body>
</html>
