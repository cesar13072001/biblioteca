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