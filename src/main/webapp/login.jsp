<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
<link rel="shortcut icon" href="./image/icono.ico">

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


</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
<script src="https://kit.fontawesome.com/b073dd6059.js" crossorigin="anonymous"></script>
<script src="./js/alerta.js"></script>
<script src="./js/login.js"></script>

</html>