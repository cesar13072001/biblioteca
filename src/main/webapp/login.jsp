<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="include/header.jsp" %>
<title>Login</title>
</head>
<body>
<!-- Codigo html de alerta -->
<%@ include file="include/alerta.jsp" %>

<%@ include file="include/form-usuario.jsp" %>

    <div class="vh-100" style="background-color: whitesmoke;">
        <div class="container py-5 h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col col-xl-10">
                    <div class="card shadow" style="border-radius: 1rem;">
                        <div class="row g-0">

                            <div class="col-md-5 col-lg-5 d-none d-md-block">
                                <img src="https://i.blogs.es/38589b/stone1/450_1000.jpg" class="img-fluid h-100" style="border-radius: 1rem 0 0 1rem;">
                            </div>
        

                            <div class="col-md-7 col-lg-7 d-flex align-items-center">
                                <div class="card-body p-4 p-lg-5 text-black">
                            
                                    <div class="d-flex align-items-center mb-3 pb-1">
                                    	<i><img src="./image/icono.ico" width="75px"></i>
                                        <span class="h1 fw-bold mb-0">Biblioteca</span>
                                    </div>
                        
                                    <h5 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">Iniciar sesión</h5>

                                    <form action="javascript:login()">
                                        <div class="mb-3">
                                        <label for="email" class="form-label">Usuario</label>
                                        <input type="email" class="form-control form-control-lg " id="email">
                                        </div>
                                        <div class="mb-3">
                                        <label for="password" class="form-label">Contraseña</label>
                                        <input type="password" class="form-control form-control-lg " id="password">
                                        </div>
                                        
                                        <button type="submit" class="btn btn-primary w-100">Ingresar</button>
                                        
                                        
                                    </form>
                                    
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


<!-- scripts con link de dependencias -->
<%@ include file="include/footer.jsp" %>
</body>
<script src="./js/alerta.js"></script>
<script>
/*Codigo script js de alerta*/


   function login(){
	   var email = $("#email").val();
	   var password = $("#password").val();
	   
	   $.ajax({
		   type: "POST",
		   url: './AuthServlet',
		   data: {type: "login",email: email, password: password},
		   success: function(data){
			   var s =  $.parseJSON(data);
			   console.log(s);
			   if(s != null){
				   mostrarAlerta(0,"Bienvenido de vuelta");
			   }
			   else{
				   mostrarAlerta(2,"Credenciales incorrectas");
			   }
		   },
		   error: function(error){
			   console.log(error);
			   mostrarAlerta(1,"Ocurrrio un error inesperado");
			   
		   }
	   });
   }
</script>

</html>