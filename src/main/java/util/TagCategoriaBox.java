package util;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import dao.DAOFactory;
import entity.Categoria;
import interfaces.CategoriaDAO;


public class TagCategoriaBox extends TagSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		
		DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		CategoriaDAO daoCategoria = daoFactory.getCategoriaDAO();
		
		
		try {
			
			out.print("<select class='form-control' name='"+name+"' >");
			out.print("<option value=''>Seleccione</option>");
			
			List<Categoria> categorias = daoCategoria.listadoCategoria();
			
			for(Categoria categoria: categorias) {
				out.print("<option value='"+categoria.getIdCategoria()+"'>");
				out.print(categoria.getNombre());
				out.print("</option>");
			}
			out.print("</select>");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return super.doStartTag();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
	

}
