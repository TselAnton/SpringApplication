<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
    <style>
        <%@ include file='css/output-style.css' %>
    </style>
    <head>
        <title>Автопарк</title>
        <meta charset="utf-8" />
    </head>
    <body>
        <div class="car-park">
            <h1 class="heading">Общественный транспорт</h1>
            <div class="services">
                <p>${transport.transportType} #${transport.routeNumber}</p>
                <div class="order-description">
                    <p>${transport.transportType}: ${transport.carModel}, цвет ${transport.carColor}</p>
                    <p>Номер машины: ${transport.carNumber}</p>
                    <p>Количество мест: ${transport.numberOfSeats}</p>
                    <p>Стоимость проезда: ${transport.costByTicket} руб</p>
                    <p>Маршрут: ${transport.routeList}</p>
                    <p>Текущее местоположение: ${transport.currentLocation}</p>
                    <p>Время начала работы маршрута: ${transport.routeStartTime}</p>
                    <p>Время конца работы маршрута: ${transport.routeEndTime}</p>
                </div>
                <a class="effect-hover" href="/SpringApplication/public-transport-service">Вернуться назад</a>
            </div>
            <p class="date-time">Время: ${time}. Дата: ${date}</p>
        </div>
    </body>
</html>