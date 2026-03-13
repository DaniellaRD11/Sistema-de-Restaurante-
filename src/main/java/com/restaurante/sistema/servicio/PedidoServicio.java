package com.restaurante.sistema.servicio;

import java.util.List;
import com.restaurante.sistema.dto.PedidoDTO;
import com.restaurante.sistema.modelo.Mesa;
import com.restaurante.sistema.modelo.Pedido;
import com.restaurante.sistema.modelo.Plato;

public interface PedidoServicio {

    // Listar todos
    List<Pedido> getAllPedido();

    // Obtener uno (Directo, sin Optional, para facilitar el controlador)
    Pedido obtenerPedidoPorId(Integer id);

    // Listas auxiliares
    List<Plato> getPlatosDisponibles();
    List<Mesa> getMesasDisponibles(); 

    // Métodos principales de Lógica
    void registrarPedidoConDetalles(PedidoDTO pedidoDTO);
    
    void agregarPlatoAlPedido(Integer pedidoId, Integer platoId, Integer cantidad);

    // Este es el que arreglaremos para que libere la mesa
    void eliminarPedido(Integer id);
}