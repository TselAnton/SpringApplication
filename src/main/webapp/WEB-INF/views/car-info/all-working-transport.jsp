<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
    <style>
        <%@ include file='css/rolling-output-style.css' %>
    </style>
    <head>
        <title>Автопарк</title>
        <meta charset="utf-8" />
    </head>
    <body>
        <div class="car-park">
            <h1 class="heading">Информация о транспорте</h1>
            <div class="services">
                <p>Работающий транспорт</p>
                <c:forEach var="t" items="${transports}">
                    <div class="order-description">
                        <p>Тип - ${t.type}</p>
                        <p>Номер машины: ${t.number}</p>
                        <p>Модель машины: ${t.model}, цвет ${t.color}</p>
                        <p>Средняя скорость: ${t.avrSpeed} км/ч</p>
                        <p>Средний расход топлива: ${t.fuelPerKil} литр/км</p>
                    </div>
                </c:forEach>
                <a class="effect-hover" href="/SpringApplication/car-info">Вернуться назад</a>
            </div>
            <p class="date-time">Время: ${time}. Дата: ${date}</p>
        </div>
    </body>
</html>