package dao;

import interfaces.AuthDAO;
import interfaces.CategoriaDAO;
import interfaces.EstadoPrestamoDAO;
import interfaces.LibroDAO;
import interfaces.PrestamoDAO;
import interfaces.RolDAO;
import interfaces.UsuarioDAO;

public abstract class DAOFactory {
	
	public static final int MYSQL = 1;
	public static final int AZURECOSMOS = 2;
	public static final int SQLSERVER = 3;
	
	// Metodos Abstractos 
	public abstract AuthDAO getAuthDAO();
	public abstract UsuarioDAO getUsuarioDAO();
	public abstract RolDAO getRolDAO();
	public abstract LibroDAO getLibroDAO();
	public abstract CategoriaDAO getCategoriaDAO();
	public abstract EstadoPrestamoDAO getEstadoPrestamoDAO();
	public abstract PrestamoDAO getPrestamoDAO();
	
	
	public static DAOFactory getDAOFactory(int tipo) {
		
		switch (tipo) {
			case MYSQL:
				return new MySqlDAOFactory();
			case AZURECOSMOS:
				return null;
			case SQLSERVER:
				return null;
		}
		
		return null;
		
	}

}
