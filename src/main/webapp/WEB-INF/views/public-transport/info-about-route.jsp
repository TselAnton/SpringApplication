<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
    <style>
        <%@ include file='css/input-value-date-style.css' %>
    </style>
    <head>
        <title>Автопарк</title>
        <meta charset="utf-8" />
    </head>
    <body>
        <div class="car-park">
            <h1 class="heading">Информация о пройденных маршрутах</h1>
            <div class="services">
                <div class="form-order">
                    <form action="/SpringApplication/public-transport-service/info-about-route" method="get">
                    <div class="information-input">
                        <label for="routeNumber">Введите номер маршрута: </label>
                        <input type="text" id="routeNumber" name="routeNumber" autocomplete="off">
                    </div>
                    <div class="date-picker">
                        <p>Выберите дату: </p>
                        <label for="from-date">с </label>
                        <input type="date" name="fromDate" id="from_date">
                        <label for="by-date">по </label>
                        <input type="date" name="byDate" id="by_date">
                    </div>
                    <button class="taxi-ordering" type="submit">Найти информацию</button>
                    </form>
                </div>
                <a class="effect-hover" href="/SpringApplication/public-transport-service">Вернуться назад</a>
            </div>
            <p class="date-time">Время: ${time}. Дата: ${date}</p>
        </div>
    </body>
</html>