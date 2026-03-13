package com.restaurante.sistema.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_pedido")
public class DetallePedido {
	
	@Id 
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "detallep_id")
	private Integer detallepId;
	
	
	private Integer cantidad;
	
	@Column ( name = "precio_unitario")
	private Double precioUnitario;
	
	// RELACIÓN CON PLATOS
	@ManyToOne
	@JoinColumn ( name = "plato_id")
	private Plato plato;
	
	// RELACIÓN CON PEDIDO (Muchos detalles pertenecen a un pedido)
	@ManyToOne
	@JoinColumn( name = "pedido_id")
	private Pedido pedido;
	
	  // subtotal = precioUnitario * cantidad
    private Double subtotal;

    //RELACIÓN CON FACTURA
    @ManyToOne
    @JoinColumn(name = "factura_id", nullable = true)
    private Factura factura;

    
   
	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public Integer getDetallepId() {
		return detallepId;
	}

	public void setDetallepId(Integer detallepId) {
		this.detallepId = detallepId;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public Plato getPlato() {
		return plato;
	}

	public void setPlato(Plato plato) {
		this.plato = plato;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	public DetallePedido() {
		
    }
	
}
