<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
            <h1 class="heading ">Автопарк</h1>
                <div class="services">
                    <p>Доступные сервисы:</p>
                    <ul class="transport">
                        <li><a class="effect-hover" href="taxi-service">Такси</a></li>
                        <li><a class="effect-hover" href="public-transport-service">Общественный транспорт</a></li>
                    </ul>
                </div>
                <div class="control">
                    <p>Управление автопарком:</p>
                    <ul class="transport">
                        <li><a class="effect-hover" href="car-info">Информация о машинах</a></li>
                        <li><a class="effect-hover" href="cash-info">Зарабаток автопарка</a></li>
                    </ul>
                </div>
            <p class="date-time">Время: ${time}. Дата: ${date}</p>
        </div>
    </body>
</html>