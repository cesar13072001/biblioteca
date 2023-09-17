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
<h1>Bienvenido de vuelta:</h1>
<h3><%=usuario.getApellidos() + ", " + usuario.getNombres() %></h3>
<br>
<h4>Mis préstamos</h4>
<table class="table" id="tabla" style="width: 100%;">
	<thead>
		<tr style="width:100%">
			<th>Id</th>
	        <th>Fecha prestamo</th>
	        <th>Fecha Vencimiento</th>
	        <th>Fecha Entrega</th>
	        <th>Estado</th>
	        <th>Usuario</th>
	        <th>Libro</th>
	     </tr>
	</thead>
</table>





</div>	
<%@ include file="/include/footer.jsp" %>
<script src="./js/alerta.js"></script>
<script>
var idUsuario = <%= usuario.getIdUsuario()%>;
</script>
<script src="./js/index.js"></script>
<script src="./js/bootstrap-validator.js"></script>
</body>
</html>