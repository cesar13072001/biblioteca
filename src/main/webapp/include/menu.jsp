<%@page import="entity.Usuario"%>
<% Usuario usuario = (Usuario)session.getAttribute("usuario"); %>

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
        <li class="nav-item">
          <a class="nav-link" href="./reportes.jsp">Reportes</a>
        </li>
        <%} %>
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
              <h6 class="dropdown-item" id="txtCorreo">
                <%= usuario.getApellidos() +", "+usuario.getNombres() %>
              </h6>
            </li>
            <li><a class="dropdown-item">Mi cuenta</a></li>
            <li><hr class="dropdown-divider" /></li>
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
