package com.restaurante.sistema.repositorio;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurante.sistema.modelo.Insumo;

@Repository
public interface InsumoRepositorio extends JpaRepository<Insumo, Integer> {

    List<Insumo> findByCantidadActualLessThan(Double cantidadMinima);
    
    List<Insumo> findByNombreContainingIgnoreCase(String nombre);
    
    List<Insumo> findByFechaActualizacionBetween(LocalDateTime desde, LocalDateTime hasta);

    

}
