package entity;





import lombok.Data;

@Data
public class Usuario {
	
	private String idUsuario;
	
	private String nombres;
	
	private String apellidos;
	
	private String fechaNacimiento;
	
	private String email;
	
	private String password;
	
	private String fechaRegistro;
	
	private Boolean estado;
	
	private int idRol;
	
	private Rol rol;
	
	

}
