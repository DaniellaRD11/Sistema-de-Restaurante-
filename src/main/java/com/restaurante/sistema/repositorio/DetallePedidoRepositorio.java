package com.restaurante.sistema.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurante.sistema.modelo.DetallePedido;
import com.restaurante.sistema.modelo.Pedido;

@Repository
public interface DetallePedidoRepositorio extends JpaRepository<DetallePedido, Integer> {

    // ✅ ESTA ES LA LÍNEA MÁGICA QUE TE FALTA
    // Spring crea automáticamente la consulta: "SELECT * FROM detalle_pedido WHERE pedido_id = ?"
    List<DetallePedido> findByPedido(Pedido pedido);
    
}
