package com.restaurante.sistema.repositorio;

import com.restaurante.sistema.modelo.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PedidoRepositorio extends JpaRepository<Pedido, Integer> {
}


