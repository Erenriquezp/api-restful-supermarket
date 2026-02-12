package uce.edu.web.api.app.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name = "detalle_venta")
@SequenceGenerator(name = "detalle_seq", sequenceName = "detalle_sequence", allocationSize = 1)
public class DetalleVenta extends PanacheEntityBase {
   
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "detalle_seq")
   public Long id;

   public Integer cantidad;
   public Double precioUnitario;
   public Double subTotal;

   @ManyToOne
   @JoinColumn(name = "venta_id")
   @JsonbTransient
   public Venta venta;

   @ManyToOne
   @JoinColumn(name = "producto_id")
   public Producto producto;
}
