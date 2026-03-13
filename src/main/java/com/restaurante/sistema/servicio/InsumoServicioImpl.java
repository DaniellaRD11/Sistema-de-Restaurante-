package com.restaurante.sistema.servicio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurante.sistema.modelo.Insumo;
import com.restaurante.sistema.repositorio.InsumoRepositorio;

@Service
public class InsumoServicioImpl implements InsumoServicio {

    @Autowired
    private InsumoRepositorio insumoRepositorio;

    @Override
    public List<Insumo> listarTodos() {
        return insumoRepositorio.findAll();
    }

    @Override
    public Optional<Insumo> obtenerPorId(Integer id) {
        return insumoRepositorio.findById(id);
    }

    @Override
    public Insumo guardar(Insumo insumo) {
        // Verificar y actualizar el estado automáticamente
        if (insumo.getCantidadActual() == 0) {
            insumo.setEstado(Insumo.EstadoInsumo.AGOTADO);
        } else if (insumo.getCantidadActual() < insumo.getCantidadMinima()) {
            insumo.setEstado(Insumo.EstadoInsumo.BAJO);
        } else {
            insumo.setEstado(Insumo.EstadoInsumo.SUFICIENTE);
        }

        return insumoRepositorio.save(insumo);
    }

    @Override
    public void eliminar(Integer id) {
        insumoRepositorio.deleteById(id);
    }

   /* @Override
    public List<Insumo> obtenerBajoStock() {
        return insumoRepositorio.findByCantidadActualLessThan(10.0); // cantidad minima
    }
*/
    @Override
    public List<Insumo> obtenerBajoStock() {
        List<Insumo> todos = insumoRepositorio.findAll();
        return todos.stream()
                .filter(i -> i.getCantidadActual() < i.getCantidadMinima())
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Insumo> buscarPorNombre(String nombre) {
        return insumoRepositorio.findByNombreContainingIgnoreCase(nombre);
    }
    
    @Override
    public List<Insumo> obtenerPorRangoDeFechas(LocalDate fechaInicio, LocalDate fechaFin) {

        if (fechaInicio == null && fechaFin == null) {
            return insumoRepositorio.findAll();
        }

        if (fechaInicio == null) {
            fechaInicio = LocalDate.of(1900, 1, 1);
        }
        if (fechaFin == null) {
            fechaFin = LocalDate.now();
        }

        LocalDateTime desde = fechaInicio.atStartOfDay();
        LocalDateTime hasta = fechaFin.atTime(LocalTime.MAX);

        return insumoRepositorio.findByFechaActualizacionBetween(desde, hasta);
    }

}
