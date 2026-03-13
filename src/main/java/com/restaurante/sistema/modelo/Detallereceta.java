package com.restaurante.sistema.modelo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "detalle_receta")
public class Detallereceta {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column ( name = "detalleR_id")
	    private Integer Id;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "plato_id", nullable = false)
	    private Plato plato;

	    // Relación Many-to-One con Insumo
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "insumo_id", nullable = false)
	    private Insumo insumo;
	    
	    
	    @Column(name = "cantidad_uso", nullable = false)
	    private Double cantidadUso; // Cantidad del insumo que gasta el plato

		public Integer getDetallerId() {
			return Id;
		}

		public void setDetallerId(Integer detalleRId) {
			this.Id = detalleRId;
		}

		public Plato getPlato() {
			return plato;
		}

		public void setPlato(Plato plato) {
			this.plato = plato;
		}

		public Insumo getInsumo() {
			return insumo;
		}

		public void setInsumo(Insumo insumo) {
			this.insumo = insumo;
		}

		public Double getCantidadUso() {
			return cantidadUso;
		}

		public void setCantidadUso(Double cantidadUso) {
			this.cantidadUso = cantidadUso;
		}

	   
	
}
