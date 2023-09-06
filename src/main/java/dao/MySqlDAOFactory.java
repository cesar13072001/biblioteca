package dao;

import interfaces.AuthDAO;
import interfaces.CategoriaDAO;
import interfaces.LibroDAO;
import interfaces.PrestamoDAO;
import interfaces.RolDAO;
import interfaces.UsuarioDAO;

public class MySqlDAOFactory extends DAOFactory{

	@Override
	public AuthDAO getAuthDAO() {
	
		return new MySqlAuthDAO();
	}

	@Override
	public UsuarioDAO getUsuarioDAO() {
		return new MySqlUsuarioDAO();
	}

	@Override
	public LibroDAO getLibroDAO() {
		return new MySqlLibroDAO();
	}

	@Override
	public CategoriaDAO getCategoriaDAO() {	
		return new MySqlCategoriaDAO();
	}

	@Override
	public PrestamoDAO getPrestamoDAO() {
		return  new MySqlPrestamoDAO();
	}

	@Override
	public RolDAO getRolDAO() {
		return new MySqlRolDAO();
	}

	

}
