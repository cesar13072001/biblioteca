package entity;

import lombok.Data;

@Data
public class Prestamo {

	private int idPrestamo;
	private String fechaPrestamo;
	private String fechaVencimiento;
	private String fechaEntregado;
	private boolean estado;
	private boolean entregado;	
	private int idUsuario;
	private int idLibro;
}

