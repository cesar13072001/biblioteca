package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import database.MysqlDBConexion;
import entity.Categoria;
import interfaces.CategoriaDAO;


public class MySqlCategoriaDAO implements CategoriaDAO{

	@Override
	public List<Categoria> listadoCategoria(){
		List<Categoria> categorias = new ArrayList<Categoria>();	
		Connection cn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		
		try {
			
			cn = MysqlDBConexion.getConexion();
			
			String sql = "select * from categoria;";
			pstm = cn.prepareStatement(sql);
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				Categoria categoria = new Categoria();
				categoria.setIdCategoria(rs.getInt(1));
				categoria.setNombre(rs.getString(2));
				categorias.add(categoria);
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
		
		
		return categorias;
	}
	
	
	@Override
	public Categoria registrarCategoria(String nombre) {
		
		Connection cn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Categoria categoria = new Categoria();

		
		try {
			
			cn = MysqlDBConexion.getConexion();
			
			String sql = "insert into categoria (nombre) values(?);";
			
			pstm = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstm.setString(1, nombre);
			
			pstm.executeUpdate();
			rs = pstm.getGeneratedKeys();
			if(rs.next()) {
				categoria.setIdCategoria(rs.getInt(1));
				categoria.setNombre(nombre);
			}
			else {
				categoria = null;
			}
			
			
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
		
		
		return categoria;
	}
	
	
	@Override
	public Categoria buscarCategoria(int idCategoria){
		Categoria categoria = null;		
		Connection cn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			
			cn = MysqlDBConexion.getConexion();
			
			String sql = "select * from categoria where idcategoria = ?;";
			pstm = cn.prepareStatement(sql);
			pstm.setInt(1, idCategoria);
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				categoria = new Categoria();
				categoria.setIdCategoria(rs.getInt(1));
				categoria.setNombre(rs.getString(2));
						
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
		
		return categoria;
	}
	
	@Override
	public int editarCategoria(Categoria categoria){
		int salida = 0;
		Connection cn = null;
		PreparedStatement pstm = null;
		
		try {
			
			cn = MysqlDBConexion.getConexion();
			
			String sql = "UPDATE categoria set nombre = ? where idcategoria = ?";
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, categoria.getNombre());
			pstm.setInt(2, categoria.getIdCategoria());
			
			
			
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
	public int eliminarCategoria(int idCategoria) {
		int salida = 0;
		Connection cn = null;
		PreparedStatement pstm = null;
		
		try {
			
			cn = MysqlDBConexion.getConexion();
			
			String sql = "DELETE FROM categoria WHERE idCategoria=?";
			
			pstm = cn.prepareStatement(sql);
			pstm.setInt(1, idCategoria);
			
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
