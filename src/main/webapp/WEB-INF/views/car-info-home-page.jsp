<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Автопарк</title>
		<h1>Информация о машинах</h1>
	</head>
    <body>
        <p><strong>Программное время: ${time}</strong></p>
		<br>
		<strong><h2>Доступные сервисы:</h2></strong>
		<ul>
			<li><h3><a href="#">Получить информацию о всём работающим транспорте в автопарке</a></h3></li>
			<li><h3><a href="#">Получить информацию о всём работающим общественном транспорте</a></h3></li>
			<li><h3><a href="#">Получить информацию о всех работающих такси</a></h3></li>
			<li><h3><a href="#">Получить информацию о всём транспорте в автопарке</a></h3></li>
			<li><h3><a href="/SpringApplication">Назад</a></h3></li>
		</ul>
    </body>
</html>