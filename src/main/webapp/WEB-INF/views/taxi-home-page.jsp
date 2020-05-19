<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Автопарк</title>
		<h1>Такси</h1>
	</head>
    <body>
        <p><strong>Программное время: ${time}</strong></p>
		<br>
		<strong><h2>Доступные сервисы:</h2></strong>
		<ul>
			<li><h3><a href="#">Заказать такси</a></h3></li>
			<li><h3><a href="#">Узнать статус заказа</a></h3></li>
			<li><h3><a href="#">Отменить заказ</a></h3></li>
			<li><h3><a href="/SpringApplication">Назад</a></h3></li>
		</ul>
    </body>
</html>