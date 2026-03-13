package com.restaurante.sistema.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.restaurante.sistema.modelo.DetallePedido;
import com.restaurante.sistema.modelo.Pedido;
import com.restaurante.sistema.repositorio.DetallePedidoRepositorio;
import com.restaurante.sistema.repositorio.PedidoRepositorio;

@Controller
@RequestMapping("/detalle")
public class DetallePedidoControlador {

    @Autowired
    private DetallePedidoRepositorio detallePedidoRepository;
    
    @Autowired
    private PedidoRepositorio pedidoRepository;

    // --- ELIMINAR UN SOLO PLATO DEL PEDIDO ---
    @GetMapping("/eliminar/{id}")
    public String eliminarDetalle(@PathVariable("id") Integer idDetalle, RedirectAttributes redirectAttributes) {
        Integer idPedido = null;
        try {
            // 1. Buscamos el detalle para saber a qué pedido volver
            DetallePedido detalle = detallePedidoRepository.findById(idDetalle)
                    .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));
            
            // Guardamos el ID del pedido para redirigir al usuario allí después
            Pedido pedido = detalle.getPedido();
            idPedido = pedido.getPedidoId();

            // 2. Validar que no esté facturado ya
            if (detalle.getFactura() != null) {
                throw new RuntimeException("No se puede eliminar un plato que ya fue facturado.");
            }

            // 3. Restar el monto del total del pedido
            double nuevoTotal = pedido.getTotal() - detalle.getSubtotal();
            // Evitar negativos por error de redondeo
            if (nuevoTotal < 0) nuevoTotal = 0.0;
            
            pedido.setTotal(nuevoTotal);
            pedidoRepository.save(pedido);

            // 4. Eliminar el detalle
            detallePedidoRepository.delete(detalle);

            redirectAttributes.addFlashAttribute("exito", "Plato eliminado del pedido.");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar plato: " + e.getMessage());
        }

        // Si logramos obtener el ID del pedido, volvemos a su detalle. Si no, al listado general.
        if (idPedido != null) {
            return "redirect:/pedido/" + idPedido;
        } else {
            return "redirect:/pedido/Listar";
        }
    }
}