package interfaces;

import entity.Usuario;

public interface AuthDAO {
	
	public Usuario loginUsuario(String email, String password);


}
