package entity;

import lombok.Data;


@Data
public class Libro {

	private String idLibro;
	
	private String titulo;
	
	private String descripcion;
	
	private Boolean estado;
	
	private int cantidad;
	
	private int idCategoria;
	
	private Categoria categoria;
}

