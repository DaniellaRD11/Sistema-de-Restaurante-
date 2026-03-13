package com.restaurante.sistema.modelo;


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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "platos")
public class Plato {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Plato_id")
	private Integer Id;
	
	private String nombre;
	private String descripcion;
	
	@OneToMany(mappedBy = "plato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Detallereceta> detallesReceta = new ArrayList<>();
	
	@Enumerated(EnumType.STRING)
	private EstadoPlato disponible;
	
   public enum EstadoPlato{ Disponible,Agotado}
	
	private Double precio;
	private String categoria;
	
	
	public Integer getPlatoId() {
		return Id;
	}
	public void setPlatoId(Integer platoId) {
		this.Id = platoId;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public EstadoPlato getDisponible() {
        return disponible;
    }
    public void setDisponible(EstadoPlato disponible) {
        this.disponible = disponible;
    }
	public List<Detallereceta> getDetallesReceta() {
		return detallesReceta;
	}
	public void setDetallesReceta(List<Detallereceta> detallesReceta) {
		this.detallesReceta = detallesReceta;
	}
	

}
