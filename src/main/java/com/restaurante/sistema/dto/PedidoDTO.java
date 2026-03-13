package com.restaurante.sistema.dto;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.restaurante.sistema.modelo.Pedido.EstadoPedido;

public class PedidoDTO {

	private Integer id;
    private EstadoPedido estado;
    private double total;
    private Double propina;
    private Double porcentajeServicio;
    private LocalDateTime fecha;
    private Integer mesaId;
    private int numeroMesa;
    
    private List<DetallePedidoDTO> detalles = new ArrayList<>();
 

    public List<DetallePedidoDTO> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetallePedidoDTO> detalles) {
		this.detalles = detalles;
	}

	public Double getPropina() {
		return propina;
	}

	public void setPropina(Double propina) {
		this.propina = propina;
	}

	public Double getPorcentajeServicio() {
		return porcentajeServicio;
	}

	public void setPorcentajeServicio(Double porcentajeServicio) {
		this.porcentajeServicio = porcentajeServicio;
	}


	public PedidoDTO() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EstadoPedido getEstado() {
		return estado;
	}

	public void setEstado(EstadoPedido estado) {
		this.estado = estado;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public Integer getMesaId() {
		return mesaId;
	}

	public void setMesaId(Integer mesaId) {
		this.mesaId = mesaId;
	}

	public int getNumeroMesa() {
		return numeroMesa;
	}

	public void setNumeroMesa(int numeroMesa) {
		this.numeroMesa = numeroMesa;
	}
    
    
}
