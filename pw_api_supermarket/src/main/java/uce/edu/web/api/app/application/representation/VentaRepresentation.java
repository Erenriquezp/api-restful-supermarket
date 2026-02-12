package uce.edu.web.api.app.application.representation;

import java.util.ArrayList;
import java.util.List;

public class VentaRepresentation {
   public Long numVenta;
   public String cedula;
   public Double totalVenta;
   public List<DetalleVentaRepresentation> detalle = new ArrayList<>();
}
