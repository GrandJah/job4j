<%@ page contentType="text/HTML;charset=UTF-8" language="JAVA" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="users" scope="request" type="java.util.Map<ru.job4j.data_base.model.User, ru.job4j.data_base.model.Role>"/>
<jsp:useBean id="useUser" scope="request" type="ru.job4j.data_base.model.User"/>

<c:import url="head.jsp"/>



<c:forEach items="${users}" var="user" varStatus="count">
        <tr>

        <td><c:out value="${count.index +1 }"/></td>
        <td><c:out value="${user.key.login}"/></td>

        <c:if test="${user.key.login eq useUser.login}">
            <form id="user" method="POST">
                <input type="hidden" name="login" value="${user.key.login}"/>
                <input type="hidden" name="role" value="${user.value.name()}"/>
            </form>
            <td><input form="user" type="text" name="name" value="${user.key.name}"/></td>
            <td><input form="user" type="text" name="email" value="${user.key.email}"/></td>
        </c:if>

        <c:if test="${user.key.login ne useUser.login}">
            <td><c:out value="${user.key.name}"/></td>
            <td><c:out value="${user.key.email}"/></td>
        </c:if>

        <td><c:out value="${user.value.name()}"/></td>

        <c:if test="${user.key.login eq useUser.login}">
            <td>
                <input form="user" type="submit" formaction="${pageContext.request.contextPath}/update" value="Обновить"/>
            </td>
        </c:if>
    </tr>
</c:forEach>
</table>


<c:import url="footer.jsp"/>
