package interfaces;

import java.util.List;

import entity.Usuario;

public interface UsuarioDAO {

	public List<Usuario> listadoUsuario();

	public Usuario buscarUsuario(String idUsuario);

	public Usuario registrarUsuario(Usuario usuario);

	public int editarUsuario(Usuario usuario);

	public int editarEstadoUsuario(String idUsuario, boolean estado);
	
	public int eliminarUsuario(String id);

	

}
