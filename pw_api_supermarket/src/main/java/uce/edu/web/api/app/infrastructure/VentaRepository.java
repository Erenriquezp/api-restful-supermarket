package uce.edu.web.api.app.infrastructure;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import uce.edu.web.api.app.domain.Venta;

@ApplicationScoped
public class VentaRepository implements PanacheRepository<Venta> {

   public Venta findByNumeroVenta(Long nunVenta) {
      return find("numVenta", nunVenta).firstResult();
   }

   public List<Venta> listAllWithDetalle() {
      return this.find("select distinct v from Venta v left join fetch v.detalle d left join fetch d.producto")
            .list();
   }
}
