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
            <h1 class="heading">Заработок Автопарка</h1>
            <div class="services">
                <div class="form-order">
                <form action="/SpringApplication/cash-info/get-cash-info" method="get">
                    <div class="date-picker">
                        <p>Выберите дату: </p>
                        <label for="from-date">с </label>
                        <input type="date" name="fromDate" id="from-date">
                        <label for="by-date">по </label>
                        <input type="date" name="byDate" id="by-date">
                    </div>
                    <button class="taxi-ordering" type="submit">Найти информацию</button>
                </form>
                </div>
                <a class="effect-hover" href="/SpringApplication">Вернуться назад</a>
            </div>
            <p class="date-time">Время: ${time}. Дата: ${date}</p>
        </div>
    </body>
</html>