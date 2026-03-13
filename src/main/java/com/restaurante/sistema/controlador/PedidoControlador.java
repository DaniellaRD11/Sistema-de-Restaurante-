package com.restaurante.sistema.controlador;

import com.restaurante.sistema.dto.PedidoDTO;
import com.restaurante.sistema.modelo.Pedido;
import com.restaurante.sistema.servicio.PedidoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pedido")
public class PedidoControlador {

    @Autowired
    private PedidoServicio pedidoServicio;

    // --- 1. LISTAR PEDIDOS ---
    @GetMapping("/Listar")
    public String listarPedidos(Model model) {
        model.addAttribute("pedidos", pedidoServicio.getAllPedido());
        return "pedido/menupedido"; 
    }
    
    // --- 2. FORMULARIO NUEVO PEDIDO ---
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoPedido(Model model) {
        PedidoDTO pedidoDTO = new PedidoDTO(); 
        model.addAttribute("pedidoDTO", pedidoDTO);
        
        // Usamos pedidoServicio para todo (ya incluye las mesas disponibles)
        model.addAttribute("mesas", pedidoServicio.getMesasDisponibles()); 
        model.addAttribute("platos", pedidoServicio.getPlatosDisponibles());
        
        return "pedido/registroPedido";
    }
    
    // --- 3. GUARDAR PEDIDO (Maestro-Detalle) ---
    @PostMapping("/guardar")
    public String guardarPedido(@ModelAttribute("pedidoDTO") PedidoDTO pedidoDTO,
                                RedirectAttributes redirectAttributes) {
        try {
            // Este método guarda mesa, pedido y detalles de una vez
            pedidoServicio.registrarPedidoConDetalles(pedidoDTO);
            redirectAttributes.addFlashAttribute("exito", "¡✅ Pedido registrado correctamente!");
            return "redirect:/pedido/Listar";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "❌ Error al guardar: " + e.getMessage());
            return "redirect:/pedido/nuevo";
        }
    }

    // --- 4. VER DETALLE (Gestión y Facturación) ---
    @GetMapping("/{id}") 
    public String verDetallePedido(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            // El servicio lanza excepción si no encuentra el pedido, así que atrapamos el error
            Pedido pedido = pedidoServicio.obtenerPedidoPorId(id);
            model.addAttribute("pedido", pedido);
            
            // Enviamos platos por si quieren agregar más items manualmente
            model.addAttribute("platos", pedidoServicio.getPlatosDisponibles());
            
            return "pedido/pedidodetalle"; 
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
            return "redirect:/pedido/Listar";
        }
    }

    // --- 5. AGREGAR PLATO INDIVIDUAL ---
    @PostMapping("/agregarPlato")
    public String agregarPlato(@RequestParam Integer pedidoId,
                               @RequestParam Integer platoId,
                               @RequestParam Integer cantidad,
                               RedirectAttributes redirectAttributes) {
        try {
            pedidoServicio.agregarPlatoAlPedido(pedidoId, platoId, cantidad);
            redirectAttributes.addFlashAttribute("exito", "✅ Plato agregado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "❌ Error al agregar plato: " + e.getMessage());
        }
        return "redirect:/pedido/" + pedidoId;
    }
  
    // --- 6. ELIMINAR PEDIDO (Y liberar mesa) ---
    @GetMapping("/eliminar/{id}")
    public String eliminarPedido(@PathVariable("id") Integer id,
                                 RedirectAttributes redirectAttributes) {
        try {
            pedidoServicio.eliminarPedido(id);
            redirectAttributes.addFlashAttribute("exito", "Pedido eliminado y mesa liberada.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar: " + e.getMessage());
        }
        return "redirect:/pedido/Listar";
    }
}