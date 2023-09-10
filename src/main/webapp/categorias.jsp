<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Categorías</title>

<%@ include file="/include/header.jsp" %>
<%@ include file="/include/dataTable.jsp" %>

</head>
<body class="bg-body-tertiary">

<%@ include file="/include/menu.jsp" %>

<div class="bg-white m-lg-2 p-lg-3 m-1 p-2 shadow-lg rounded-2" id="contenedor">
<%@ include file="include/alerta.jsp" %>
<h1>Categorías</h1>
<ol class="breadcrumb">
	<li class="breadcrumb-item"><a href="./">Biblioteca</a></li>
    <li class="breadcrumb-item active">Categorías</li>
</ol>


<button type="button" class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
<i class="fa-solid fa-book-open-reader"></i> Agregar
</button>
	
	
<!-- Modal -->
<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5" id="tituloModal">Agregar categoría</h1>
	      </div>
	      <form class="needs-validation" id="formularioCategoria" novalidate action="javascript:accionFormulario()">
	      	<div class="modal-body row g-3">
	      
	        	<input type="hidden" id="idCategoria" value="0">
					<div class="col-12">
						<label class="form-label" for="nombres">Nombres</label>
						<input class="form-control" type="text" id="nombres" required autocomplete="off">
						<div class="invalid-feedback">
			        	Ingrese un nombre
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
			<th>Id categoría</th>
	        <th>Nombres</th>
	        <th>Acción</th>
	        <th></th>            
	     </tr>
	</thead>
</table>


</div>	
<%@ include file="/include/footer.jsp" %>
<script src="./js/alerta.js"></script>
<script src="./js/bootstrap-validator.js"></script>
<script src="./js/categorias.js"></script>
</body>
</html>