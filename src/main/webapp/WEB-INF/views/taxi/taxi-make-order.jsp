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
    </head>
    <body>
         <div class="car-park">
            <h1 class="heading">Сделать заказ</h1>
            <div class="services">
                <form action="/SpringApplication/taxi-service/make-order" method="post">
                    <p>Введите ФИО: <input type="text" name="fullName"></p>
                    <p><input type="submit" value="Заказать такси"></p>
                </form>
                <a class="effect-hover" href="/SpringApplication/taxi-service">Вернуться назад</a>
            </div>
            <p class="date-time">Время: ${time}. Дата: ${date}</p>
         </div>
    </body>
</html>