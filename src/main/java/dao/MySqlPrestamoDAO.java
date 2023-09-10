package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.MysqlDBConexion;
import entity.Prestamo;
import interfaces.EstadoPrestamoDAO;
import interfaces.LibroDAO;
import interfaces.PrestamoDAO;
import interfaces.UsuarioDAO;

public class MySqlPrestamoDAO implements PrestamoDAO{
	
	
	@Override
	public List<Prestamo> listadoPrestmamos(){
		List<Prestamo> prestamos = new ArrayList<Prestamo>();	
		Connection cn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		
		UsuarioDAO daoUsuario = null;
		LibroDAO daoLibro = null;
		EstadoPrestamoDAO daoEstadoPrestamo = null;
		
		try {
			
			cn = MysqlDBConexion.getConexion();
			
			String sql = "select * from prestamo";
			pstm = cn.prepareStatement(sql);
			rs = pstm.executeQuery();
			
			
			daoUsuario = daoFactory.getUsuarioDAO();
			daoLibro = daoFactory.getLibroDAO();
			daoEstadoPrestamo = daoFactory.getEstadoPrestamoDAO();
			
			while (rs.next()) {
				
				
				Prestamo prestamo = new Prestamo();
				prestamo.setIdPrestamo(rs.getInt(1));
				prestamo.setFechaPrestamo(rs.getString(2));
				prestamo.setFechaVencimiento(rs.getString(3));
				prestamo.setFechaEntrega(rs.getString(4) == null ? "**Sin entrega**" : rs.getString(4));
				prestamo.setIdEstado(rs.getInt(5));
				prestamo.setIdUsuario(rs.getString(6));
				prestamo.setIdLibro(rs.getString(7));
				
				prestamo.setUsuario(daoUsuario.buscarUsuario(prestamo.getIdUsuario()));
				prestamo.setLibro(daoLibro.buscarLibro(prestamo.getIdLibro()));
				prestamo.setEstadoPrestamo(daoEstadoPrestamo.buscarEstadoPrestamo(prestamo.getIdEstado()));
				
				prestamos.add(prestamo);
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
		
		
		return prestamos;
	}
	
	
	
	
	
	
	
	
	
	@Override
	public Prestamo registrarPrestamo(Prestamo prestamo) {
		
		Connection cn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			
			cn = MysqlDBConexion.getConexion();
			
			String sql = "insert into prestamo values(null,?,?,?,?,?,?);";
			
			pstm = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstm.setString(1, prestamo.getFechaPrestamo());
			pstm.setString(2, prestamo.getFechaVencimiento());
			pstm.setString(3, prestamo.getFechaEntrega());
			pstm.setInt(4, prestamo.getIdEstado());
			pstm.setString(5, prestamo.getIdUsuario());
			pstm.setString(6, prestamo.getIdLibro());
			
			
			pstm.executeUpdate();
			rs = pstm.getGeneratedKeys();
			if(rs.next()) {
				prestamo.setFechaEntrega("**Sin entrega**");
				DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
				UsuarioDAO daoUsuario = daoFactory.getUsuarioDAO();
				LibroDAO daoLibro = daoFactory.getLibroDAO();
				EstadoPrestamoDAO daoEstadoPrestamo = daoFactory.getEstadoPrestamoDAO();
				
				prestamo.setIdPrestamo(rs.getInt(1));
				
				prestamo.setUsuario(daoUsuario.buscarUsuario(prestamo.getIdUsuario()));
				prestamo.setLibro(daoLibro.buscarLibro(prestamo.getIdLibro()));
				prestamo.setEstadoPrestamo(daoEstadoPrestamo.buscarEstadoPrestamo(prestamo.getIdEstado()));
				
			}
			else {
				prestamo = null;
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
		
		
		return prestamo;
	}
	
	
	@Override
	public Prestamo entregarLibro(int idPrestamo, String fechaEntrega, int idEstado) {
		
		Connection cn = null;
		PreparedStatement pstm = null;
		//ResultSet rs = null;
		Prestamo prestamo = null;

		try {
			
			cn = MysqlDBConexion.getConexion();
			
			String sql = "update prestamo set idEstado = ?, fechaEntrega = ? where idPrestamo = ?;";
			
			
			pstm = cn.prepareStatement(sql);
			pstm.setInt(1, idEstado);
			pstm.setString(2, fechaEntrega);
			pstm.setInt(3, idPrestamo);
			
			
			
			int salida = pstm.executeUpdate();
			if (salida > 0) {
				prestamo = new Prestamo();
				
				prestamo.setFechaEntrega(fechaEntrega);
				prestamo.setIdEstado(idEstado);
				
				DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
				EstadoPrestamoDAO daoEstadoPrestamo = daoFactory.getEstadoPrestamoDAO();
				
				prestamo.setEstadoPrestamo(daoEstadoPrestamo.buscarEstadoPrestamo(prestamo.getIdEstado()));
				
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
		
		
		return prestamo;
	}
	
	
	
	@Override
	public int buscarDeudasUsuario(String idUsuario) {
		int resultado = 0;
		Connection cn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {	
			cn = MysqlDBConexion.getConexion();
			
			String sql = "call sp_verificarDeudasUsuario(?);";
			
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, idUsuario);
			
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				resultado++;
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
		return resultado;
	}
	
	
	

}
