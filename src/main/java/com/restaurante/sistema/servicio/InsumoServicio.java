package com.restaurante.sistema.servicio;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.restaurante.sistema.modelo.Insumo;


public interface InsumoServicio {
	
	List<Insumo> listarTodos();

    Optional<Insumo> obtenerPorId(Integer id);

    Insumo guardar(Insumo insumo);

    void eliminar(Integer id);

    List<Insumo> obtenerBajoStock();

    List<Insumo> buscarPorNombre(String nombre);
    
    List<Insumo> obtenerPorRangoDeFechas(LocalDate fechaInicio, LocalDate fechaFin);


}
