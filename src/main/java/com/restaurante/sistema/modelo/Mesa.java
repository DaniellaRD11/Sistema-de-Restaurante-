package com.restaurante.sistema.modelo;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;



@Entity
@Table(name = "mesa")
public class Mesa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column( name= "mesa_id")
	private Integer mesaId;
	
	@Column( name = "numero_mesa")
	private Integer numeroMesa;
	
	private Integer capacidad;
	
	@Enumerated(EnumType.STRING)
	private EstadoMesa estado;
	
	@OneToMany( mappedBy = "mesa")
	private List<Pedido> pedidos;
	
	public enum EstadoMesa { DISPONIBLE,OCUPADO,RESERVADO}

	public Integer getMesaId() {
		return mesaId;
	}

	public void setMesaId(Integer mesaId) {
		this.mesaId = mesaId;
	}

	public Integer getNumeroMesa() {
		return numeroMesa;
	}

	public void setNumeroMesa(Integer numeroMesa) {
		this.numeroMesa = numeroMesa;
	}

	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

	public EstadoMesa getEstado() {
		return estado;
	}


	public void setEstado(EstadoMesa estado) {
		this.estado = estado;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	
}
