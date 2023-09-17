var tabledata;
listado();



function listado() {
  tabledata = new DataTable("#tabla", {
    pageLength: 5,
    lengthMenu: [
      [5, 10, 20, -1],
      [5, 10, 20, "Todos"],
    ],
    scrollX: true,
    scrollY: true,
    language: {
      url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json",
    },
    ajax: {
      url: "./PrestamoServlet?type=listarUsuario&idUsuario="+idUsuario,
      type: "GET",
      dataType: "json",
    },
    columns: [
      { data: "idPrestamo" },
      { data: "fechaPrestamo" },
      { data: "fechaVencimiento" },
      { data: "fechaEntrega"},
      { data: "estadoPrestamo", render: function(valor){
		  if(valor.idEstado == 1){
			 return `<span class="badge text-bg-warning">${valor.descripcion}</span>`;
		  }
		  else if(valor.idEstado == 2){
			 return `<span class="badge text-bg-success">${valor.descripcion}</span>`;
		  }
		  else if(valor.idEstado == 3){
			  return `<span class="badge text-bg-danger">${valor.descripcion}</span>`;
		  }
		  else if(valor.idEstado == 4){
			  return `<span class="badge text-bg-secondary">${valor.descripcion}</span>`;
		  }
	  	} 
	  },
      { data: "usuario", render: function(valor){
		return `<button type="button" class="btn btn-light" data-bs-placement="top" data-bs-toggle="popover" data-bs-trigger="hover focus"
			    data-bs-title="Usuario: ${valor.idUsuario}"
			    data-bs-content="
			    <strong>Nombres: </strong> ${valor.nombres}<br>
			    <strong>Apellidos: </strong> ${valor.apellidos}<br>
			    <strong>Correo: </strong> ${valor.email}
			    " data-bs-html="true">
    			${valor.idUsuario}
 				</button>`; 
	  	} 
	  },
      { data: "libro", render: function(valor){
		 return `<button type="button" class="btn btn-light" data-bs-placement="top" data-bs-toggle="popover" data-bs-trigger="hover focus"
			    data-bs-title="Libro: ${valor.idLibro}"
			    data-bs-content="
			    <strong>Titulo: </strong> ${valor.titulo}<br>
			    <strong>Categoría: </strong> ${valor.categoria.nombre}
			    " data-bs-html="true">
    			${valor.idLibro}
 				</button>`;  
	  	}
	  }
      
    ],
    drawCallback: function() {
      $('[data-bs-toggle="popover"]').popover();
    }
    
  });
}