package com.restaurante.sistema.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurante.sistema.modelo.Plato;

@Repository
public interface PlatoRepositorio extends JpaRepository<Plato, Integer> {
	
	public List<Plato> findByDisponible(Plato.EstadoPlato estado);
	
	List<Plato> findByCategoria(String categoria);
}
