package com.restaurante.sistema.dto;

import java.util.Date;

public class FacturaReporteDTO {
    
    // NOTA: Estos nombres coinciden con los <field> de tu archivo .jrxml
    private Integer id;
    private Date fecha;             
    private String nombrePlato;     
    private Integer cantidad;       
    private Double precioUnitario;  
    private Integer numeroMesa;     
    private Double total;           
    private String estado;          

    // Constructor vacío (Necesario para Jasper)
    public FacturaReporteDTO() {}

    // Constructor completo (Usado por el Servicio)
    public FacturaReporteDTO(Integer id, Date fecha, String nombrePlato, Integer cantidad, 
                             Double precioUnitario, Integer numeroMesa, Double total, String estado) {
        this.id = id;
        this.fecha = fecha;
        this.nombrePlato = nombrePlato;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.numeroMesa = numeroMesa;
        this.total = total;
        this.estado = estado;
    }

    // --- GETTERS Y SETTERS ---

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    
    public String getNombrePlato() { return nombrePlato; }
    public void setNombrePlato(String nombrePlato) { this.nombrePlato = nombrePlato; }
    
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    
    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }
    
    public Integer getNumeroMesa() { return numeroMesa; }
    public void setNumeroMesa(Integer numeroMesa) { this.numeroMesa = numeroMesa; }
    
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}