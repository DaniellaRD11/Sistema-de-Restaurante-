package com.restaurante.sistema.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurante.sistema.modelo.Plato;
import com.restaurante.sistema.repositorio.PlatoRepositorio;

@Service
public class PlatoServicio {
       
	@Autowired
	private PlatoRepositorio platoRepositorio;
	
	public List<Plato> listarTodos() {
        return platoRepositorio.findAll();
    }

    public Optional<Plato> obtenerPorId(Integer id) {
        return platoRepositorio.findById(id);
    }

    public Plato guardar(Plato plato) {
        return platoRepositorio.save(plato);
    }

    public void eliminar(Integer id) {
        platoRepositorio.deleteById(id);
    }
}

