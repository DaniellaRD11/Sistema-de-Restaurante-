package com.restaurante.sistema.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "factura")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "factura_id")
    private Integer facturaId;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(name = "fecha_factura")
    private LocalDateTime fechaFactura;

    @Column(name = "total_factura")
    private Double total;

    @Column(name = "metodo_pago")
    private String metodoPago;

    // --- CORRECCIÓN: AHORA ES UN ENUM ---
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_factura")
    private EstadoFactura estadoFactura;

    @Column(name = "subtotal_base")
    private Double subtotalBase;

    @Column(name = "propina")
    private Double propina;

    @Column(name = "porcentaje_servicio")
    private Double porcentajeServicio;

    // --- DEFINICIÓN DEL ENUM ---
    public enum EstadoFactura {
        PAGADA,
        PENDIENTE,
        ANULADA
    }

    // --- CONSTRUCTOR ---
    public Factura() {
        this.fechaFactura = LocalDateTime.now();
        this.estadoFactura = EstadoFactura.PAGADA; // Valor por defecto del Enum
    }

    // --- GETTERS Y SETTERS ACTUALIZADOS ---

    public Integer getFacturaId() { return facturaId; }
    public void setFacturaId(Integer facturaId) { this.facturaId = facturaId; }

    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }

    public LocalDateTime getFechaFactura() { return fechaFactura; }
    public void setFechaFactura(LocalDateTime fechaFactura) { this.fechaFactura = fechaFactura; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    // Getter y Setter para el Enum
    public EstadoFactura getEstadoFactura() { return estadoFactura; }
    public void setEstadoFactura(EstadoFactura estadoFactura) { this.estadoFactura = estadoFactura; }

    public Double getSubtotalBase() { return subtotalBase; }
    public void setSubtotalBase(Double subtotalBase) { this.subtotalBase = subtotalBase; }

    public Double getPropina() { return propina; }
    public void setPropina(Double propina) { this.propina = propina; }

    public Double getPorcentajeServicio() { return porcentajeServicio; }
    public void setPorcentajeServicio(Double porcentajeServicio) { this.porcentajeServicio = porcentajeServicio; }
}