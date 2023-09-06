package interfaces;

import java.util.List;

import entity.Categoria;

public interface CategoriaDAO {

	public List<Categoria> listadoCategoria();

	public Categoria registrarCategoria(Categoria categoria);

	public Categoria buscarCategoria(int idCategoria);

	public int editarCategoria(Categoria categoria);

	public int eliminarCategoria(int idCategoria);

}
