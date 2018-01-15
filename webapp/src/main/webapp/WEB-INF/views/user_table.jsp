<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>JSP page</title>
</head>
<body>

<table border = 1px>
    <tr>
        <th></th>
        <th>login</th>
        <th>name</th>
        <th>email</th>
    </tr>
    <jsp:useBean id="users" scope="request" type="java.util.List"/>
    <c:forEach items="${users}" var="user" varStatus="count">
        <tr>
            <form method = "POST">
                <td><c:out value="${count.index + 1}"/></td>
                <td><c:out value="${user.login}"/></td>
                <input type="hidden" name="login" value="${user.login}">
                <td><input type="text" name="name" value="${user.name}"></td>
                <td><input type="text" name="email" value="${user.email}"></td>
                <td><input type="submit" formaction ="${pageContext.request.contextPath}/update" value="Обновить"></td>
                <td><input type="submit" formaction = "${pageContext.request.contextPath}/delete" value="Удалить"></td>
            </form>
        </tr>
    </c:forEach>
</table>

<form method = "POST" action="${pageContext.request.contextPath}/create">
    <input type="text" name="login" value="login">
    <input type="text" name="name" value="name">
    <input type="text" name="email" value="email">
    <p><input type="submit" value="Создать"></p>
</form>
</body>
</html>
