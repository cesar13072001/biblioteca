var usuario;
var libro;






listado();



function formularioUsuario(){
	var idUsuario = $("#idUsuario").val();
	var nombres = $("#nombres");
	buscarUsuario(idUsuario);
	if(usuario == null){
		mostrarAlerta(2,"El usuario no fue encontrado");
		formUsuarioInvalido();
	}
	else if(consultaDeuda(idUsuario) != 0){
		mostrarAlerta(1,"El usuario tiene prestamos pendientes");
		formUsuarioInvalido();
	}
	else if(usuario.estado == true){
		nombres.val(`${usuario.apellidos}, ${usuario.nombres}`);
		$("#div_nombres").attr("hidden", false);
		$("#form_libro").attr("hidden",false);
		
	}
	else if(usuario.estado == false){
		mostrarAlerta(1,"El usuario esta inhabilitado");
		formUsuarioInvalido();
	}
}

listado2();

function listado2() {
  $.ajax({
    url: "./PrestamoServlet?type=listar",
    type: "GET",
    dataType: "json",
    //async: false,
    success: function (data) {
	  console.log(data);
    },
    error: function (error) {
      console.log(error);
    },
    beforeSend: function () {},
  });
}





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
      url: "./PrestamoServlet?type=listar",
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
	  },
      {
        defaultContent:
          '<button type="button" title="Entregar" class="btn btn-primary btn-entregar"><i class="fa-solid fa-book-bookmark"></i></button> ',
        orderable: false,
        searchable: false,
        width: "50px",
      }
    ],
    drawCallback: function() {
      $('[data-bs-toggle="popover"]').popover();
    }
    
  });
}




function formUsuarioInvalido(){
		var nombres = $("#nombres");
		nombres.val("");
		$("#div_nombres").attr("hidden", true);
		$("#form_libro").attr("hidden",true);
		$("#form_libro")[0].reset();
		$("#div_titulo").attr("hidden", true);
		$("#div_disponible").attr("hidden", true);	
		$("#formularioPrestamo").attr("hidden", true);
		$("#btn_formulario").attr("disabled", true);
		$("#formularioPrestamo")[0].reset();
}


function buscarUsuario(id) {
  $.ajax({
    url: "./UsuarioServlet?type=buscar",
    data: { id: id },
    type: "POST",
    dataType: "json",
    async: false,
    success: function (data) {
	  console.log(data);
      usuario = data;
    },
    error: function (error) {
      console.log(error);
    },
    beforeSend: function () {},
  });
}

function consultaDeuda(id) {
  var salida;
  $.ajax({
    url: "./PrestamoServlet?type=consultaDeuda",
    data: { idUsuario: id },
    type: "POST",
    dataType: "json",
    async: false,
    success: function (data) {
	  console.log(data);
      salida = data.resultado;
    },
    error: function (error) {
      console.log(error);
    },
    beforeSend: function () {},
  });
  return salida;
}


function formularioLibro(){
	var idLibro = $("#idLibro").val();
	var titulo = $("#titulo");
	var disponible = $("#disponible");
	buscarLibro(idLibro);
	if(libro == null){
		mostrarAlerta(2,"El libro no fue encontrado");
		titulo.val("");
		disponible.val("");
		$("#div_titulo").attr("hidden", true);
		$("#div_disponible").attr("hidden", true);
		$("#btn_formulario").attr("disabled", true);
		$("#formularioPrestamo").attr("hidden", true);
	}
	else if(libro.estado == true){
		titulo.val(libro.titulo);
		disponible.val(libro.cantidad);
		$("#div_titulo").attr("hidden", false);
		$("#div_disponible").attr("hidden", false);
		if(libro.cantidad != 0){
			$("#formularioPrestamo").removeClass("was-validated");
			$("#formularioPrestamo").attr("hidden", false);
			$("#btn_formulario").attr("disabled", false);	
		}
		else{
			$("#btn_formulario").attr("disabled", true);
			mostrarAlerta(1,"El libro no tiene existencias");
		}
	}
	else if(libro.estado == false){
		mostrarAlerta(1,"El libro esta inhabilitado");
		titulo.val("");
		disponible.val("");
		$("#div_titulo").attr("hidden", true);
		$("#div_disponible").attr("hidden", true);
		$("#btn_formulario").attr("disabled", true);
		$("#formularioPrestamo").attr("hidden", true);
	
	}
}


function buscarLibro(id) {
  $.ajax({
    url: "./LibroServlet?type=buscar",
    data: { idLibro: id },
    type: "POST",
    dataType: "json",
    async: false,
    success: function (data) {
	  console.log(data);
      libro = data;
    },
    error: function (error) {
      console.log(error);
    },
    beforeSend: function () {},
  });
}


function accionFormulario(){
	
	var datos = {
		idUsuario : usuario.idUsuario,
		idLibro : libro.idLibro,
		dias : $("#dias").val()
	}
	
	$.ajax({
    url: "./PrestamoServlet?type=reserva",
    data: datos,
    type: "POST",
    dataType: "json",
    async: false,
    success: function (data) {
	  console.log(data);
    },
    error: function (error) {
      console.log(error);
    },
    beforeSend: function () {},
  });
	
}



$("#staticBackdrop").on("hidden.bs.modal", function () {
  $("#form_libro")[0].reset();
  $("#form_usuario")[0].reset();
  $("#formularioPrestamo")[0].reset();
  
  $("#div_nombres").attr("hidden", true);
  $("#form_libro").attr("hidden",true);
  $("#div_titulo").attr("hidden", true);
  $("#div_disponible").attr("hidden", true);
  $("#formularioPrestamo").attr("hidden", true);
  $("#formularioPrestamo").removeClass("was-validated");
  usuario = null;
  libro = null;
});


