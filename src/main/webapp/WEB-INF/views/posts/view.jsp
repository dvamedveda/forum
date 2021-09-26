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
    <div class="d-flex justify-content-between">
        <a class="btn btn-primary" href="<c:url value='/'/>">Вернуться на главную</a>
        <c:if test="${requestScope['user'] != null}">
            <div class="d-flex justify-content-end">
                <button type="button" class="btn btn-outline-success mx-2">Вы вошли как: ${requestScope['user'].login}</button>
                <a class="btn btn-primary" href="<c:url value='/logout'/>">Выйти</a>
            </div>
        </c:if>
    </div>
    <table class="table table-borderless">
        <tr class="table-primary">
            <td>
                <h3>
                   <span>${post.name}</span>
                </h3>
            </td>
        </tr>
        <tr class="table-primary">
            <td>${post.desc}</td>
        </tr>
        <tr class="table-primary">
            <td class="align-bottom" align="right">
                <div class="d-flex flex-row justify-content-end">
                    <span class="badge bg-dark mx-1">${post.user.login}</span>
                    <span class="badge bg-secondary mx-1">${post.created}</span>
                </div>
            </td>
        </tr>

    </table>
    <table class="table table-borderless">
        <c:forEach var="comment" items="${requestScope['comments']}">
            <tr class="table-info">
                <td colspan="5" bgcolor="#90ee90">${comment.text}</td>
            </tr>
            <tr class="table-info">
                <td class="align-bottom" align="right">
                    <div class="d-flex flex-row justify-content-end">
                        <span class="badge bg-dark mx-1">${comment.user.login}</span>
                        <span class="badge bg-secondary mx-1">${comment.commented}</span>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>
    <div class="width-auto">
        <form method="post" action="/comment?id=${requestScope['post'].id}">
            <table style="min-width: 100%">
                <tr>
                   <h4>
                       Комментировать тему
                   </h4>
                </tr>
                <tr>
                    <td>
                        <textarea class="form-control" name="text" placeholder="Оставьте какой-нибудь комментарий к теме" rows="5"></textarea>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="text" hidden name="user" value="${requestScope['user'].login}">
                        <input type="submit" name="submit" value="Оставить комментарий">
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>
