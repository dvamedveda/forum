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
                Изменение темы
            </h3>
            <form action="/save" method="post">
                <table style="min-width: 100%">
                    <tr>
                        <td>
                            <h4>Измените название темы:</h4>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="text" name="name" value="${requestScope['post'].name}" style="min-width: 100%">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <h4>Измените описание темы:</h4>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <textarea name="desc" rows="4" style="min-width: 100%">${requestScope['post'].desc}</textarea>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input hidden type="text" name="id" value="${requestScope['post'].id}">
                            <input type="submit" value="Сохранить тему"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </body>
</html>