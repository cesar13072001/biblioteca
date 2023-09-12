package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import database.MysqlDBConexion;
import entity.Categoria;
import entity.Libro;
import interfaces.CategoriaDAO;
import interfaces.LibroDAO;

public class MySqlLibroDAO implements LibroDAO{
	@Override
	public List<Libro> listadoLibros(){
		List<Libro> libros = new ArrayList<Libro>();	
		Connection cn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			
			cn = MysqlDBConexion.getConexion();
			
			String sql = "select * from libro l inner join categoria c on l.idCategoria = c.idCategoria;";
			pstm = cn.prepareStatement(sql);
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				Libro libro = new Libro();
				libro.setIdLibro(rs.getString(1));
				libro.setTitulo(rs.getString(2));
				libro.setDescripcion(rs.getString(3));
				libro.setEstado(rs.getBoolean(4));
				libro.setCantidad(rs.getInt(5));
				libro.setIdCategoria(rs.getInt(6));
				libro.setCategoria(
							new Categoria(
									rs.getInt(7),
									rs.getString(8)
							)
						);
				
				libros.add(libro);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			try {
				
				if(rs!=null) rs.close();
				if(pstm!=null) pstm.close();
				if(cn!=null) cn.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return libros;
	}
	
	
	@Override
	public Libro registrarLibro(Libro libro) {
		int salida = 0;
		Connection cn = null;
		PreparedStatement pstm = null;

		try {
			
			cn = MysqlDBConexion.getConexion();
			
			String sql = "insert into libro values(?,?,?,?,?,?);";
			
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, libro.getIdLibro());
			pstm.setString(2, libro.getTitulo());
			pstm.setString(3, libro.getDescripcion());
			pstm.setBoolean(4, libro.getEstado());
			pstm.setInt(5, libro.getCantidad());
			pstm.setInt(6, libro.getIdCategoria());
			
			salida = pstm.executeUpdate();
			
			if(salida == 1) {
				DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
				CategoriaDAO daoCategoria = daoFactory.getCategoriaDAO();
				int id = libro.getIdCategoria();
				Categoria categoria  = daoCategoria.listadoCategoria()
						.stream()
						.filter(c -> c.getIdCategoria() == id)
						.collect(Collectors.toList()).get(0);
				libro.setCategoria(categoria);	
			}
			else {
				libro = null;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			libro = null;
		}
		
		finally {
			try {
				if(pstm!=null) pstm.close();
				if(cn!=null) cn.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return libro;
	}
	
	
	@Override
	public Libro buscarLibro(String idLibro){
		Libro libro = null;		
		Connection cn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			
			cn = MysqlDBConexion.getConexion();
			
			String sql = "select * from libro where idLibro = ?;";
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, idLibro);
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				libro = new Libro();
				libro.setIdLibro(rs.getString(1));
				libro.setTitulo(rs.getString(2));
				libro.setDescripcion(rs.getString(3));
				libro.setEstado(rs.getBoolean(4));
				libro.setCantidad(rs.getInt(5));
				libro.setIdCategoria(rs.getInt(6));
				
				DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
				CategoriaDAO daoCategoria = daoFactory.getCategoriaDAO();
				int id = libro.getIdCategoria();
				Categoria categoria  = daoCategoria.listadoCategoria()
						.stream()
						.filter(c -> c.getIdCategoria() == id)
						.collect(Collectors.toList()).get(0);
				libro.setCategoria(categoria);	
					
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		finally {
			try {
				
				if(rs!=null) rs.close();
				if(pstm!=null) pstm.close();
				if(cn!=null) cn.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return libro;
	}
	
	@Override
	public Libro editarLibro(Libro libro){
		Connection cn = null;
		PreparedStatement pstm = null;
		
		try {
			
			cn = MysqlDBConexion.getConexion();
			
			String sql = "UPDATE libro set titulo = ?, descripcion = ?, cantidad = ?, idCategoria = ? where idLibro = ?";
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, libro.getTitulo());
			pstm.setString(2, libro.getDescripcion());
			pstm.setInt(3, libro.getCantidad());
			pstm.setInt(4, libro.getIdCategoria());
			pstm.setString(5, libro.getIdLibro());
			
			
			int salida = pstm.executeUpdate();
			if(salida == 1) {
				DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
				CategoriaDAO daoCategoria = daoFactory.getCategoriaDAO();
				int id = libro.getIdCategoria();
				Categoria categoria  = daoCategoria.listadoCategoria()
						.stream()
						.filter(c -> c.getIdCategoria() == id)
						.collect(Collectors.toList()).get(0);
				libro.setCategoria(categoria);	
			}
			else {
				libro = null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			libro = null;
		}
		
		finally {
			try {
				
				if(pstm!=null) pstm.close();
				if(cn!=null) cn.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return libro;
	}
	
	

	@Override
	public int actualizarCantidadReserva(String idLibro, int cantidad) {
		int salida = 0;
		Connection cn = null;
		PreparedStatement pstm = null;

		try {
			
			cn = MysqlDBConexion.getConexion();
			
			String sql = "call sp_actualizarCantidadLibro(?,?);";
			
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, idLibro);
			pstm.setInt(2, cantidad);
			
			
			salida = pstm.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			try {
				if(pstm!=null) pstm.close();
				if(cn!=null) cn.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return salida;
	}
	
	
	
	@Override
	public int editarEstadoLibro(String idUsuario, boolean estado){
		int salida = 0;
		Connection cn = null;
		PreparedStatement pstm = null;
		
		try {
			
			cn = MysqlDBConexion.getConexion();
			
			String sql = "UPDATE libro SET estado=? WHERE idLibro=?";
			pstm = cn.prepareStatement(sql);
			pstm.setBoolean(1, estado);
			pstm.setString(2, idUsuario);
			
			
			salida = pstm.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			try {
				
				if(pstm!=null) pstm.close();
				if(cn!=null) cn.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return salida;
	}
	
	
	
	
	@Override
	public int eliminarLibro(String idLibro) {
		int salida = 0;
		Connection cn = null;
		PreparedStatement pstm = null;
		
		try {
			
			cn = MysqlDBConexion.getConexion();
			
			String sql = "DELETE FROM libro WHERE idLibro=?";
			
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, idLibro);
			
			salida = pstm.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			try {
				
				if(pstm!=null) pstm.close();
				if(cn!=null) cn.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return salida;
	}

}
