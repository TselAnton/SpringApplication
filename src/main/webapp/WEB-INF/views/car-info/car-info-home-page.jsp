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
            <h1 class="heading">Информация о машинах</h1>
            <div class="services">
                <p>Доступные сервисы:</p>
                <ul class="machine-information">
                    <li><a class="effect-hover" href="/SpringApplication/car-info/info-about-all-working-transport">Информация о всём работающим транспорте в автопарке</a></li>
                    <li><a class="effect-hover" href="/SpringApplication/car-info/info-about-all-working-public-transport">Информация о всём работающим общественном транспорте</a></li>
                    <li><a class="effect-hover" href="/SpringApplication/car-info/info-about-working-taxi">Информация о всех работающих такси</a></li>
                    <li><a class="effect-hover" href="/SpringApplication/car-info/info-about-all-transport">Информация о всём транспорте в автопарке</a></li>
                    <li><a class="effect-hover" href="/SpringApplication">Назад</a></li>
                </ul>
            </div>
            <p class="date-time">Время: ${time}. Дата: ${time}</p>
        </div>
    </body>
</html>