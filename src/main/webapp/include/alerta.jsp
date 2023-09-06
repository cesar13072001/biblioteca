<div class="toast-container position-fixed bottom-0 end-0 p-3">
  <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
    <div class="toast-header">
      <strong class="me-auto"> Mensaje</strong>
      <small>Justo ahora</small>
      <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close" onclick="cerrarAlerta()"></button>
    </div>
    <div id="div-mensaje" class="toast-body" style="border-bottom: solid 5px; border-radius: 0px 0px 5px 5px">
      <i id="i-success" hidden class="fa-solid fa-circle-check fa-xl text-success"></i>
      <i id="i-warning" hidden class="fa-solid fa-circle-exclamation fa-xl text-warning" ></i>
      <i id="i-danger" hidden class="fa-solid fa-circle-xmark fa-xl text-danger"></i>
      <span id="span-mensaje">Credenciales incorrectas.</span>    	
    </div>
  </div>
</div>