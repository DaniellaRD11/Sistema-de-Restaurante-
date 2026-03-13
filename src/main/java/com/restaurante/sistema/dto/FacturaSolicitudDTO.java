package com.restaurante.sistema.dto;

import java.util.List;

public class FacturaSolicitudDTO {

    // 1. ID del Pedido al que pertenece la cuenta
    private Integer pedidoId;

    // 2. Lista de IDs de los platos (DetallePedido) que el cliente va a pagar ahora
    // (Esto permite pagar solo una parte de la mesa si se desea)
    private List<Integer> detallesIds; 

    // 3. Forma de pago (Efectivo, Tarjeta, etc.)
    private String metodoPago;

    // --- CONSTRUCTOR VACÍO (Necesario para que Spring convierta el JSON) ---
    public FacturaSolicitudDTO() {
    }

    // --- GETTERS Y SETTERS ---
    public Integer getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Integer pedidoId) {
        this.pedidoId = pedidoId;
    }

    public List<Integer> getDetallesIds() {
        return detallesIds;
    }

    public void setDetallesIds(List<Integer> detallesIds) {
        this.detallesIds = detallesIds;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
}