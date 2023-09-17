 function login(){
	   var email = $("#email").val();
	   var password = $("#password").val();
	   
	   $.ajax({
		   type: "POST",
		   url: './AuthServlet',
		   data: {type: "login",email: email, password: password},
		   dataType: "json",
		   success: function(data){
			   if(data != null){
				  mostrarAlerta(0,"Bienvenido de vuelta");
				   window.location.replace("./"); 

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