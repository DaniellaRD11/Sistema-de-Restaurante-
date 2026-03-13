package com.restaurante.sistema.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.restaurante.sistema.modelo.Mesa;
import com.restaurante.sistema.repositorio.MesaRepositorio;

@Service
public class MesaServicioImpl  implements MesaServicio{

	private final MesaRepositorio mesaRepositorio;

	public MesaServicioImpl(MesaRepositorio mesaRepositorio) {
		this.mesaRepositorio = mesaRepositorio;
	}
        
	@Override
    public List<Mesa> getMesasDisponibles() {
        return mesaRepositorio.findMesasDisponibles();
    }
    public List<Mesa> listarTodas() {
        return mesaRepositorio.findAll();
    }

    public Optional<Mesa> obtenerPorId(Integer id) {
        return mesaRepositorio.findById(id);
    }

    public Mesa guardar(Mesa mesa) {
        return mesaRepositorio.save(mesa);
    }

    public void eliminar(Integer id) {
        mesaRepositorio.deleteById(id);
    }
	
    public List<Mesa> listarMesasDisponibles() {
    	return mesaRepositorio.findMesasDisponibles();
    }
}
