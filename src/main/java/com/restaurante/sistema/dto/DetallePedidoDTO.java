package com.restaurante.sistema.dto;

public class DetallePedidoDTO {

	private Integer platoId; 
    private Integer cantidad;
    
    private String nombrePlato;
    private Double precioUnitario;
    
	public Integer getPlatoId() {
		return platoId;
	}
	public void setPlatoId(Integer platoId) {
		this.platoId = platoId;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public String getNombrePlato() {
		return nombrePlato;
	}
	public void setNombrePlato(String nombrePlato) {
		this.nombrePlato = nombrePlato;
	}
	public Double getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	
    
    
}
