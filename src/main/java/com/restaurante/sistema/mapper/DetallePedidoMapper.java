package com.restaurante.sistema.mapper;

import java.util.List;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.restaurante.sistema.dto.DetallePedidoDTO;
import com.restaurante.sistema.modelo.DetallePedido;

public interface DetallePedidoMapper {
	

	DetallePedidoMapper INSTANCE = Mappers.getMapper(DetallePedidoMapper.class);

    @Mapping(source = "plato.id", target = "platoId")
    @Mapping(source = "plato.nombre", target = "nombrePlato")
    DetallePedidoDTO toDTO(DetallePedido detalle);

    List<DetallePedidoDTO> toDTOs(List<DetallePedido> detalles);
}

