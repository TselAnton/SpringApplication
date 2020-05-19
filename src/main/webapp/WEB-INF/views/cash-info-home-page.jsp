<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Автопарк</title>
		<h1>Зарабаток автопарка</h1>
	</head>
    <body>
        <p><strong>Программное время: ${time}</strong></p>
		<br>
		<strong><h2>Доступные сервисы:</h2></strong>
		<ul>
			<li><h3><a href="#">Получить доход по определённому типу транспорта за период</a></h3></li>
			<li><h3><a href="#">Получить доход по всем типам транспорта за период</a></h3></li>
			<li><h3><a href="/SpringApplication">Назад</a></h3></li>
		</ul>
    </body>
</html>