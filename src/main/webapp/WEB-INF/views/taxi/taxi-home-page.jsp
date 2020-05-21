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
            <h1 class="heading">Такси</h1>
            <div class="services">
                <p>Доступные сервисы:</p>
                <ul class="taxi-orders">
                    <li><a class="effect-hover" href="/SpringApplication/taxi-service/get-make-order-form">Заказать такси</a></li>
                    <li><a class="effect-hover" href="/SpringApplication/taxi-service/get-order-status-form">Узнать статус заказа</a></li>
                    <li><a class="effect-hover" href="/SpringApplication/taxi-service/get-cancel-order-form">Отменить заказ</a></li>
                    <li><a class="effect-hover" href="/SpringApplication">Назад</a></li>
                </ul>
            </div>
            <p class="date-time">Время: ${time}. Дата: ${time}</p>
         </div>
    </body>
</html>