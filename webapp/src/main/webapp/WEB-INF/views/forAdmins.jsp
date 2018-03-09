<%@ page contentType="text/HTML;charset=UTF-8" language="JAVA" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.job4j.data_base.model.Role" %>

<Doct
<jsp:useBean id="users" scope="request" type="java.util.Map<ru.job4j.data_base.model.User, ru.job4j.data_base.model.Role>"/>
<jsp:useBean id="roles" scope="request" type="java.util.List<ru.job4j.data_base.model.Role>"/>

<c:import url="head.jsp"/>

<c:forEach items="${users}" var="user" varStatus="count">
    <tr>
        <form method="POST">
            <td><c:out value="${count.index + 1}"/></td>
            <td><c:out value="${user.key.login}"/></td>
            <input type="hidden" name="login" value="${user.key.login}"/>
            <td><input type="text" name="name" value="${user.key.name}"/></td>
            <td><input type="text" name="email" value="${user.key.email}"/></td>
            <td><select name="role">
                <c:forEach items="${roles}" var="role_item" >
                    <option ${role_item == user.value ? "selected" : ""} ><c:out value="${role_item.name()}"/></option>
                </c:forEach>
            </select></td>
            <td>
                <input type="submit" formaction="${pageContext.request.contextPath}/update" value="Обновить"/>
            </td>
            <td>
                <input type="submit" formaction="${pageContext.request.contextPath}/delete" value="Удалить"/>
            </td>
        </form>
    </tr>
</c:forEach>
</table>
</br></br>

    <form method = "POST" action="${pageContext.request.contextPath}/create">
    <input type="text" name="login" value="login">
    <input type="text" name="name" value="name">
    <input type="text" name="email" value="email">
        <td><select name="role">
            <c:forEach items="${roles}" var="role_item" >
                <option><c:out value="${role_item.name()}"/></option>
            </c:forEach>
        </select></td>
    <input type="submit" value="Создать">
</form>


<c:import url="footer.jsp"/>


