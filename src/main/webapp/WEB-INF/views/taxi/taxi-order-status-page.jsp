<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
    <style>
        <%@ include file='css/order-status-style.css' %>
    </style>
    <head>
        <title>Автопарк</title>
        <meta charset="utf-8" />
    </head>
    <body>
        <div class="car-park">
            <h1 class="heading">Такси</h1>
            <div class="services">
                <p>Заказ #${order.orderNumb}</p>
                <div class="order-description">
                    <p>${order.carClass}: ${order.carModel}, цвет ${order.carColor}</p>
                    <p>Номер машины: ${order.carNumber}</p>
                    <p>Водитель: ${order.driverName}</p>
                    <p>Цена поездки: ${order.price} руб</p>
                    <p>Статус поездки: ${order.status}</p>
                    <p>Длина поездки: ${order.tripLength} км</p>
                    <p>Начало поездки: ${order.startTime}</p>
                    <p>Конец поездки: ${order.endTime}</p>
                    <p>Заказчик: ${order.passengerFullName}</p>
                </div>
                <a class="effect-hover" href="/SpringApplication/taxi-service">Вернуться назад</a>
            </div>
            <p class="date-time">Время: ${time}. Дата: ${date}</p>
        </div>
    </body>
</html>