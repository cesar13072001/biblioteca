package entity;

import lombok.Data;

@Data
public class Prestamo {

	private int idPrestamo;
	private String fechaPrestamo;
	private String fechaVencimiento;
	private String fechaEntrega;
	private int idEstado;
	private String idUsuario;
	private String idLibro;
	
	private EstadoPrestamo estadoPrestamo;
	private Usuario usuario;
	private Libro libro;
}


