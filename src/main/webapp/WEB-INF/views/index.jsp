<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Автопарк</title>
		<h1>Автопарк</h1>
	</head>
    <body>
        <p><strong>Программное время: ${time}</strong></p>
		<br>
		<strong><h2>Доступные сервисы:</h2></strong>
		<ul>
			<li><h3><a href="taxi-service">Такси</a></h3></li>
			<li><h3><a href="public-transport-service">Общественный транспорт</a></h3></li>
		</ul>
		<br>
		<strong><h2>Управление автопарком:</h2></strong>
		<ul>
			<li><h3><a href="car-info">Информация о машинах</a></h3></li>
			<li><h3><a href="cash-info">Зарабаток автопарка</a></h3></li>
		</ul>
    </body>
</html>