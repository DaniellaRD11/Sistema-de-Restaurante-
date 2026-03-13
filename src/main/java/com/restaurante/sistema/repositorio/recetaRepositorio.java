package com.restaurante.sistema.repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.restaurante.sistema.modelo.Detallereceta;
import com.restaurante.sistema.modelo.Plato;


public interface recetaRepositorio {

	public interface RecetaRepository extends JpaRepository<Detallereceta, Long> {
	    // Método clave para el descargo de inventario: encuentra la receta de un plato
	    List<Detallereceta> findByPlato(Plato plato);
	}
}