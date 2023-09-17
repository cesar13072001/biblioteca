var tabledata;
var fila;
var categoria;







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
      url: "./CategoriaServlet?type=listar",
      type: "GET",
      dataType: "json",
    },
    columns: [
      { data: "idCategoria", "width": "10%" },
      { data: "nombre" },
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


function rellenarCampos(idCategoria) {
  $("#tituloModal").html("Editar categoría: " + idCategoria);
  buscarCategoria(idCategoria);

  $("#nombres").val(categoria.nombre);
  
  $("#staticBackdrop").modal("show");
}


function accionFormulario() {
  var idCategoria = $("#idCategoria").val();
  if (idCategoria != "0") {
    editarCategoria();
  } else {
    agregarCategoria();
  }
}



function agregarCategoria() {
  $.ajax({
    type: "POST",
    url: "./CategoriaServlet?type=agregar",
    data: {nombre : $("#nombres").val()},
    dataType: "json",
    success: function (data) {
      if (data != null) {
		tabledata.row.add(data).draw(false);
        $("#staticBackdrop").modal("hide");
        mostrarAlerta(0, "Categoría agregado correctamente");
      } else {
        mostrarAlerta(2, "Ocurrio un error al agregar categoría");
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



function editarCategoria() {
   var datos = {
	idCategoria: $("#idCategoria").val(),
    nombre: $("#nombres").val()
   };
 
  $.ajax({
    type: "POST",
    url: "./CategoriaServlet?type=editar",
    data: datos,
    dataType: "json",
    success: function (data) {
      if (data.resultado == 1) {
		  categoria.nombre = datos.nombre;
		  categoria.idCategoria = datos.idCategoria;
		  
		  
        $("#staticBackdrop").modal("hide");
        mostrarAlerta(0, "Categoría editado correctamente");
        tabledata.row(fila).data(categoria).draw(false);
      } else {
        mostrarAlerta(2, "Ocurrio un error al editar categoría");
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


function buscarCategoria(id) {
  $.ajax({
    url: "./CategoriaServlet?type=buscar",
    data: { idCategoria: id },
    type: "POST",
    dataType: "json",
    async: false,
    success: function (data) {
      categoria = data;
    },
    error: function (error) {
      console.log(error);
    },
    beforeSend: function () {},
  });
}



function eliminarCategoria(id) {
  var salida;
  jQuery.ajax({
    url: "./CategoriaServlet?type=eliminar",
    type: "POST",
    data: { idCategoria: id},
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



$("#tabla tbody").on("click", ".btn-editar", function () {
  var filaSeleccionada = $(this).closest("tr");
  let idCategoria = tabledata.row(filaSeleccionada).data()["idCategoria"];
  fila = filaSeleccionada;
  $("#btn_formulario").html("Editar");
  $("#idCategoria").attr("value", idCategoria);
  rellenarCampos(idCategoria);
});



$("#staticBackdrop").on("hidden.bs.modal", function () {
  $("#formularioCategoria")[0].reset();
  $("#formularioCategoria").removeClass("was-validated");
  $("#btn_formulario").html("Agregar");
  $("#idCategoria").val(0);

  $("#tituloModal").html("Agregar categoría");

});

$("#tabla tbody").on("click", ".btn-eliminar", function () {
  var filaSeleccionada = $(this).closest("tr");
  let idCategoria = tabledata.row(filaSeleccionada).data()["idCategoria"];
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
        var resultado = eliminarCategoria(idCategoria);
        if (resultado != 0) {
          tabledata.row(filaSeleccionada).remove().draw(false);
          swalWithBootstrapButtons.fire(
            "Eliminado",
            "La categoría fue eliminada",
            "success"
          );
        } else {
          Swal.fire({
            icon: "error",
            title: "Error",
            text: "Ocurrio un error al eliminar la categoría",
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

