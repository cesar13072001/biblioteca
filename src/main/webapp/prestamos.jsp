<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Prestamos</title>

<%@ include file="/include/header.jsp" %>
<%@ include file="/include/dataTable.jsp" %>

</head>
<body class="bg-body-tertiary">

<%@ include file="/include/menu.jsp" %>

<div class="bg-white m-lg-2 p-lg-3 m-1 p-2 shadow-lg rounded-2" id="contenedor">
<%@ include file="include/alerta.jsp" %>
<h1>Prestamos</h1>
<ol class="breadcrumb">
	<li class="breadcrumb-item"><a href="./">Biblioteca</a></li>
    <li class="breadcrumb-item active">Prestamos</li>
</ol>


<button type="button" class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
<i class="fa-solid fa-book-bookmark"></i> Reservar
</button>
	
	
<!-- Modal -->
<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5" id="tituloModal">Reservar libro</h1>
	      </div>
	      	<div class="modal-body">
	      <form class="row g-2" action="javascript:formularioUsuario()" id="form_usuario">
					<div class="col-12">
						<label class="form-label" for="nombres">Id Usuario</label>
						<div class="input-group">
							<input class="form-control" type="text" id="idUsuario" required autocomplete="off">
							<button class="btn btn-outline-secondary" type="submit" id="btn_buscarUsuario"><i class="fa-solid fa-magnifying-glass"></i></button>
						</div>
						<div class="invalid-feedback">
			        	Ingrese el Id del usuario
			      		</div>
					</div>
					
					
					
					<div class="col-12" id="div_nombres" hidden>
						<label class="form-label" for="nombres">Nombres</label>
						<input class="form-control" type="text" id="nombres" disabled>
					</div>
					
		  </form>	
		  

		  <form class="row g-2 mt-1" action="javascript:formularioLibro()" id="form_libro" hidden>
					<div class="col-12">
						<label class="form-label" for="idLibro">ISBN</label>
						<div class="input-group">
							<input class="form-control" type="text" id="idLibro" required autocomplete="off">
							<button class="btn btn-outline-secondary" type="submit" id="btn_buscarUsuario"><i class="fa-solid fa-magnifying-glass"></i></button>
						</div>
						<div class="invalid-feedback">
			        	Ingrese el ISBN del libro
			      		</div>
					</div>
					
					

					<div class="col-8" id="div_titulo" hidden>
						<label class="form-label" for="titulo">Titulo</label>
						<input class="form-control" type="text" id="titulo" disabled>
					</div>
					<div class="col-4" id="div_disponible" hidden>
						<label class="form-label" for="cantidad">Disponible</label>
						<input class="form-control" type="text" id="disponible" disabled>
					</div>
		  </form>	
		  
		  	
		  <form class="needs-validation g-3 mt-3" id="formularioPrestamo" hidden novalidate action="javascript:accionFormulario()">
		    	<div class="col-12">
						<label class="form-label" for="dias">Días de prestamo</label>
						<select class="form-select" id="dias" required>
  							<option selected value="">Seleccione</option>
  							<option value="1">1 día</option>
  							<option value="2">2 días</option>
  							<option value="3">3 días</option>
						</select>
						<div class="invalid-feedback">
			        	Seleccione una opción
			      		</div>
					</div>
		    
		  </form>  
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" id="btn_cerrar" data-bs-dismiss="modal">Cancelar</button>
	        <button type="submit" form="formularioPrestamo" class="btn btn-primary" id="btn_formulario" disabled>
	        Reservar</button>  
	     
	         
	      </div>
	    </div>
	  </div>
	</div>


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
	        <th>Acción</th>
	     </tr>
	</thead>
</table>




</div>	

<%@ include file="/include/footer.jsp" %>
<script src="./js/alerta.js"></script>
<script src="./js/prestamos.js"></script>
<script src="./js/bootstrap-validator.js"></script>
</body>
</html>