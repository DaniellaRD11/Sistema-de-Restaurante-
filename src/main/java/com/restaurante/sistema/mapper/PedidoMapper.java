package com.restaurante.sistema.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.restaurante.sistema.dto.PedidoDTO;
import com.restaurante.sistema.modelo.Mesa;
import com.restaurante.sistema.modelo.Pedido;

@Mapper(componentModel = "spring")
public interface PedidoMapper {


    @Mappings({
        @Mapping(target = "id", source = "pedidoId"),
        @Mapping(target = "mesaId", source = "mesa.mesaId"),
        @Mapping(target = "numeroMesa", source = "mesa.numeroMesa")
    })
    PedidoDTO toPedidoDTO(Pedido pedido);

    @Mappings({
        @Mapping(target = "pedidoId", source = "id"),
        @Mapping(target = "mesa", expression = "java(mapMesaIdToMesa(dto.getMesaId()))"),
        @Mapping(target = "detalles", ignore = true)
    })
    Pedido toPedido(PedidoDTO dto);


    default Mesa mapMesaIdToMesa(Integer mesaId) {
        if (mesaId == null) {
            return null;
        }
        Mesa mesa = new Mesa();
        mesa.setMesaId(mesaId);
        return mesa;
    }
}
