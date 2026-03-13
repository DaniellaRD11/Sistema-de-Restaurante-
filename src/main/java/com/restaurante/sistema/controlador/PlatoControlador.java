package com.restaurante.sistema.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.restaurante.sistema.modelo.Plato;
import com.restaurante.sistema.repositorio.PlatoRepositorio;

@Controller
@RequestMapping("/menu")
public class PlatoControlador {
	
	@Autowired
    private PlatoRepositorio platoRepositorio;
	
	@GetMapping ("Listar")
    public String mostrarPaginaDeMenu(Model model) {
        
        List<Plato> listaDePlatos = platoRepositorio.findAll();
        model.addAttribute("listaPlatos", listaDePlatos);
        model.addAttribute("plato", new Plato());

        return "menu/lista_menu"; 
    }
	
	@PostMapping("/guardar")
    public String guardarPlato(@ModelAttribute Plato platoNuevo) {
        
        platoRepositorio.save(platoNuevo);
        return "redirect:/menu/Listar";
    }
	
	@GetMapping("/eliminar/{id}")
	public String eliminarPlato(@PathVariable("id") Integer platoId) {
	    platoRepositorio.deleteById(platoId);
	    return "redirect:/menu/Listar";
	}
	
	@GetMapping("/editar/{id}")
    public String mostrarFormularioDeEditar(@PathVariable("id") Integer platoId, Model model) {
        
        Plato platoParaEditar = platoRepositorio.findById(platoId).orElse(null);
        model.addAttribute("plato", platoParaEditar);
        return "menu/editar_plato";
    }
}