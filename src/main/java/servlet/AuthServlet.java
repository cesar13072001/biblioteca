package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import dao.DAOFactory;
import entity.Usuario;
import interfaces.AuthDAO;
import util.Encriptador;

/**
 * Servlet implementation class AuthServlet
 */
@WebServlet("/AuthServlet")
public class AuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String type = request.getParameter("type");
    	response.setContentType("text/html; charset=utf-8");

    	System.out.println(type);
    	if(type.equals("login")) {
    		login(request, response);
    	}
    }
    
    
 
    
    
    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String email = request.getParameter("email");
    	String password = request.getParameter("password");
    	
    	DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		AuthDAO daoAuth = daoFactory.getAuthDAO();
		
		Usuario usuario = new Usuario();
    	
		HashMap<String, Object> respuesta = null;
		
    	try {
    	Encriptador encriptador = new Encriptador();   	
    	usuario = daoAuth.loginUsuario(email, encriptador.password(password)); 
    	System.out.println(usuario);
    	if(usuario != null) {
    		System.out.println("usuario no es null");
    		HttpSession sesionUsuario = request.getSession();
        	sesionUsuario.setAttribute("usuario", usuario);
        	respuesta = new HashMap<String, Object>();
        	respuesta.put("rol", usuario.getRol());
        	respuesta.put("estado", usuario.getEstado());
        	
        	
    	}	
    	}catch (Exception e) {
			System.out.println(e.getMessage());
		}
    	
    	HttpSession sesion = request.getSession();
		Usuario user = (Usuario) sesion.getAttribute("usuario"); 
    	
    	
    	Gson gson = new Gson();
    	PrintWriter out = response.getWriter();
		out.print(gson.toJson(user));
		out.flush();
		out.close();
    	
    }
    
    
    protected void salir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.getSession().invalidate();
		response.sendRedirect("login.jsp");
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
		System.out.println("Estoy en el post");
		doGet(request, response);
	}

}
