package uce.edu.web.api.app.interfaces;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import uce.edu.web.api.app.application.VentaService;
import uce.edu.web.api.app.application.representation.VentaRepresentation;

@Path("/ventas")
public class VentaResource {

   @Inject
   private VentaService service;

   @GET
   @Path("")
   @Produces(MediaType.APPLICATION_JSON)
   public Response obtenerTodas() {
      return Response.ok(this.service.obtenerTodas()).build();
   }

   @POST
   @Path("")
   @Consumes(MediaType.APPLICATION_JSON)
   public Response crear(VentaRepresentation venta) {
      this.service.crear(venta);
      return Response.status(Response.Status.CREATED).entity(venta).build();
   }
}
