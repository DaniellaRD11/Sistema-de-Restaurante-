package com.restaurante.sistema.servicio;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurante.sistema.dto.DetallePedidoDTO;
import com.restaurante.sistema.dto.PedidoDTO;
import com.restaurante.sistema.modelo.DetallePedido;
import com.restaurante.sistema.modelo.Mesa;
import com.restaurante.sistema.modelo.Pedido;
import com.restaurante.sistema.modelo.Plato;
import com.restaurante.sistema.repositorio.DetallePedidoRepositorio;
import com.restaurante.sistema.repositorio.MesaRepositorio;
import com.restaurante.sistema.repositorio.PedidoRepositorio;
import com.restaurante.sistema.repositorio.PlatoRepositorio;

@Service
public class PedidoServicioImpl implements PedidoServicio {

    @Autowired
    private PedidoRepositorio pedidoRepository;
    @Autowired
    private MesaRepositorio mesaRepository;
    @Autowired
    private PlatoRepositorio platoRepository;
    @Autowired
    private DetallePedidoRepositorio detallePedidoRepository;

    @Override
    public List<Pedido> getAllPedido() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido obtenerPedidoPorId(Integer id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado ID: " + id));
    }

    @Override
    public List<Plato> getPlatosDisponibles() {
        return platoRepository.findAll(); // Puedes filtrar por estado si tuvieras
    }

    @Override
    public List<Mesa> getMesasDisponibles() {
        // Devuelve todas o solo las disponibles según prefieras
        return mesaRepository.findAll(); 
    }

    // --- LÓGICA DE REGISTRO MAESTRO-DETALLE ---
    @Override
    @Transactional
    public void registrarPedidoConDetalles(PedidoDTO dto) {
        // 1. Validar Mesa
        Mesa mesa = mesaRepository.findById(dto.getMesaId())
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));

        // 2. Crear Cabecera Pedido
        Pedido pedido = new Pedido();
        pedido.setMesa(mesa);
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado(Pedido.EstadoPedido.ABIERTO);
        pedido = pedidoRepository.save(pedido);

        // 3. Guardar Detalles
        double total = 0.0;
        if (dto.getDetalles() != null) {
            for (DetallePedidoDTO detDTO : dto.getDetalles()) {
                Plato plato = platoRepository.findById(detDTO.getPlatoId())
                        .orElseThrow(() -> new RuntimeException("Plato no encontrado"));

                DetallePedido detalle = new DetallePedido();
                detalle.setPedido(pedido);
                detalle.setPlato(plato);
                detalle.setCantidad(detDTO.getCantidad());
                
                double subtotal = plato.getPrecio() * detDTO.getCantidad();
                detalle.setSubtotal(subtotal);
                
                detallePedidoRepository.save(detalle);
                total += subtotal;
            }
        }
        
        // 4. Actualizar Total y Estado de Mesa
        pedido.setTotal(total);
        pedidoRepository.save(pedido);
        
        mesa.setEstado(Mesa.EstadoMesa.OCUPADO); // Marcamos mesa como OCUPADA
        mesaRepository.save(mesa);
    }

    // --- LÓGICA DE AGREGAR PLATO INDIVIDUAL ---
    @Override
    @Transactional
    public void agregarPlatoAlPedido(Integer pedidoId, Integer platoId, Integer cantidad) {
        Pedido pedido = obtenerPedidoPorId(pedidoId);
        Plato plato = platoRepository.findById(platoId)
                .orElseThrow(() -> new RuntimeException("Plato no encontrado"));

        DetallePedido detalle = new DetallePedido();
        detalle.setPedido(pedido);
        detalle.setPlato(plato);
        detalle.setCantidad(cantidad);
        double subtotal = plato.getPrecio() * cantidad;
        detalle.setSubtotal(subtotal);
        
        detallePedidoRepository.save(detalle);

        // Actualizar total del pedido
        double totalActual = (pedido.getTotal() == null) ? 0.0 : pedido.getTotal();
        pedido.setTotal(totalActual + subtotal);
        pedidoRepository.save(pedido);
    }

    // --- SOLUCIÓN AL PROBLEMA DE ELIMINAR ---
    @Override
    @Transactional
    public void eliminarPedido(Integer id) {
        Pedido pedido = obtenerPedidoPorId(id);

        // 1. Borrar detalles primero (Para evitar error de FK)
        if (!pedido.getDetalles().isEmpty()) {
            detallePedidoRepository.deleteAll(pedido.getDetalles());
        }

        // 2. Liberar la Mesa (ESTO ES LO QUE TE FALTABA)
        if (pedido.getMesa() != null) {
            Mesa mesa = pedido.getMesa();
            mesa.setEstado(Mesa.EstadoMesa.DISPONIBLE);
            mesaRepository.save(mesa);
        }

        // 3. Borrar el pedido
        pedidoRepository.delete(pedido);
    }
}