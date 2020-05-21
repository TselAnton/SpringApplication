<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
    <style>
        <%@ include file='css/make-order-style.css' %>
    </style>
    <head>
        <title>Автопарк</title>
        <meta charset="utf-8" />
    </head>
    <body>
        <div class="car-park">
            <h1 class="heading">Сделать заказ</h1>
            <div class="services">
                <div class="form-order">
                    <form action="/SpringApplication/taxi-service/make-order" method="post">
                        <div class="information-input">
                            <label for="fullName">Введите ФИО: </label>
                            <input type="text" id="fullName" name="fullName" autocomplete="off">
                        </div>
                        <button class="taxi-ordering" type="submit">Заказать такси</button>
                    </form>
                </div>
                <a class="effect-hover" href="/SpringApplication/taxi-service">Вернуться назад</a>
            </div>
            <p class="date-time">Время: ${time}. Дата: ${date}</p>
        </div>
    </body>
</html>