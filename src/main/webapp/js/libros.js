var categorias;
var libro;
var fila;

listarCategorias();
listado();

function listarCategorias() {
  $.ajax({
    url: "./CategoriaServlet?type=listar",
    type: "GET",
    dataType: "json",
    async: false,
    success: function (data) {
	  categorias = data.data;
	  llenarCombo();
    },
    error: function (error) {
      console.log(error);
    },
    beforeSend: function () {
	},
  });
}


function llenarCombo(idCategoria = ""){
	var combo = $("#idCategoria");
	if(idCategoria == ""){
		let contenido = `<option value ="" selected>Seleccione</option>`;
		for(let cat of categorias){
			contenido +=`<option value="${cat.idCategoria}">${cat.nombre}</option>`;
		}
		combo.html(contenido);
	}else{
		let contenido = `<option value ="" disabled>Seleccione</option>`;
		for(let cat of categorias){
			if(idCategoria == cat.idCategoria){
				contenido +=`<option value="${cat.idCategoria}" selected>${cat.nombre}</option>`;
			}else{
				contenido +=`<option value="${cat.idCategoria}">${cat.nombre}</option>`;
			}
		combo.html(contenido);
		}		
	}	
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
      url: "./LibroServlet?type=listar",
      type: "GET",
      dataType: "json",
    },
    columns: [
      { data: "idLibro", "width": "10%" },
      { data: "titulo" },
      { data: "descripcion" },
      { data: "cantidad" },
      { data: "categoria.nombre", width: "75px" },
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
      }
    ],
  });
}


function accionFormulario() {
  var isbn = $("#isbn").val();
  if (isbn != "0") {
    editarLibro();
  } else {
    agregarLibro();
  }
}


function agregarLibro() {
  var datos = {
    idLibro: $("#idLibro").val(),
    titulo: $("#titulo").val(),
    descripcion: $("#descripcion").val(),
    cantidad: $("#cantidad").val(),
    idCategoria: $("#idCategoria").val()
  };

  $.ajax({
    type: "POST",
    url: "./LibroServlet?type=agregar",
    data: datos,
    dataType: "json",
    success: function (data) {
      if (data != null) {
		tabledata.row.add(data).draw(false);
        $("#staticBackdrop").modal("hide");
        mostrarAlerta(0, "Usuario agregado correctamente");
      } else {
        mostrarAlerta(2, "Ocurrio un error al agregar libro");
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

function buscarLibro(id) {
  $.ajax({
    url: "./LibroServlet?type=buscar",
    data: { idLibro: id },
    type: "POST",
    dataType: "json",
    async: false,
    success: function (data) {
      libro = data;
    },
    error: function (error) {
      console.log(error);
    },
    beforeSend: function () {},
  });
}


function editarLibro() {
   var datos = {
    idLibro: $("#idLibro").val(),
    titulo: $("#titulo").val(),
    descripcion: $("#descripcion").val(),
    cantidad: $("#cantidad").val(),
    idCategoria: $("#idCategoria").val(),
    estado : libro.estado,
  };
 
  $.ajax({
    type: "POST",
    url: "./LibroServlet?type=editar",
    data: datos,
    dataType: "json",
    success: function (data) {
      if (data != null) {
        $("#staticBackdrop").modal("hide");
        mostrarAlerta(0, "Libro editado correctamente");
        tabledata.row(fila).data(data).draw(false);
      } else {
        mostrarAlerta(2, "Ocurrio un error al editar el libro");
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



function rellenarCampos(idLibro) {
  $("#tituloModal").html("Editar libro: " + idLibro);
  buscarLibro(idLibro);
  llenarCombo(libro.idCategoria);	
  
  $("#idLibro").val(libro.idLibro);
  $("#titulo").val(libro.titulo);
  $("#descripcion").val(libro.descripcion);
  $("#cantidad").val(libro.cantidad);

  $("#div_idLibro").attr("hidden", true);
  $("#idLibro").attr("required", false);
  $("#staticBackdrop").modal("show");
}




function cambiarEstadoLibro(id, estado) {
  var salida;
  jQuery.ajax({
    url: "./LibroServlet?type=editarEstado",
    type: "POST",
    data: { idLibro: id, estado: estado },
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
  return salida;
}


function eliminarLibro(id) {
  var salida;
  jQuery.ajax({
    url: "./LibroServlet?type=eliminar",
    type: "POST",
    data: { idLibro: id},
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
  return salida;
}





$("#tabla tbody").on("click", ".btn-editar", function () {
  var filaSeleccionada = $(this).closest("tr");
  let idLibro = tabledata.row(filaSeleccionada).data()["idLibro"];
  fila = filaSeleccionada;
  $("#btn_formulario").html("Editar");
  $("#isbn").attr("value", idLibro);
  
  rellenarCampos(idLibro);
});


$("#tabla tbody").on("change", "#switchActivo", function () {
  var filaSeleccionada = $(this).closest("tr");
  let idLibro = tabledata.row(filaSeleccionada).data()["idLibro"];
  var checked = this.checked;
  this.checked = !checked;

  var opcion = checked == true ? "activará" : "desactivará";

  var texto = "Esta acción " + opcion + " el libro, ¿Desea continuar?";

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
        var salida = cambiarEstadoLibro(idLibro, checked);

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


$("#tabla tbody").on("click", ".btn-eliminar", function () {
  var filaSeleccionada = $(this).closest("tr");
  let idLibro = tabledata.row(filaSeleccionada).data()["idLibro"];
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
        var resultado = eliminarLibro(idLibro);
        if (resultado != 0) {
          tabledata.row(filaSeleccionada).remove().draw(false);
          swalWithBootstrapButtons.fire(
            "Eliminado",
            "El libro fue eliminado",
            "success"
          );
        } else {
          Swal.fire({
            icon: "error",
            title: "Error",
            text: "Ocurrio un error al eliminar el libro",
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


$("#staticBackdrop").on("hidden.bs.modal", function () {
  $("#formularioLibro")[0].reset();
  $("#formularioLibro").removeClass("was-validated");

  $("#div_idLibro").attr("hidden", false);
  $("#btn_formulario").html("Agregar");
  $("#isbn").val(0);

  $("#idLibro").attr("required", true);

  llenarCombo();
  $("#tituloModal").html("Agregar libro");

});


