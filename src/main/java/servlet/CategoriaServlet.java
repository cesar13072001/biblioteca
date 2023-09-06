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
import entity.Categoria;
import interfaces.CategoriaDAO;

/**
 * Servlet implementation class CategoriaServlet
 */
@WebServlet("/CategoriaServlet")
public class CategoriaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoriaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String type= request.getParameter("type");
    	if(type.equals("listar")) {
    		listarCategorias(request, response);
    	}
    	if(type.equals("agregar")) {
    		agregarCategoria(request, response);
    	}
    	if(type.equals("buscar")) {
    		buscarCategoria(request, response);
    	}
    	if(type.equals("editar")) {
    		editarCategoria(request, response);
    	}
    	if(type.equals("eliminar")) {
    		eliminarCategoria(request, response);
    	}
    }
    
    protected void listarCategorias(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	List<Categoria> categorias = new ArrayList<Categoria>();
    	
    	HashMap<String, Object> respuesta = null;

    	DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
    	CategoriaDAO daoCategoria = daoFactory.getCategoriaDAO();
    	
    	
    	try {
			categorias = daoCategoria.listadoCategoria();
			respuesta = new HashMap<String, Object>();
    		respuesta.put("data", categorias);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	Gson gson = new Gson();
    	PrintWriter out = response.getWriter();
		out.print(gson.toJson(respuesta));
		out.flush();
		out.close();
    }
    
    protected void agregarCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String nombre = request.getParameter("nombre");
    	
    	Categoria categoria = new Categoria();
    	
    	DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
    	CategoriaDAO daoCategoria = daoFactory.getCategoriaDAO();
    	
    	try {
    		categoria = daoCategoria.registrarCategoria(nombre);
    		
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	Gson gson = new Gson();
    	PrintWriter out = response.getWriter();
    	out.print(gson.toJson(categoria));
    	out.flush();
    	out.close();
    	
    }
    
    protected void buscarCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));
    	
    	Categoria categoria = new Categoria();
    	
    	DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
    	CategoriaDAO daoCategoria = daoFactory.getCategoriaDAO();
    	
    	try {
    		categoria = daoCategoria.buscarCategoria(idCategoria);
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	Gson gson = new Gson();
    	PrintWriter out = response.getWriter();
    	out.print(gson.toJson(categoria));
    	out.flush();
    	out.close();
    	
    }
    
    protected void editarCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));
    	String nombre = request.getParameter("nombre");
    	
    	Categoria categoria = new Categoria();
    	categoria.setIdCategoria(idCategoria);
    	categoria.setNombre(nombre);
    	
    	DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
    	CategoriaDAO daoCategoria = daoFactory.getCategoriaDAO();
    	
    	HashMap<String, Object> respuesta = null;
    	
    	try {
    		int salida = daoCategoria.editarCategoria(categoria); 		
        	respuesta = new HashMap<String, Object>();
    		respuesta.put("resultado", salida);
    	}catch (Exception e) {
    		e.printStackTrace();
		}
    	Gson gson = new Gson();
    	PrintWriter out = response.getWriter();
    	out.print(gson.toJson(respuesta));
    	out.flush();
    	out.close();

    }
    
    protected void eliminarCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));

    	DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
    	CategoriaDAO daoCategoria = daoFactory.getCategoriaDAO();
    	
    	HashMap<String, Object> respuesta = null;
    	
    	try {
    		int salida = daoCategoria.eliminarCategoria(idCategoria); 		
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
