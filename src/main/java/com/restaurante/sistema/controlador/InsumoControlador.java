package com.restaurante.sistema.controlador;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.restaurante.sistema.modelo.Insumo;
import com.restaurante.sistema.modelo.Insumo.EstadoInsumo;
import com.restaurante.sistema.servicio.InsumoServicio;

@Controller
@RequestMapping("/insumo")
public class InsumoControlador {

    @Autowired
    private InsumoServicio insumoServicio;

   
    @GetMapping("/lista")
    public String listarInsumos(Model model) {
        List<Insumo> insumos = insumoServicio.listarTodos();
        model.addAttribute("insumos", insumos);
        return "insumo/listaInsumo"; 
    }

    
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("insumo", new Insumo());
        return "insumo/formInsumo"; 
    }

    
    @PostMapping("/guardar")
    public String guardarInsumo(@ModelAttribute("insumo") Insumo insumo) {
        insumoServicio.guardar(insumo);
        return "redirect:/insumo/lista";
    }

    
    @GetMapping("/editar/{id}")
    public String editarInsumo(@PathVariable Integer id, Model model) {
        Optional<Insumo> insumoOpt = insumoServicio.obtenerPorId(id);
        if (insumoOpt.isPresent()) {
            model.addAttribute("insumo", insumoOpt.get());
            return "insumo/formInsumo";
        } else {
            return "redirect:/insumo/lista";
        }
    }

    
    @GetMapping("/eliminar/{id}")
    public String eliminarInsumo(@PathVariable Integer id) {
        insumoServicio.eliminar(id);
        return "redirect:/insumo/lista";
    }

    @GetMapping("/bajo-stock")
    public String verInsumosBajoStock(Model model) {
        List<Insumo> bajoStock = insumoServicio.obtenerBajoStock();
        model.addAttribute("insumos", bajoStock);
        model.addAttribute("titulo", "Insumos con Bajo Stock");
        return "insumo/listaInsumo"; 
    }

   
    @GetMapping("/buscar")
    public String buscarInsumos(@RequestParam("nombre") String nombre, Model model) {
        List<Insumo> resultados = insumoServicio.buscarPorNombre(nombre);
        model.addAttribute("insumos", resultados);
        model.addAttribute("titulo", "Resultados de búsqueda para: " + nombre);
        return "insumo/listaInsumos";
    }
    
    @GetMapping("/reporte")
    public String generarReporte(
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            Model model) {

        List<Insumo> insumos = insumoServicio.obtenerPorRangoDeFechas(fechaInicio, fechaFin);

        long agotados = insumos.stream().filter(i -> i.getEstado() == EstadoInsumo.AGOTADO).count();
        long bajos = insumos.stream().filter(i -> i.getEstado() == EstadoInsumo.BAJO).count();
        long suficientes = insumos.stream().filter(i -> i.getEstado() == EstadoInsumo.SUFICIENTE).count();

        model.addAttribute("insumos", insumos);
        model.addAttribute("agotados", agotados);
        model.addAttribute("bajos", bajos);
        model.addAttribute("suficientes", suficientes);

        return "insumo/reporteInventario";
    }

}

