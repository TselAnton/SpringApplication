<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
    <style>
        <%@ include file='css/style.css' %>
    </style>
    <head>
        <title>Автопарк</title>
        <meta charset="utf-8" />
    </head>
    <body>
        <div class="car-park">
            <h1 class="heading">Заработок автопарка</h1>
            <div class="services">
                <p>Расчёт по общественному транспорту</p>
                <div class="order-description">
                    <p>Прибыль: ${publicProfit} ₽</p>
                    <p>Расходы: ${publicExpenses} ₽</p>
                </div>
            </div>
            <div class="services">
                <p>Расчёт по такси</p>
                <div class="order-description">
                    <p>Прибыль: ${taxiProfit} ₽</p>
                    <p>Расходы: ${taxiExpenses} ₽</p>
                </div>
            </div>
            <div class="services-last">
                <p>Итого</p>
                <div class="order-description">
                    <p>Суммарная прибыль: ${sumProfit} ₽</p>
                    <p>Суммарные расходы: ${sumExpenses} ₽</p>
                    <p>Итоговая прибыль: ${sumAll} ₽</p>
                </div>
                <a class="effect-hover" href="/SpringApplication">Вернуться назад</a>
            </div>
            <p class="date-time">Время: ${time}. Дата: ${date}</p>
        </div>
    </body>
</html>