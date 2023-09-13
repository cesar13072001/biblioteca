package interfaces;

import java.util.List;

import entity.Prestamo;

public interface PrestamoDAO {

	public Prestamo registrarPrestamo(Prestamo prestamo);

	public int buscarDeudasUsuario(String idUsuario);

	public List<Prestamo> listadoPrestmamos();

	public Prestamo entregarLibro(int idPrestamo, String fechaEntrega, int idEstado);

	public int actualizarEstadoNoEntregado(int idPrestamo);

	public List<Integer> listarPrestamosAtrasados();

}
