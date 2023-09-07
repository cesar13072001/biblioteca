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
import entity.Libro;
import interfaces.LibroDAO;
import interfaces.UsuarioDAO;

/**
 * Servlet implementation class LibroServlet
 */
@WebServlet("/LibroServlet")
public class LibroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LibroServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String type= request.getParameter("type");
    	if(type.equals("listar")) {
    		listarLibros(request, response);
    	}
    	if(type.equals("agregar")) {
    		agregarLibro(request, response);
    	}
    	if(type.equals("buscar")) {
    		buscarLibro(request, response);
    	}
    	if(type.equals("editar")) {
    		editarLibro(request, response);
    	}
    	if(type.equals("editarEstado")) {
    		editarEstadoLibro(request, response);
    	}
    	if(type.equals("eliminar")) {
    		eliminarLibro(request, response);
    	}
    }
    
    protected void listarLibros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	List<Libro> libros = new ArrayList<Libro>();
    	
    	HashMap<String, Object> respuesta = null;

    	DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
    	LibroDAO daoLibro = daoFactory.getLibroDAO();
    	
    	
    	try {
			libros = daoLibro.listadoLibros();
			respuesta = new HashMap<String, Object>();
    		respuesta.put("data", libros);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	Gson gson = new Gson();
    	PrintWriter out = response.getWriter();
		out.print(gson.toJson(respuesta));
		out.flush();
		out.close();
    }
    
    protected void agregarLibro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String idLibro = request.getParameter("idLibro");
    	String titulo = request.getParameter("titulo");
    	String descripcion = request.getParameter("descripcion");
    	int cantidad = Integer.parseInt(request.getParameter("cantidad"));
    	int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));
    	
    	Libro libro = new Libro();
    	libro.setIdLibro(idLibro);
    	libro.setTitulo(titulo);
    	libro.setDescripcion(descripcion);
    	libro.setEstado(true);
    	libro.setCantidad(cantidad);
    	libro.setIdCategoria(idCategoria);
    	
    	Libro salida = new Libro();
    	
    	DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
    	LibroDAO daoLibro = daoFactory.getLibroDAO();
    	
    	try {
    		salida = daoLibro.registrarLibro(libro);
    		
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	Gson gson = new Gson();
    	PrintWriter out = response.getWriter();
    	out.print(gson.toJson(salida));
    	out.flush();
    	out.close();
    	
    }
    
    protected void buscarLibro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String idLibro = request.getParameter("idLibro");
    	
    	Libro libro = new Libro();
    	
    	DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
    	LibroDAO daoLibro = daoFactory.getLibroDAO();
    	
    	try {
    		libro = daoLibro.buscarLibro(idLibro);
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	Gson gson = new Gson();
    	PrintWriter out = response.getWriter();
    	out.print(gson.toJson(libro));
    	out.flush();
    	out.close();
    	
    }
    
    protected void editarLibro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String idLibro = request.getParameter("idLibro");
    	String titulo = request.getParameter("titulo");
    	String descripcion = request.getParameter("descripcion");
    	int cantidad = Integer.parseInt(request.getParameter("cantidad"));
    	int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));
    	Boolean estado = Boolean.parseBoolean(request.getParameter("estado"));
    	
    	Libro libro = new Libro();
    	libro.setIdLibro(idLibro);
    	libro.setTitulo(titulo);
    	libro.setDescripcion(descripcion);
    	libro.setCantidad(cantidad);
    	libro.setIdCategoria(idCategoria);
    	libro.setEstado(estado);
    	
    	DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
    	LibroDAO daoLibro = daoFactory.getLibroDAO();
    	
    	Libro salida = new Libro();
    	
    	try {
    		salida = daoLibro.editarLibro(libro); 		
        	
    	}catch (Exception e) {
    		e.printStackTrace();
		}
    	Gson gson = new Gson();
    	PrintWriter out = response.getWriter();
    	out.print(gson.toJson(salida));
    	out.flush();
    	out.close();

    }
    
    
    public void editarEstadoLibro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String idLibro = request.getParameter("idLibro");
    	Boolean estado = Boolean.parseBoolean(request.getParameter("estado"));

    	
    	DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
    	LibroDAO daoLibro = daoFactory.getLibroDAO();
    		
    	HashMap<String, Integer> respuesta = null;
    	
    	
    	try {
    		respuesta = new HashMap<String, Integer>();
    		int salida = daoLibro.editarEstadoLibro(idLibro, estado);
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
    
    protected void eliminarLibro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String idLibro = request.getParameter("idLibro");

    	DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
    	LibroDAO daoLibro = daoFactory.getLibroDAO();
    	
    	HashMap<String, Object> respuesta = null;
    	
    	try {
    		int salida = daoLibro.eliminarLibro(idLibro); 		
        	respuesta = new HashMap<String, Object>();
    		respuesta.put("resultado", salida);
			
		} catch (Exception e) {
			e.printStackTrace();
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
