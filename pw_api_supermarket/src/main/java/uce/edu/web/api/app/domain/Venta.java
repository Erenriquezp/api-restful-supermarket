package uce.edu.web.api.app.domain;

import java.util.ArrayList;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "ventas")
@SequenceGenerator(name = "ventas_seq", sequenceName = "ventas_sequence", allocationSize = 1)
public class Venta extends PanacheEntityBase {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ventas_seq")
   public Long id;

   public Long numVenta;
   public String cedula;
   public Double totalVenta;

   @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
   public List<DetalleVenta> detalle = new ArrayList<>();
}
