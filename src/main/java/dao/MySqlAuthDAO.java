package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.MysqlDBConexion;
import entity.Rol;
import entity.Usuario;
import interfaces.AuthDAO;

public class MySqlAuthDAO implements AuthDAO{

	@Override
	public Usuario loginUsuario(String email, String password) {
		
		Usuario usuario = null;
		
		Connection cn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			
			cn = MysqlDBConexion.getConexion();
			
			String sql = "call sp_login(?,?);";
			
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, email);
			pstm.setString(2, password);
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				
				
				usuario = new Usuario();
			
				usuario.setIdUsuario(rs.getString(1));
				usuario.setNombres(rs.getString(2));
				usuario.setApellidos(rs.getString(3));
				usuario.setFechaNacimiento(rs.getString(4));
				usuario.setEmail(rs.getString(5));
				usuario.setFechaRegistro(rs.getString(7));
				usuario.setEstado(rs.getBoolean(8));
				usuario.setIdRol(rs.getInt(10));
				usuario.setRol(new Rol(rs.getInt(10), rs.getString(11)));
				
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
		
		return usuario;
		
	}
	
	

}
