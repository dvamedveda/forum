<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Forum</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"
            integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js"
            integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/"
            crossorigin="anonymous"></script>
</head>
    <body>
        <div class="container pt-3 px-3 mx-auto">
            <h3 class="text-center">
                Job4j Forum
            </h3>
            <c:if test="${requestScope['message'] != null}">
                <div style="color:red; font-weight: bold; margin: 30px 0px;">
                        ${requestScope['message']}
                </div>
            </c:if>
            <div class="d-flex justify-content-between">
                <a class="btn btn-primary" href="<c:url value='/create'/>">Создать тему</a>
                <c:if test="${requestScope['user'] != null}">
                    <div class="d-flex justify-content-end">
                        <button type="button" class="btn btn-outline-success mx-2">Вы вошли как: ${requestScope['user'].login}</button>
                        <a class="btn btn-primary" href="<c:url value='/logout'/>">Выйти</a>
                    </div>
                </c:if>
                <c:if test="${requestScope['user'] == null}">
                    <div class="d-flex justify-content-end">
                        <a class="btn btn-primary" href="<c:url value='/login'/>">Войти</a>
                    </div>
                </c:if>
            </div>
            <table class="table table-sm table-striped">
                <thead>
                <tr>
                    <th scope="col">Темы</th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="post" items="${requestScope['posts']}">
                    <tr>
                        <td>
                            <div class="d-flex flex-column">
                                <div class="d-flex flex-row">
                                    <span class="badge bg-secondary mx-1">${post.created}</span>
                                    <span class="badge bg-dark mx-1">${post.user.login}</span>
                                </div>
                                <div>
                                    <h5>${post.name}</h5>
                                </div>
                            </div>
                        </td>
                        <c:set var="viewUrl" value="view?id=${post.id}"/>
                        <c:set var="editUrl" value="edit?id=${post.id}"/>
                        <td width="30%" valign="middle" align="right">
                            <c:if test="${post.user.login == requestScope['user'].login}">
                                <a class="btn btn-outline-secondary btn-sm" href="${editUrl}">Редактировать</a>
                            </c:if>
                            <a class="btn btn-outline-success btn-sm" href="${viewUrl}">Перейти к теме</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
