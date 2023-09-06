<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Usuarios</title>

<%@ include file="/include/header.jsp" %>
<%@ include file="/include/dataTable.jsp" %>

</head>
<body class="bg-body-tertiary">

<%@ include file="/include/menu.jsp" %>

<div class="bg-white m-lg-2 p-lg-3 m-1 p-2 shadow-lg rounded-2" id="contenedor">
<%@ include file="include/alerta.jsp" %>
<h1>Usuarios</h1>
<ol class="breadcrumb">
	<li class="breadcrumb-item"><a href="/dashboard">Biblioteca</a></li>
    <li class="breadcrumb-item active">Usuarios</li>
</ol>




<button type="button" class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
Registrar
</button>
	
	
<!-- Modal -->
<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5" id="tituloModal">Agregar usuario</h1>
	      </div>
	      <form class="needs-validation" id="formularioUsuario" novalidate action="javascript:accionFormulario()">
	      	<div class="modal-body row g-3">
	      
	        	<input type="hidden" id="idUsuario" value="0">
					<div class="col-12">
						<label class="form-label" for="nombres">Nombres</label>
						<input class="form-control" type="text" id="nombres" required>
						<div class="invalid-feedback">
			        	Ingrese los nombres
			      		</div>
					</div>
					
					<div class="col-12">
						<label class="form-label" for="apellidos">Apellidos</label>
						<input class="form-control" type="text" id="apellidos" required>
						<div class="invalid-feedback">
			        	Ingrese los apellidos
			      		</div>
					</div>
					
					<div class="col-12">
						<label class="form-label" for="fecha">Fecha de nacimiento</label>
						<input class="form-control" type="date" id="fecha" required>
						<div class="invalid-feedback">
			        	Ingrese la fecha de nacimiento
			      		</div>
					</div>
					
					
					<div class="col-6" id="div_dni">
						<label class="form-label" for="dni">DNI</label>
						<input class="form-control" type="text" id="dni" required>
						<div class="invalid-feedback">
			        	Ingrese un documento de identidad
			      		</div>
					</div>
					
					
					<div class="col-6" id="div_c_correo">
						<label class="form-label" for="consulta">¿Tiene correo?</label>
						<select class="form-select" id="consulta" required onchange="habilitarInputCorreo()">
				      		<option value="">Seleccione</option>
				      		<option value="1">Si</option>
				      		<option value="2">No</option>
			    		</select>
						
						<div class="invalid-feedback">
			        	Seleccione una opción
			      		</div>
					</div>
					
					
					<div class="col-12" id="div_correo" hidden="true">
						<label class="form-label" for="correo">Correo</label>
						<input class="form-control" type="email" id="correo">
						<div class="invalid-feedback">
			        	Formato incorrecto
			      		</div>
					</div>
		    
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" id="btn_cerrar" data-bs-dismiss="modal">Cancelar</button>
	        <button type="submit" class="btn btn-primary" id="btn_formulario">
	        <!-- 
	        <div class="spinner-border spinner-border-sm" role="status">
  			<span class="visually-hidden">Loading...</span>
			</div>
			-->
	        Registrar</button>   
	      </div>
	      </form>
	    </div>
	  </div>
	</div>
	
	
	
<table class="table" id="tabla" style="width: 100%;">
	<thead>
		<tr style="width:100%">
			<th>IdUsuario</th>
	        <th>Nombres</th>
	        <th>Apellidos</th>
	        <th>Fecha Nacimiento</th>
	        <th>Email</th>
	        <th>Registro</th>
	        <th>Estado</th>
	        <th>Rol</th>
	        <th>Acción</th>
	        <th></th>            
	     </tr>
	</thead>
</table>
	
	
</div>		
<%@ include file="/include/footer.jsp" %>
<script src="./js/alerta.js"></script>
<script src="./js/usuarios.js"></script>
<script src="./js/bootstrap-validator.js"></script>
</body>
</html>