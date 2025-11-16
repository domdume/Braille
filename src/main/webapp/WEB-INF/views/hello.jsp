<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8"/>
    <title>Hola MVC</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 2rem; }
        form { margin-top: 1rem; }
        input[type=text] { padding: .4rem; }
        button { padding: .4rem .8rem; }
    </style>
</head>
<body>
<h1>${greeting.message}</h1>
<form method="get" action="${pageContext.request.contextPath}/hello">
    <label>Tu nombre: <input type="text" name="name" placeholder="Escribe tu nombre"/></label>
    <button type="submit">Saludar</button>
</form>
<p>
    <a href="${pageContext.request.contextPath}/">Volver al inicio</a>
</p>
</body>
</html>

