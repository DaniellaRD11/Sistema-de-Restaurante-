package com.restaurante.sistema.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurante.sistema.modelo.Factura;

@Repository
public interface FacturaRepositorio extends JpaRepository<Factura, Integer> {
    
    // Método personalizado: Encontrar todas las facturas asociadas a un pedido específico
    List<Factura> findByPedido_PedidoId(Integer pedidoId);
}
