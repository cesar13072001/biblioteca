var tabledata;
var fila;
var usuario;

listado();

function habilitarInputCorreo() {
  if ($("#consulta").val() === "1") {
    $("#div_correo").attr("hidden", false);
  } else {
    $("#div_correo").attr("hidden", true);
  }
}

function accionFormulario() {
  var idUsuario = $("#idUsuario").val();
  if (idUsuario != "0") {
    editarUsuario();
  } else {
    agregarUsuario();
  }
}

function agregarUsuario() {
  var datos = {
    nombres: $("#nombres").val(),
    apellidos: $("#apellidos").val(),
    fecha: $("#fecha").val(),
    dni: $("#dni").val(),
    correo: $("#correo").val()
  };

  $.ajax({
    type: "POST",
    url: "./UsuarioServlet?type=agregar",
    data: datos,
    dataType: "json",
    success: function (data) {
		console.log(data);
      if (data != null) {
		tabledata.row.add(data).draw(false);
        $("#staticBackdrop").modal("hide");
        mostrarAlerta(0, "Usuario agregado correctamente");
      } else {
        mostrarAlerta(2, "Ocurrio un error al agregar usuario");
      }
      desactivarBotones(true, "Agreg");

    },
    error: function (error) {
      console.log(error);
    },
    beforeSend: function () {
		desactivarBotones(false, "Agreg");
	},
  });
}


function editarUsuario() {
   var datos = {
    nombres: $("#nombres").val(),
    apellidos: $("#apellidos").val(),
    fecha: $("#fecha").val(),
    idUsuario: $("#idUsuario").val(),
    email: $("#correo").val()
   };
 
  $.ajax({
    type: "POST",
    url: "./UsuarioServlet?type=editar",
    data: datos,
    dataType: "json",
    success: function (data) {
      if (data.resultado == 1) {
		  usuario.nombres = datos.nombres;
		  usuario.apellidos = datos.apellidos;
		  usuario.fechaNacimiento = datos.fecha;
		  usuario.email = datos.email;
		  
        $("#staticBackdrop").modal("hide");
        mostrarAlerta(0, "Usuario editado correctamente");
        tabledata.row(fila).data(usuario).draw(false);
      } else {
        mostrarAlerta(2, "Ocurrio un error al editar");
      }
      desactivarBotones(true, "Edit");
    },
    error: function (error) {
      console.log(error);
    },
    beforeSend: function () {
		desactivarBotones(false, "Edit");
	},
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
      url: "./UsuarioServlet?type=listar",
      type: "GET",
      dataType: "json",
      error: function(error){
		 return [];
	  }
    },
    columns: [
      { data: "idUsuario" },
      { data: "nombres", width: "400px" },
      { data: "apellidos", width: "400px" },
      { data: "fechaNacimiento", width: "200px" },
      { data: "email" },
      { data: "fechaRegistro", width: "250px" },
      {
        data: "estado",
        width: "50px",
        render: function (valor) {
          if (valor) {
            return '<div class="form-check form-switch"><input title="Activo" class="form-check-input" type="checkbox" role="switch" id="switchActivo" checked></div>';
          } else {
            return '<div class="form-check form-switch"> <input title="Inactivo" class="form-check-input" type="checkbox" role="switch" id="switchActivo"></div>';
          }
        },
      },
      { data: "rol.nombreRol", width: "75px" },
      {
        defaultContent:
          '<button type="button" title="Editar" class="btn btn-success btn-editar"><i class="fas fa-pen"></i></button> ',
        orderable: false,
        searchable: false,
        width: "50px",
      },
      {
        defaultContent:
          '<button type="button" title="Eliminar" class="btn btn-danger btn-eliminar"><i class="fas fa-trash"></i></button>',
        orderable: false,
        searchable: false,
        width: "50px",
      },
    ],
  });
}

