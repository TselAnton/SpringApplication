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
            <h1 class="heading">Заработок автопарка</h1>
            <div class="services">
                <p>Доступные сервисы:</p>
                <ul class="fleet-earnings">
                    <li><a class="effect-hover" href="#">Доход по определённому типу транспорта за период</a></li>
                    <li><a class="effect-hover" href="#">Доход по всем типам транспорта за период</a></li>
                    <li><a class="effect-hover" href="/SpringApplication">Назад</a></li>
                </ul>
            </div>
            <p class="date-time">Время: ${time}. Дата: ${time}</p>
        </div>
    </body>
</html>