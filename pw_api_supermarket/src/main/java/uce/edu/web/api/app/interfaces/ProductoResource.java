package uce.edu.web.api.app.interfaces;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import uce.edu.web.api.app.application.ProductoService;
import uce.edu.web.api.app.application.representation.ProductoRepresentation;

@Path("/productos")
public class ProductoResource {
   
   @Inject
   private ProductoService service;

   @GET
   @Path("")
   @Produces(MediaType.APPLICATION_JSON)
   public List<ProductoRepresentation> listarTodos() {
      return this.service.listarTodos();
   }

   @GET
   @Path("/buscar/{codigoBarras}")
   @Produces(MediaType.APPLICATION_JSON)
   public ProductoRepresentation buscarPorCodigoBarras(@PathParam("codigoBarras") String codigoBarras) {
      return this.service.buscarPorCodigoBarras(codigoBarras);
   }


   @POST
   @Path("")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   public Response guardar(ProductoRepresentation producto) {
      this.service.guardar(producto);
      return Response.status(Response.Status.CREATED).entity(producto).build();
   }
}
