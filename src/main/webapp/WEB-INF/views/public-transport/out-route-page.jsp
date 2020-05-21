<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
    <style>
        <%@ include file='css/output-route-style.css' %>
    </style>
    <head>
        <title>Автопарк</title>
        <meta charset="utf-8" />
    </head>
    <body>
        <div class="car-park">
            <h1 class="heading">Общественный транспорт</h1>
            <div class="services">
                <p>Информация о маршруте #${routeNumb}</p>
                <c:forEach var="route" items="${routes}">
                    <div class="order-description">
                        <p>Дата: ${route.date}</p>
                        <p>Количество пассажиров за день: ${route.numOfPassengers} человек(-а)</p>
                        <p>Пройденный путь: ${route.pathLength} км</p>
                        <p>${route.transport.transportType}: ${route.transport.carModel}, цвет ${route.transport.carColor}</p>
                        <p>Номер машины: ${route.transport.carNumber}</p>
                        <p>Количество мест: ${route.transport.numberOfSeats}</p>
                        <p>Стоимость проезда: ${route.transport.costByTicket} руб</p>
                    </div>
                </c:forEach>
                <a class="effect-hover" href="/SpringApplication/public-transport-service">Вернуться назад</a>
            </div>
            <p class="date-time">Время: ${time}. Дата: ${date}</p>
        </div>
    </body>
</html>