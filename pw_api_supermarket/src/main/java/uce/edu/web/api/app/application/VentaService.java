package uce.edu.web.api.app.application;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uce.edu.web.api.app.application.representation.DetalleVentaRepresentation;
import uce.edu.web.api.app.application.representation.VentaRepresentation;
import uce.edu.web.api.app.domain.DetalleVenta;
import uce.edu.web.api.app.domain.Producto;
import uce.edu.web.api.app.domain.Venta;
import uce.edu.web.api.app.infrastructure.ProductoRepository;
import uce.edu.web.api.app.infrastructure.VentaRepository;

@ApplicationScoped
public class VentaService {
   @Inject
   private VentaRepository ventaRepository;

   @Inject
   private ProductoRepository productoRepository;

   @Transactional
   public void crear(VentaRepresentation ventaR) {
      Venta venta = new Venta();
      venta.numVenta = ventaR.numVenta;
      venta.cedula = ventaR.cedula;
      venta.totalVenta = 0.0;
      // No es necesario inicializar venta.detalle si ya lo haces en la entidad

      Double total = 0.0;

      for (DetalleVentaRepresentation detalleR : ventaR.detalle) {
         Producto producto = this.productoRepository.findByCodigoBarras(detalleR.codigoBarras);

         // Validación de existencia y stock
         if (producto == null) {
            throw new RuntimeException("Producto no encontrado: " + detalleR.codigoBarras);
         }

         if (producto.stock < detalleR.cantidad) {
            throw new RuntimeException("Stock insuficiente para: " + producto.nombre);
         }

         DetalleVenta detalle = new DetalleVenta();
         detalle.cantidad = detalleR.cantidad;
         detalle.precioUnitario = producto.precio;
         detalle.subTotal = producto.precio * detalleR.cantidad;

         detalle.producto = producto;
         detalle.venta = venta;
         venta.detalle.add(detalle);

         total += detalle.subTotal;

         producto.stock -= detalleR.cantidad;
         this.productoRepository.persist(producto); 
      }

      venta.totalVenta = total;

      this.ventaRepository.persist(venta);
   }

   @Transactional
   public List<VentaRepresentation> obtenerTodas() {
      List<VentaRepresentation> lista = new ArrayList<>();
      for (Venta venta : this.ventaRepository.listAllWithDetalle()) {
         lista.add(this.mapper(venta));
      }
      return lista;
   }

   private VentaRepresentation mapper(Venta venta) {
      VentaRepresentation dto = new VentaRepresentation();
      dto.numVenta = venta.numVenta;
      dto.cedula = venta.cedula;
      dto.totalVenta = venta.totalVenta;
      dto.detalle = new ArrayList<>();

      for (DetalleVenta detalle : venta.detalle) {
         dto.detalle.add(this.mapperDetalle(detalle));
      }

      return dto;
   }

   private DetalleVentaRepresentation mapperDetalle(DetalleVenta detalle) {
      DetalleVentaRepresentation dto = new DetalleVentaRepresentation();
      dto.cantidad = detalle.cantidad;
      dto.codigoBarras = detalle.producto.codigoBarras;
      return dto;
   }

}
