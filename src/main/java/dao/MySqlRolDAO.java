package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import database.MysqlDBConexion;
import entity.Rol;
import interfaces.RolDAO;

public class MySqlRolDAO implements RolDAO{

	
	@Override
	public List<Rol> listadoRol(){
		List<Rol> roles = new ArrayList<Rol>();	
		Connection cn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		
		try {
			
			cn = MysqlDBConexion.getConexion();
			
			String sql = "select * from rol";
			pstm = cn.prepareStatement(sql);
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				Rol rol = new Rol();
				rol.setIdRol(rs.getInt(1));
				rol.setNombreRol(rs.getString(2));
				
			
				roles.add(rol);
				
			}
			if(roles.size() == 0) {
				roles = null;
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
		
		
		return roles;
	}
}
