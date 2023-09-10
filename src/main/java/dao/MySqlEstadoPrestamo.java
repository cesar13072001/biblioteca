package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.MysqlDBConexion;
import entity.EstadoPrestamo;
import interfaces.EstadoPrestamoDAO;

public class MySqlEstadoPrestamo implements EstadoPrestamoDAO{

	@Override
	public EstadoPrestamo buscarEstadoPrestamo(int idEstado){
		EstadoPrestamo estadoPrestamo = null;		
		Connection cn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			
			cn = MysqlDBConexion.getConexion();
			
			String sql = "select * from estadoEntrega where idEstado = ?;";
			pstm = cn.prepareStatement(sql);
			pstm.setInt(1, idEstado);
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				estadoPrestamo = new EstadoPrestamo();
				estadoPrestamo.setIdEstado(rs.getInt(1));
				estadoPrestamo.setDescripcion(rs.getString(2));
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
		
		return estadoPrestamo;
	}
}
