<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<body>
<c:if test="${not empty requestScope['message']}">
    <div style="color:red; font-weight: bold; margin: 30px 0px;">
            ${requestScope['message']}
    </div>
</c:if>
<form name='login' action="<c:url value='/login'/>" method='POST'>
    <table>
        <tr>
            <td>UserName:</td>
            <td><input type='text' name='username' required></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type='password' name='password' required/></td>
        </tr>
        <tr>
            <td colspan='2'><input name="submit" type="submit" value="submit" /></td>
        </tr>
    </table>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
<a class="btn btn-primary" href="<c:url value='register'/>" role="button">Регистрация</a>
</body>
</html>
