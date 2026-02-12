package uce.edu.web.api.app.application;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uce.edu.web.api.app.application.representation.ProductoRepresentation;
import uce.edu.web.api.app.domain.Producto;
import uce.edu.web.api.app.infrastructure.ProductoRepository;

@ApplicationScoped
public class ProductoService {
   @Inject
   private ProductoRepository repository;

   public List<ProductoRepresentation> listarTodos() {
      List<ProductoRepresentation> lista = new ArrayList<>();
      for(Producto prod : this.repository.listAll()) {
         lista.add(this.mapper(prod));
      }
      return lista;
   }

   @Transactional
   public void guardar(ProductoRepresentation dto) {
      Producto producto = this.repository.findByCodigoBarras(dto.codigoBarras);
      if (producto != null) {
         producto.stock += dto.stock;        
      } else {
         this.repository.persist(this.mapperToProducto(dto));         
      }
   }

   public ProductoRepresentation buscarPorCodigoBarras(String codigoBarras) {
      return mapper(this.repository.findByCodigoBarras(codigoBarras));
   }

   private ProductoRepresentation mapper(Producto producto) {
      ProductoRepresentation dto = new ProductoRepresentation();
      dto.id = producto.id;
      dto.codigoBarras = producto.codigoBarras;
      dto.categoria = producto.categoria;
      dto.nombre = producto.nombre;
      dto.precio = producto.precio;
      dto.stock = producto.stock;

      return dto;
   }

   private Producto mapperToProducto(ProductoRepresentation dto) {
      Producto producto = new Producto();
      producto.id = dto.id;
      producto.codigoBarras = dto.codigoBarras;
      producto.categoria = dto.categoria;
      producto.nombre = dto.nombre;
      producto.precio = dto.precio;
      producto.stock = dto.stock;

      return producto;
   }
}
