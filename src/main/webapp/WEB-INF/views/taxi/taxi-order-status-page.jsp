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
                <p>Заказ #${orderNumb}</p>
                <ul class="taxi-orders">
                    <li>${carClass}: ${carModel}, цвет ${carColor}</li>
                    <li>Номер машины: ${carNumber}</li>
                    <li>Водитель: ${driverName}</li>
                    <li>Цена поездки: ${price} руб</li>
                    <li>Статус поездки: ${status}</li>
                    <li>Длина поездки: ${tripLength} км</li>
                    <li>Начало поездки: ${startTime}</li>
                    <li>Конец поездки: ${endTime}</li>
                    <li><a class="effect-hover" href="/SpringApplication/taxi-service">Вернуться назад</a></li>
                </ul>
            </div>
            <p class="date-time">Время: ${time}. Дата: ${date}</p>
         </div>
    </body>
</html>