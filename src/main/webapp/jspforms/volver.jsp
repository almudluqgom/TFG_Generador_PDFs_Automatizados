<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Insert title here</title>
</head>
<body>
<%
    String carpeta = (String)session.getAttribute("carpeta");
%>
Tu carpeta nueva es <%= carpeta %>

<p><a href="htmlforms/CargarFichero.html">Irse a los PDFs</a>
<p><a href="htmlforms/CargarCarpeta.html">Meter carpeta de nuevo</a>
</body>
</html>