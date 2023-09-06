function cerrarAlerta() {
  $("#liveToast").hide();
}

function mostrarAlerta(tipo, mensaje, tiempo = 3000) {
  var div_mensaje = $("#div-mensaje");
  var i_success = $("#i-success");
  var i_warning = $("#i-warning");
  var i_danger = $("#i-danger");

  $("#span-mensaje").html(mensaje);

  if (tipo == 0) {
    div_mensaje.removeClass("border-warning");
    div_mensaje.removeClass("border-danger");
    div_mensaje.addClass("border-success");
    i_success.attr("hidden", false);
    i_warning.attr("hidden", true);
    i_danger.attr("hidden", true);
  }

  if (tipo == 1) {
    div_mensaje.removeClass("border-success");
    div_mensaje.removeClass("border-danger");
    div_mensaje.addClass("border-warning");
    i_success.attr("hidden", true);
    i_warning.attr("hidden", false);
    i_danger.attr("hidden", true);
  }

  if (tipo == 2) {
    div_mensaje.removeClass("border-success");
    div_mensaje.removeClass("border-warning");
    div_mensaje.addClass("border-danger");
    i_success.attr("hidden", true);
    i_warning.attr("hidden", true);
    i_danger.attr("hidden", false);
  }

  $("#liveToast").show();

  setTimeout(() => {
    $("#liveToast").hide();
  }, tiempo);
}
