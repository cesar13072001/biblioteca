package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import entity.Categoria;
import entity.Libro;
import entity.Prestamo;
import entity.Usuario;
import interfaces.CategoriaDAO;
import interfaces.LibroDAO;
import interfaces.PrestamoDAO;
import interfaces.UsuarioDAO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import util.GeneradorReporte;

/**
 * Servlet implementation class ReporteSerlvet
 */
@WebServlet("/ReporteSerlvet")
public class ReporteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReporteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String categoria = request.getParameter("categoria");
    	DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
    	UsuarioDAO daoUsuario = null;
    	CategoriaDAO daoCategoria = null;
    	LibroDAO daoLibro = null;
    	PrestamoDAO daoPrestamo = null;

    	
    	ServletOutputStream out = response.getOutputStream();
    	String file = request.getServletContext().getRealPath("./reportes/"+categoria+".jasper");

    	
    	List<Usuario> usuarios = new ArrayList<Usuario>();
    	List<Categoria> categorias = new ArrayList<Categoria>();
    	List<Libro> libros = new ArrayList<Libro>();
    	List<Prestamo> prestamos = new ArrayList<Prestamo>();
    	
    	JRBeanCollectionDataSource datSource= null;
    	
    	if(categoria.equals("usuario")) {
    		daoUsuario = daoFactory.getUsuarioDAO();
    		usuarios = daoUsuario.listadoUsuario();
    		datSource= new JRBeanCollectionDataSource(usuarios);
    	}
    	if(categoria.equals("categoria")) {
    		daoCategoria = daoFactory.getCategoriaDAO();
    		categorias = daoCategoria.listadoCategoria();
    		datSource= new JRBeanCollectionDataSource(categorias);
    	}
    	if(categoria.equals("libro")) {
    		daoLibro = daoFactory.getLibroDAO();
    		libros = daoLibro.listadoLibros();
    		datSource= new JRBeanCollectionDataSource(libros);
    	}
    	if(categoria.equals("prestamos")) {
    		daoPrestamo = daoFactory.getPrestamoDAO();
    		prestamos = daoPrestamo.listadoPrestamos();
    		datSource= new JRBeanCollectionDataSource(prestamos);
    	}
    	
    	
    	response.setContentType("application/pdf");
    	response.addHeader("Content-disposition", "inline; filename="+categoria+".pdf");
    	JasperPrint jasper = GeneradorReporte.genera(file, datSource, null);
    	try {
			JasperExportManager.exportReportToPdfStream(jasper, out);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     	
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
