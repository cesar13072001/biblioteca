<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Esto es el index</h1>
</body>

<script>
	<%HttpSession sesion = request.getSession();
	String letra = null;
	letra = (String)sesion.getAttribute("letra");
	%>
	if(<%=letra%> == null){
		alert("No hay nada")
	}
	
</script>
</html>