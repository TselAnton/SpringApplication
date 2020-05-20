<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<style>
    <%@ include file='..//css/menu-page-style.css' %>
</style>
<html lang="ru>
    <head>
        <title>Автопарк</title>
        <meta charset="utf-8" />
        <link rel="stylesheet" type="css" href="style.scss"/>
    </head>
    <body>
        <div class="car-park">
            <h1 class="heading">Общественный транспорт</h1>
                <div class="services">
                <p>Доступные сервисы:</p>
                <ul class="public-transport">
                    <li><a class="effect-hover" href="#">Найти транспорт по номеру маршрута</a></li>
                    <li><a class="effect-hover" href="#">Узнать текущее местоположение транспорта по номеру маршрута</a></li>
                    <li><a class="effect-hover" href="#">Получить информацию о поездках за период по номеру маршрута</a></li>
                    <li><a class="effect-hover" href="#">Получить информацию о поездках за период всего транспорта</a></li>
                    <li><a class="effect-hover" href="/SpringApplication">Назад</a></li>
                </ul>
            </div>
            <p class="date-time">Время: ${time}. Дата: ${time}</p>
        </div>
    </body>
</html>