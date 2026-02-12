package uce.edu.web.api.app.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import uce.edu.web.api.app.domain.DetalleVenta;

@ApplicationScoped
public class DetalleVentaRepository implements PanacheRepository<DetalleVenta> {
   
}
