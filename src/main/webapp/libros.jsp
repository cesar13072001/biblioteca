<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Libros</title>

<%@ include file="/include/header.jsp" %>
<%@ include file="/include/dataTable.jsp" %>

</head>
<body class="bg-body-tertiary">

<%@ include file="/include/menu.jsp" %>

<div class="bg-white m-lg-2 p-lg-3 m-1 p-2 shadow-lg rounded-2" id="contenedor">
<%@ include file="include/alerta.jsp" %>
<h1>Libros</h1>
<ol class="breadcrumb">
	<li class="breadcrumb-item"><a href="./">Biblioteca</a></li>
    <li class="breadcrumb-item active">Libros</li>
</ol>



<button type="button" class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
<i class="fa-solid fa-book"></i> Agregar
</button>

<a class="btn btn-secondary mb-3" href="./ReporteSerlvet?categoria=libro" target="_blank">
<i class="fa-solid fa-file-pdf"></i> Reporte
</a>
	
	
<!-- Modal -->
<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5" id="tituloModal">Agregar categoría</h1>
	      </div>
	      <form class="needs-validation" id="formularioLibro" novalidate action="javascript:accionFormulario()">
	      	<div class="modal-body row g-3">
	      	<input type="hidden" id="isbn" value="0">
	      		<div class="col-12" id="div_idLibro">
						<label class="form-label" for="idLibro">ISBN</label>
						<input class="form-control" type="text" id="idLibro" autocomplete="off" required>
						<div class="invalid-feedback">
			        	Ingrese el ISBN
			      		</div>
					</div>
				<!-- 
				 <div class="col-6">
				 	<label class="form-label" for="idLibro">Cantidad</label>	 	
					<div class="input-group mb-3">
					  <button class="btn btn-outline-secondary" type="button" id="button-addon2"><i class="fa-solid fa-minus"></i></button>
					  <input type="text" class="form-control" placeholder="Recipient's username" aria-label="Recipient's username" aria-describedby="button-addon2">
					 <button class="btn btn-outline-secondary" type="button" id="button-addon2"><i class="fa-solid fa-plus"></i></button>
					
					</div>
				</div>
				<div class="col-6 mt-5 text-center">
				<button class="btn btn-primary">Actualizar</button>
				</div>
	       -->	
					<div class="col-12">
						<label class="form-label" for="titulo">Titulo</label>
						<input class="form-control" type="text" id="titulo" autocomplete="off" required>
						<div class="invalid-feedback">
			        	Ingrese un titulo
			      		</div>
					</div>
					<div class="col-12">
						<label class="form-label" for="descripcion">Descripción</label>
						<textarea class="form-control" id="descripcion" rows="3" required></textarea>
						<div class="invalid-feedback">
			        	Ingrese una descripción
			      		</div>
					</div>
					<div class="col-4">
						<label class="form-label" for="cantidad">Cantidad</label>
						<input class="form-control" type="number" id="cantidad" min="1" autocomplete="off" required>
						<div class="invalid-feedback">
			        	Ingrese una cantidad
			      		</div>
					</div>	
					<div class="col-8">
						<label class="form-label" for="idCategoria">Categoria</label>
						<select class="form-select" id="idCategoria" required>
						  <option value ="" selected>Seleccione</option>
						  <option value="1">One</option>
						  <option value="2">Two</option>
						  <option value="3">Three</option>
						</select>
						<div class="invalid-feedback">
			        	Seleccione una categoría
			      		</div>
					</div>
		    
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" id="btn_cerrar" data-bs-dismiss="modal">Cancelar</button>
	        <button type="submit" class="btn btn-primary" id="btn_formulario">
	        Registrar</button>   
	      </div>
	      </form>
	    </div>
	  </div>
	</div>
	



<table class="table" id="tabla" style="width: 100%;">
	<thead>
		<tr style="width:100%">
			<th>Id Libro</th>
	        <th>Titulo</th>
	        <th>Descripción</th>    
	        <th>Cantidad</th>
	        <th>Categoría</th>
	        <th>Estado</th>
	        <th>Acción</th>    
	        <th></th>                    
	     </tr>
	</thead>
</table>



</div>	
<%@ include file="/include/footer.jsp" %>
<script src="./js/alerta.js"></script>
<script src="./js/libros.js"></script>
<script src="./js/bootstrap-validator.js"></script>
</body>
</html>