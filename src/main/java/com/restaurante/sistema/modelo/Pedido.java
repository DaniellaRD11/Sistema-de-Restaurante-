package com.restaurante.sistema.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedido")
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column ( name= "pedido_id")
	private Integer pedidoId;
	
	
	private LocalDateTime fecha;
	
	@Enumerated(EnumType.STRING)
	private EstadoPedido estado;
	
	public enum EstadoPedido{ ABIERTO, CERRADO}

	private Double subtotal; // Costo solo de los platos
	private Double total;

	
	/*@ManyToOne
    @JoinColumn(name = "usuario_id") // Puede ser null si aún no se asigna
    private Usuario usuario;*/
	
	 @ManyToOne
	    @JoinColumn(name = "mesa_id") 
	    private Mesa mesa;
	
	 @OneToMany( mappedBy = "pedido", cascade = CascadeType.ALL)
		private List<DetallePedido> detalles;
		
	 @OneToMany(mappedBy = "pedido")
	 private List<Factura> facturas = new ArrayList<>();;
	    

	    public Pedido() {
	        // Inicializar fecha y estado por defecto al crear el objeto
	        this.fecha = LocalDateTime.now();
	        this.estado = EstadoPedido.ABIERTO;
	    }
		
	 

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}

	public Integer getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Integer pedidoId) {
		this.pedidoId = pedidoId;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public EstadoPedido getEstado() {
		return estado;
	}

	public void setEstado(EstadoPedido estado) {
		this.estado = estado;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public List<DetallePedido> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetallePedido> detalles) {
		this.detalles = detalles;
	}
	
	public void agregarDetalle(DetallePedido detalle) {
        detalles.add(detalle);
        detalle.setPedido(this);
    }

}
