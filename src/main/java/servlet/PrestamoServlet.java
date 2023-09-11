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
import entity.Prestamo;
import interfaces.LibroDAO;
import interfaces.PrestamoDAO;
import util.FechaActual;

/**
 * Servlet implementation class PrestamoServlet
 */
@WebServlet("/PrestamoServlet")
public class PrestamoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrestamoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String type = request.getParameter("type");
    	response.setContentType("text/html; charset=utf-8");
    	if(type.equals("listar")) {
    		listado(request, response);
    	}
    	if(type.equals("reserva")) {
    		reserva(request, response);
    	}
    	if(type.equals("entrega")) {
    		entrega(request, response);
    	}
    	if(type.equals("consultaDeuda")) {
    		consultaDeuda(request, response);
    	}
    	
    }
    
    
    protected void listado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		PrestamoDAO daoPrestamo = daoFactory.getPrestamoDAO();
		
		List<Prestamo> prestamos = new ArrayList<Prestamo>();
    	
    	HashMap<String, Object> respuesta = null;
		
    	try {
			prestamos = daoPrestamo.listadoPrestmamos();
			respuesta = new HashMap<String, Object>();
    		respuesta.put("data", prestamos);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
		
		System.out.println();
    	Gson gson = new Gson();
    	PrintWriter out = response.getWriter();
		out.print(gson.toJson(respuesta));
		out.flush();
		out.close();
    }
    
    
    protected void reserva(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String idUsuario = request.getParameter("idUsuario");
    	String idLibro = request.getParameter("idLibro");
    	int dias = Integer.parseInt(request.getParameter("dias"));
    	
    	Prestamo prestamo = new Prestamo();
    	prestamo.setIdUsuario(idUsuario);
    	prestamo.setIdLibro(idLibro);
    	prestamo.setFechaPrestamo(new FechaActual().fecha());
    	prestamo.setFechaVencimiento(new FechaActual().fechaAumentada(dias));
    	prestamo.setFechaEntrega(null);
    	prestamo.setIdEstado(1);
    	
    	DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		PrestamoDAO daoPrestamo = daoFactory.getPrestamoDAO();
		LibroDAO daoLibro = daoFactory.getLibroDAO();
    	
		Prestamo salida = new Prestamo();
		
    	try {
    		salida = daoPrestamo.registrarPrestamo(prestamo);
    		if(salida != null) {
    			daoLibro.actualizarCantidadReserva(idLibro, -1);
    		}
    		
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    	
    	System.out.println();
    	Gson gson = new Gson();
    	PrintWriter out = response.getWriter();
		out.print(gson.toJson(salida));
		out.flush();
		out.close();
    }
    
    protected void entrega(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));
    	int idEstado = Integer.parseInt(request.getParameter("idEstado"));
    	String idLibro = request.getParameter("idLibro");
    	String fecha = new FechaActual().fecha();
    	
    	
    	if(idEstado == 1) {
			idEstado = 2;
		}
		else if(idEstado == 3) {
			idEstado = 4;
		}
    	
    	DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		PrestamoDAO daoPrestamo = daoFactory.getPrestamoDAO();
		LibroDAO daoLibro = daoFactory.getLibroDAO();

		
		Prestamo prestamo = new Prestamo();
		
		try {
			prestamo = daoPrestamo.entregarLibro(idPrestamo, fecha, idEstado);
			if(prestamo != null) {
				daoLibro.actualizarCantidadReserva(idLibro, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println();
    	Gson gson = new Gson();
    	PrintWriter out = response.getWriter();
		out.print(gson.toJson(prestamo));
		out.flush();
		out.close();
    	
    	
    }
    
    protected void consultaDeuda(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String idUsuario = request.getParameter("idUsuario");
    	
    	int salida = 0;
    	
    	DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		PrestamoDAO daoPrestamo = daoFactory.getPrestamoDAO();
		
    	HashMap<String, Object> respuesta = null;

		
		try {
    		salida = daoPrestamo.buscarDeudasUsuario(idUsuario);
    		respuesta = new HashMap<String, Object>();
    		respuesta.put("resultado", salida);
    		
    	}catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println();
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
