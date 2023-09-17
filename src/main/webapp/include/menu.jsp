<%@page import="entity.Usuario"%>
<% Usuario usuario = (Usuario)session.getAttribute("usuario"); %>


	
<!-- Modal -->
<div class="modal fade" id="modal_user" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="modal_user" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5">Usuario</h1>
	      </div>
	      	<div class="modal-body row g-3">
	      
					<div class="col-12">
						<label class="form-label" for="m_nombres">Nombres</label>
						<input class="form-control" type="text" id="m_nombres" readonly="readonly" value="<%=usuario.getNombres() %>">
						
					</div>
					
					<div class="col-12">
						<label class="form-label" for="m_apellidos">Apellidos</label>
						<input class="form-control" type="text" id="m_apellidos" readonly="readonly" value="<%=usuario.getApellidos() %>">
						
					</div>
					
					<div class="col-12">
						<label class="form-label" for="m_fecha">Fecha de nacimiento</label>
						<input class="form-control" type="date" id="m_fecha" readonly="readonly" value="<%=usuario.getFechaNacimiento() %>">
						
					</div>
					
					
					<div class="col-12">
						<label class="form-label" for="m_dni">DNI</label>
						<input class="form-control" type="text" id="m_dni" readonly="readonly" value="<%=usuario.getIdUsuario() %>">
						
					</div>
					
		
					<div class="col-12">
						<label class="form-label" for="m_correo">Correo</label>
						<input class="form-control" type="email" id="m_correo" readonly="readonly" value="<%=usuario.getEmail() %>">
						
					</div>
		    
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button> 
	      </div>
	    </div>
	  </div>
	</div>
	
	






<nav
  class="sb-topnav navbar navbar-expand-sm sticky-top"
  style="background-color: #e3f2fd"
>
  <div class="container-fluid">
    <a class="navbar-brand px-1" href="./">
    	<i><img height="40" src="./image/icono.ico" /></i> Biblioteca
    </a>
    <button
      class="navbar-toggler"
      type="button"
      data-bs-toggle="collapse"
      data-bs-target="#navbarNavAltMarkup"
      aria-controls="navbarNavAltMarkup"
      aria-expanded="false"
      aria-label="Toggle navigation"
    >
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
      <ul class="navbar-nav">
      	<% if(usuario.getIdRol() == 1){ %>
        <li class="nav-item">
          <a class="nav-link" href="./usuarios.jsp">Usuarios</a>
        </li>
         <li class="nav-item">
          <a class="nav-link" href="./categorias.jsp">Categorías</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="./libros.jsp">Libros</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="./prestamos.jsp">Prestamos</a>
        </li>
        <%}%>
      </ul>

      <ul class="navbar-nav ms-auto me-0 me-md-3 my-2 my-md-0">
        <li class="nav-item dropdown">
          <a
            class="nav-link dropdown-toggle"
            id="navbarDropdown"
            href="#"
            role="button"
            data-bs-toggle="dropdown"
            aria-expanded="false"
            ><i class="fas fa-user fa-fw"></i
          ></a>
          <ul
            class="dropdown-menu dropdown-menu-end"
            aria-labelledby="navbarDropdown"
          >
            <li>
              <h6 class="dropdown-item">
              <%= usuario.getEmail() %>
              </h6>
            </li>
            <li><a class="dropdown-item" role="button" data-bs-toggle="modal" data-bs-target="#modal_user">Mi cuenta</a></li>
            <li><hr class="dropdown-divider"></li>
            <li><a class="dropdown-item" href="#" onclick="cerrarSesion()">Cerrar sesión</a></li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</nav>
<script>
	function cerrarSesion(){
		$.ajax({
		    url: "./AuthServlet?type=logout",
		    type: "GET",
		    dataType: "json",
		    contentType: "application/json; charset=utf-8",
		    async: false,
		    success: function (data) {
		    	if(data != null)
		    	window.location.replace("./"); 
		    },
		    error: function (error) {
		      console.log(error);
		    },
		    beforeSend: function () {},
		  });
	}
</script>
