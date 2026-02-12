package uce.edu.web.api.app.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "producto")
@SequenceGenerator(name = "producto_seq", sequenceName = "producto_sequence", allocationSize = 1)
public class Producto extends PanacheEntityBase {
   
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "producto_seq")
   public Long id;

   public String codigoBarras;
   public String nombre;
   public String categoria;
   public Integer stock;
   public Double precio;
}
