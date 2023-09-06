<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Biblioteca</title>

<%@ include file="/include/header.jsp" %>
<%@ include file="/include/dataTable.jsp" %>

</head>
<body class="bg-body-tertiary">

<%@ include file="/include/menu.jsp" %>

<div class="bg-white m-lg-2 p-lg-3 m-1 p-2 shadow-lg rounded-2" id="contenedor">
<%@ include file="include/alerta.jsp" %>
<h1>Bienvenido de vuelta</h1>
<ol class="breadcrumb">
	<li class="breadcrumb-item"><a href="/dashboard">Biblioteca</a></li>
    <li class="breadcrumb-item active">Categorías</li>
</ol>




</div>	
<%@ include file="/include/footer.jsp" %>
<script src="./js/alerta.js"></script>
<script src="./js/usuarios.js"></script>
<script src="./js/bootstrap-validator.js"></script>
</body>
</html>