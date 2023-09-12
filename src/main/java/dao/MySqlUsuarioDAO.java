package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import database.MysqlDBConexion;
import entity.Rol;
import entity.Usuario;
import interfaces.RolDAO;
import interfaces.UsuarioDAO;

public class MySqlUsuarioDAO implements UsuarioDAO{
	
	@Override
	public List<Usuario> listadoUsuario(){
		List<Usuario> usuarios = new ArrayList<Usuario>();	
		Connection cn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		
		try {
			
			cn = MysqlDBConexion.getConexion();
			
			String sql = "call sp_listadoUsuarios();";
			pstm = cn.prepareStatement(sql);
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				Usuario usuario = new Usuario();
				
				usuario.setIdUsuario(rs.getString(1));
				usuario.setNombres(rs.getString(2));
				usuario.setApellidos(rs.getString(3));
				usuario.setFechaNacimiento(rs.getString(4));
				usuario.setEmail(rs.getString(5) != null ? rs.getString(5) : "**Sin datos**");
				usuario.setFechaRegistro(rs.getString(6));
				usuario.setEstado(rs.getBoolean(7));
				usuario.setIdRol(rs.getInt(8));
				usuario.setRol(
						new Rol(
								rs.getInt(9),
								rs.getString(10)
								)
						);
							
				usuarios.add(usuario);
				
			}
			if(usuarios.size() == 0) {
				usuarios = null;
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
		
		
		return usuarios;
	}
	
	
	@Override
	public Usuario registrarUsuario(Usuario usuario) {
		
		Connection cn = null;
		PreparedStatement pstm = null;
		
		try {
			
			cn = MysqlDBConexion.getConexion();
			
			String sql = "INSERT INTO USUARIO VALUES "
					+ "(?,?,?,?,?,?,?,?,?);";
			
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, usuario.getIdUsuario());
			pstm.setString(2, usuario.getNombres());
			pstm.setString(3, usuario.getApellidos());
			pstm.setString(4, usuario.getFechaNacimiento());
			pstm.setString(5, usuario.getEmail());
			pstm.setString(6, usuario.getPassword());
			pstm.setString(7, usuario.getFechaRegistro());
			pstm.setBoolean(8, usuario.getEstado());
			pstm.setInt(9, usuario.getIdRol());
			int salida = pstm.executeUpdate();
			
			
			if(salida == 1) {
			DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
			RolDAO daoRol = daoFactory.getRolDAO();
			int id = usuario.getIdRol();
			Rol rol  = daoRol.listadoRol()
					.stream()
					.filter(r -> r.getIdRol() == id)
					.collect(Collectors.toList()).get(0);
			usuario.setRol(rol);
			usuario.setPassword(null);
			if(usuario.getEmail() == null) {
				usuario.setEmail("**Sin datos**");
			}
			
			}else {
				usuario = null;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			usuario= null;
		}
		
		finally {
			try {
				if(pstm!=null) pstm.close();
				if(cn!=null) cn.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return usuario;
	}
	
	
	@Override
	public Usuario buscarUsuario(String idUsuario){
		Usuario usuario = null;		
		Connection cn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			
			cn = MysqlDBConexion.getConexion();
			
			String sql = "call sp_buscarUsuario(?);";
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, idUsuario);
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				usuario = new Usuario();
				usuario.setIdUsuario(rs.getString(1));
				usuario.setNombres(rs.getString(2));
				usuario.setApellidos(rs.getString(3));
				usuario.setFechaNacimiento(rs.getString(4));
				usuario.setEmail(rs.getString(5) != null ? rs.getString(5) : "");
				usuario.setFechaRegistro(rs.getString(6));
				usuario.setEstado(rs.getBoolean(7));
				usuario.setIdRol(rs.getInt(8));
				
				int idRol = usuario.getIdRol();
				DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
				RolDAO daoRol = daoFactory.getRolDAO();	
				Rol rol  = daoRol.listadoRol()
						.stream()
						.filter(r -> r.getIdRol() == idRol)
						.collect(Collectors.toList()).get(0);
				usuario.setRol(rol);
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
	
	@Override
	public int editarUsuario(Usuario usuario){
		int salida = 0;
		Connection cn = null;
		PreparedStatement pstm = null;
		
		try {
			
			cn = MysqlDBConexion.getConexion();
			
			String sql = "call sp_actualizarUsuario(?,?,?,?,?)";
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, usuario.getNombres());
			pstm.setString(2, usuario.getApellidos());
			pstm.setString(3, usuario.getFechaNacimiento());
			pstm.setString(4, usuario.getEmail());
			pstm.setString(5, usuario.getIdUsuario());
			
			
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
	public int editarEstadoUsuario(String idUsuario, boolean estado){
		int salida = 0;
		Connection cn = null;
		PreparedStatement pstm = null;
		
		try {
			
			cn = MysqlDBConexion.getConexion();
			
			String sql = "UPDATE usuario SET estado=? WHERE idUsuario=?";
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
	public int eliminarUsuario(String id) {
		int salida = 0;
		Connection cn = null;
		PreparedStatement pstm = null;
		
		try {
			
			cn = MysqlDBConexion.getConexion();
			
			String sql = "DELETE FROM usuario WHERE idUsuario=?";
			
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, id);
			
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
