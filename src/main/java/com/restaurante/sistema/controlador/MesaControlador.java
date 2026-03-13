package com.restaurante.sistema.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.restaurante.sistema.modelo.Mesa;
import com.restaurante.sistema.servicio.MesaServicioImpl;

@RestController
@RequestMapping("/mesas")
public class MesaControlador {

	@Autowired
    private MesaServicioImpl mesaServicio;

    @GetMapping
    public List<Mesa> listarTodas() {
        return mesaServicio.listarTodas();
    }

    @GetMapping("/{id}")
    public Optional<Mesa> obtenerPorId(@PathVariable Integer id) {
        return mesaServicio.obtenerPorId(id);
    }

    @PostMapping
    public Mesa guardar(@RequestBody Mesa mesa) {
        return mesaServicio.guardar(mesa);
    }

    @PutMapping("/{id}")
    public Mesa actualizar(@PathVariable Integer id, @RequestBody Mesa mesa) {
        mesa.setMesaId(id);
        return mesaServicio.guardar(mesa);
    }
    
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        mesaServicio.eliminar(id);
    }
}
