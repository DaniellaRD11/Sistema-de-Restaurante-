package com.restaurante.sistema.modelo;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "insumo")
public class Insumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "insumo_id")
    private long Id;

  
    private String nombre;
    private String descripcion;
    private String unidad;

    @Column(name = "stock_actual")
    private Double cantidadActual = 0.0;

    @Column(name = "stock_minimo")
    private Double cantidadMinima;


    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoInsumo estado;
    
    @Column(name = "costo_unitario")
    private Double costoUnitario;
    
    

    public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public Double getCostoUnitario() {
		return costoUnitario;
	}

	public void setCostoUnitario(Double costoUnitario) {
		this.costoUnitario = costoUnitario;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

    public Insumo() {
    }

    public Insumo(LocalDateTime fechaActualizacion, EstadoInsumo estado) {
        this.fechaActualizacion = fechaActualizacion;
        this.estado = estado;
    }

    public enum EstadoInsumo {
        SUFICIENTE,
        BAJO,
        AGOTADO
    }

    public long getInsumoId() {
        return Id;
    }

    public void setInsumoId(long insumoId) {
        this.Id = insumoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Double getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(Double cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public Double getCantidadMinima() {
        return cantidadMinima;
    }

    public void setCantidadMinima(Double cantidadMinima) {
        this.cantidadMinima = cantidadMinima;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public EstadoInsumo getEstado() {
        return estado;
    }

    public void setEstado(EstadoInsumo estado) {
        this.estado = estado;
    }
}
	