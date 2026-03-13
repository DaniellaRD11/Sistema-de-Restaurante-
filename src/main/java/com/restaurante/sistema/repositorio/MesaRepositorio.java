package com.restaurante.sistema.repositorio;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.restaurante.sistema.modelo.Mesa;

@Repository
public interface MesaRepositorio extends JpaRepository<Mesa, Integer> {

	@Query("SELECT m FROM Mesa m WHERE m.mesaId NOT IN " +
	           "(SELECT p.mesa.mesaId FROM Pedido p WHERE p.estado = 'ABIERTO')")
	    List<Mesa> findMesasDisponibles();
    
} 
