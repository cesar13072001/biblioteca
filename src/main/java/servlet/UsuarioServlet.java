package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.DAOFactory;
import entity.Usuario;
import interfaces.UsuarioDAO;
import util.Encriptador;
import util.EnviarCorreo;
import util.FechaActual;

/**
 * Servlet implementation class UsuarioServlet
 */
@WebServlet("/UsuarioServlet")
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsuarioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String type= request.getParameter("type");
    	if(type.equals("listar")) {
    		listadoUsuario(request, response);
    	}
    	if(type.equals("agregar")) {
    		registrar(request, response);
    	}
    	if(type.equals("buscar")) {
    		buscarUsuario(request, response);
    	}
    	if(type.equals("editar")) {
    		editarUsuario(request, response);
    	}
    	if(type.equals("editarEstado")) {
    		editarEstadoUsuario(request, response);
    	}
    	if(type.equals("eliminar")) {
    		eliminarUsuario(request, response);
    	}
    	
    }
    
    
    public void listadoUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
    	UsuarioDAO daoUsuario = daoFactory.getUsuarioDAO();
    	
    	HashMap<String, Object> respuesta = null;
    	List<Usuario> usuario = new ArrayList<Usuario>();
    	
    	try {
    		usuario = daoUsuario.listadoUsuario();
    		respuesta = new HashMap<String, Object>();
    		respuesta.put("data", usuario);
    		
    	}catch (Exception e) {
			System.out.println(e);
		}
    	
    	Gson gson = new Gson();
    	PrintWriter out = response.getWriter();
		out.print(gson.toJson(respuesta));
		out.flush();
		out.close();
    	
    }
    
    protected void registrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String nombres = request.getParameter("nombres");
    	String apellidos = request.getParameter("apellidos");
    	String nacimiento = request.getParameter("fecha");
    	String dni = request.getParameter("dni");
    	String correo = request.getParameter("correo");
    	
    	Usuario salida = new Usuario();
    	
    	if(correo.equals("")) {
    		correo = null;
    	}
    
    	Usuario usuario = new Usuario();
    	usuario.setIdUsuario(dni);
    	usuario.setNombres(nombres);
    	usuario.setApellidos(apellidos);
    	usuario.setFechaNacimiento(nacimiento);
    	usuario.setEmail(correo);
    	usuario.setFechaRegistro(new FechaActual().fecha());
    	usuario.setEstado(true);
    	usuario.setIdRol(2);
    	    		
    	DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		UsuarioDAO daoUsuario = daoFactory.getUsuarioDAO();
		
		
    	
    	try {

        	Encriptador encriptador = new Encriptador(); 
        	usuario.setPassword(encriptador.password(dni));
    		
        	salida = daoUsuario.registrarUsuario(usuario);
        	
        	
        	if(salida != null && correo != null) {
        		EnviarCorreo enviar = new EnviarCorreo();
        		enviar.correo(correo, dni);
        	}
        	
        	
        	}
    	catch (Exception e) {
			System.out.println(e);
		}
    	
    	Gson gson = new Gson();
    	PrintWriter out = response.getWriter();
		out.print(gson.toJson(salida));
		out.flush();
		out.close();   	    	
  }
    
    
    public void buscarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String idUsuario = request.getParameter("id");
    	
    	DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
    	UsuarioDAO daoUsuario = daoFactory.getUsuarioDAO();
    		
    	Usuario usuario = new Usuario();
    	
    	try {
    		usuario = daoUsuario.buscarUsuario(idUsuario);
    		
    	}catch (Exception e) {
			System.out.println(e);
		}
    	
    	Gson gson = new Gson();
    	PrintWriter out = response.getWriter();
		out.print(gson.toJson(usuario));
		out.flush();
		out.close();
    	
    }
    
    
    public void editarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String idUsuario = request.getParameter("idUsuario");
    	String nombres = request.getParameter("nombres");
    	String apellidos = request.getParameter("apellidos");
    	String nacimiento = request.getParameter("fecha");
    	String correo = request.getParameter("email");
    	
    	if(correo.equals("")) {
    		correo = null;
    	}
    	
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(idUsuario);
		usuario.setNombres(nombres);
		usuario.setApellidos(apellidos);
		usuario.setFechaNacimiento(nacimiento);
		usuario.setEmail(correo);
    	
    	DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
    	UsuarioDAO daoUsuario = daoFactory.getUsuarioDAO();
    		
    	HashMap<String, Object> respuesta = null;
    	
    	try {
    		
    		int salida  = daoUsuario.editarUsuario(usuario);
        	respuesta = new HashMap<String, Object>();
    		respuesta.put("resultado", salida);
    		
    	}catch (Exception e) {
			System.out.println(e);
		}
    	
    	Gson gson = new Gson();
    	PrintWriter out = response.getWriter();
		out.print(gson.toJson(respuesta));
		out.flush();
		out.close();
    	
    }
    
    
    public void editarEstadoUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String idUsuario = request.getParameter("idUsuario");
    	Boolean estado = Boolean.parseBoolean(request.getParameter("estado"));

    	
    	DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
    	UsuarioDAO daoUsuario = daoFactory.getUsuarioDAO();
    		
    	HashMap<String, Integer> respuesta = null;
    	
    	
    	try {
    		respuesta = new HashMap<String, Integer>();
    		int salida = daoUsuario.editarEstadoUsuario(idUsuario, estado);
    		respuesta.put("resultado", salida);
    		
    	}catch (Exception e) {
			System.out.println(e);
		}
    	
    	Gson gson = new Gson();
    	PrintWriter out = response.getWriter();
		out.print(gson.toJson(respuesta));
		out.flush();
		out.close();
    	
    }
    
    public void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String idUsuario = request.getParameter("idUsuario");
    	
    	DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
    	UsuarioDAO daoUsuario = daoFactory.getUsuarioDAO();
    		
    	HashMap<String, Object> respuesta = null;
    	
    	try {
    		respuesta = new HashMap<String, Object>();
    		int salida = daoUsuario.eliminarUsuario(idUsuario);
    		respuesta.put("resultado", salida);
    		
    	}catch (Exception e) {
			System.out.println(e);
		}
    	
    	Gson gson = new Gson();
    	PrintWriter out = response.getWriter();
		out.print(gson.toJson(respuesta));
		out.flush();
		out.close();
    	
    }
    
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
