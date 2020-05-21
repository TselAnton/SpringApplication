<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<style>
    <%@ include file='css/menu-page-style.css' %>
</style>
<html lang="ru>
    <head>
        <title>Автопарк</title>
        <meta charset="utf-8" />
    </head>
    <body>
         <div class="car-park">
            <h1 class="heading">Ошибка</h1>
            <div class="services">
                <p>${errorMessage}</p>
                <ul class="taxi-orders">
                    <li><a class="effect-hover" href=${link}>Вернуться назад</a></li>
                </ul>
            </div>
            <p class="date-time">Время: ${time}. Дата: ${date}</p>
         </div>
    </body>
</html>