package interfaces;

import java.util.List;

import entity.Libro;

public interface LibroDAO {

	public List<Libro> listadoLibros();

	public Libro registrarLibro(Libro libro);

	public Libro buscarLibro(String idLibro);

	public Libro editarLibro(Libro libro);

	public int eliminarLibro(String idLibro);

	public int editarEstadoLibro(String idUsuario, boolean estado);

	public int actualizarCantidadReserva(String idLibro, int cantidad);

}
