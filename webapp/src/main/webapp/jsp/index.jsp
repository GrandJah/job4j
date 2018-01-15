<%@ page import="ru.job4j.interface_servlet.UserStore" %>
<%@ page import="ru.job4j.crud_server.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JSP page</title>
</head>
<body>
<table border = 1px>
    <tr><th></th><th>login</th><th>name</th><th>email</th><th></th><th></th></tr>

    <%int i = 0; for(User user : UserStore.getInstance().getUsers()) {%>

    <tr><form method = "POST">
        <td><%=++i%></td>
        <td><%=user.getLogin()%></td>
        <input type="hidden" name="login" value="<%=user.getLogin()%>">
        <td><input type="text" name="name" value="<%=user.getName()%>"></td>
        <td><input type="text" name="email" value="<%=user.getEmail()%>"></td>
        <td><input type="submit" formaction = "<%=request.getContextPath()%>/update" value="Обновить"></td>
        <td><input type="submit" formaction = "<%=request.getContextPath()%>/delete" value="Удалить"></td>
    </form></tr>

    <% } %>

</table>

<form method = "POST" action="<%=request.getContextPath()%>/create">
    <input type="text" name="login" value="login">
    <input type="text" name="name" value="name">
    <input type="text" name="email" value="email">
    <p><input type="submit" value="Создать"></p>
</form>
</body>
</html>
