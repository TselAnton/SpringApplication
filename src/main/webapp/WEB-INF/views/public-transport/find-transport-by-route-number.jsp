<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
    <style>
        <%@ include file='css/input-value-style.css' %>
    </style>
    <head>
        <title>Автопарк</title>
        <meta charset="utf-8" />
    </head>
    <body>
        <div class="car-park">
            <h1 class="heading">Информация о транспорте</h1>
            <div class="services">
                <div class="form-order">
                    <form action="/SpringApplication/public-transport-service/show-transport-info" method="get">
                        <div class="information-input">
                            <label for="routeNumber">Введите номер маршрута: </label>
                            <input type="text" id="routeNumber" name="routeNumber" autocomplete="off">
                        </div>
                        <button class="taxi-ordering" type="submit">Найти маршрут</button>
                    </form>
                </div>
                <a class="effect-hover" href="/SpringApplication/public-transport-service">Вернуться назад</a>
            </div>
            <p class="date-time">Время: ${time}. Дата: ${date}</p>
        </div>
    </body>
</html>