function rellenarCampos(idUsuario) {
  $("#tituloModal").html("Editar usuario: " + idUsuario);
  buscarUsuario(idUsuario);

  $("#div_c_correo").attr("hidden", true);
  $("#div_dni").attr("hidden", true);
  $("#div_correo").attr("hidden", false);

  $("#nombres").val(usuario.nombres);
  $("#apellidos").val(usuario.apellidos);
  $("#fecha").val(usuario.fechaNacimiento);
  $("#correo").val(usuario.email);
  $("#idUsuario").val(usuario.idUsuario);

  $("#staticBackdrop").modal("show");
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


function cambiarEstadoUsuario(id, estado) {
  var salida;
  jQuery.ajax({
    url: "./UsuarioServlet?type=editarEstado",
    type: "POST",
    data: { idUsuario: id, estado: estado },
    dataType: "json",
    async: false,
    success: function (data) {
      salida = data.resultado;
    },
    error: function (error) {
      console.log(error);
    },
    beforeSend: function () {},
  });
  console.log(salida);
  return salida;
}


function eliminarUsuario(id) {
  var salida;
  jQuery.ajax({
    url: "./UsuarioServlet?type=eliminar",
    type: "POST",
    data: { idUsuario: id},
    dataType: "json",
    async: false,
    success: function (data) {
      salida = data.resultado;
    },
    error: function (error) {
      console.log(error);
    },
    beforeSend: function () {},
  });
  console.log(salida);
  return salida;
}

function desactivarBotones(estado, accion){
	var botonF = $("#btn_formulario");
	var botonC = $("#btn_cerrar");
	if(estado){
		botonF.html(accion+"ar");
		botonF.attr('disabled', false);
		botonC.attr('disabled', false);

		
	}else{
	    let texto = `<div class="spinner-border spinner-border-sm" role="status">
  			<span class="visually-hidden">Loading...</span>
			</div>` + accion + "ando..";
	    botonF.html(texto);
	    botonF.attr('disabled', true);
		botonC.attr('disabled', true);
	    
	}
}

$("#tabla tbody").on("change", "#switchActivo", function () {
  var filaSeleccionada = $(this).closest("tr");
  let idUsuario = tabledata.row(filaSeleccionada).data()["idUsuario"];
  var checked = this.checked;
  this.checked = !checked;
  console.log(checked);

  var opcion = checked == true ? "activará" : "desactivará";

  var texto = "Esta acción " + opcion + " al usuario, ¿Desea continuar?";

  const swalWithBootstrapButtons = Swal.mixin({
    customClass: {
      confirmButton: "btn btn-success m-1",
      cancelButton: "btn btn-danger m-1",
    },
    buttonsStyling: false,
  });
  swalWithBootstrapButtons
    .fire({
      title: "¿Desea actualizar?",
      text: texto,
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Continuar",
      cancelButtonText: "Cancelar",
      customClass: {
        confirmButton: "btn btn-danger m-1",
        cancelButton: "btn btn-success m-1",
      },
      reverseButtons: true,
    })
    .then((result) => {
      if (result.isConfirmed) {
        var salida = cambiarEstadoUsuario(idUsuario, checked);

        if (salida == 1) {
          this.checked = checked;
          swalWithBootstrapButtons.fire(
            "Actualizado",
            "El estado fue actualizado",
            "success"
          );
        } else {
          Swal.fire({
            icon: "error",
            title: "Error",
            text: "Ocurrio un error al eliminar",
          });
        }
      } else if (result.dismiss === Swal.DismissReason.cancel) {
        this.checked = !checked;
        swalWithBootstrapButtons.fire(
          "Cancelado",
          "Usted canceló la acción",
          "error"
        );
      }
    });
});



$("#tabla tbody").on("click", ".btn-editar", function () {
  var filaSeleccionada = $(this).closest("tr");
  let idUsuario = tabledata.row(filaSeleccionada).data()["idUsuario"];
  fila = filaSeleccionada;
  $("#btn_formulario").html("Editar");
  $("#idProducto").attr("value", idUsuario);
  $("#consulta").attr("required", false);
  $("#dni").attr("required", false);
  rellenarCampos(idUsuario);
});


$("#staticBackdrop").on("hidden.bs.modal", function () {
  $("#formularioUsuario")[0].reset();
  $("#formularioUsuario").removeClass("was-validated");

  $("#div_c_correo").attr("hidden", false);
  $("#div_dni").attr("hidden", false);
  $("#btn_formulario").html("Agregar");
  $("#idUsuario").val(0);

  $("#consulta").attr("required", true);
  $("#dni").attr("required", true);

  $("#tituloModal").html("Agregar usuario");

  habilitarInputCorreo();
});

$("#tabla tbody").on("click", ".btn-eliminar", function () {
  var filaSeleccionada = $(this).closest("tr");
  let idUsuario = tabledata.row(filaSeleccionada).data()["idUsuario"];
  const swalWithBootstrapButtons = Swal.mixin({
    customClass: {
      confirmButton: "btn btn-success m-1",
      cancelButton: "btn btn-danger m-1",
    },
    buttonsStyling: false,
  });
  swalWithBootstrapButtons
    .fire({
      title: "¿Desea eliminar?",
      text: "Esta acción es ireversible, ¿Desea continuar?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Eliminar",
      cancelButtonText: "Cancelar",
      customClass: {
        confirmButton: "btn btn-danger m-1",
        cancelButton: "btn btn-success m-1",
      },
      reverseButtons: true,
    })
    .then((result) => {
      if (result.isConfirmed) {
        var resultado = eliminarUsuario(idUsuario);
        if (resultado != 0) {
          tabledata.row(filaSeleccionada).remove().draw(false);
          swalWithBootstrapButtons.fire(
            "Eliminado",
            "El usuario fue eliminado",
            "success"
          );
        } else {
          Swal.fire({
            icon: "error",
            title: "Error",
            text: "Ocurrio un error al eliminar al usuario",
          });
        }
      } else if (result.dismiss === Swal.DismissReason.cancel) {
        swalWithBootstrapButtons.fire(
          "Cancelado",
          "Usted canceló la acción",
          "error"
        );
      }
    });
});
