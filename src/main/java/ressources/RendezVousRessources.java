package ressources;

import entities.RendezVous;
import metiers.RendezVousBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("rendezvous")
public class RendezVousRessources {

    public static RendezVousBusiness rendezVousMetier = new RendezVousBusiness();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("add")
    public Response addrendezVous(RendezVous r) {
        if (rendezVousMetier.addRendezVous(r)) {
            return Response.status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list")
    public Response getListeRendezVous() {
        List<RendezVous> liste = rendezVousMetier.getListeRendezVous();
        if (liste.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(liste).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response updateRdv(@PathParam("id") int id, RendezVous updatedRendezVous) {
        if (rendezVousMetier.updateRendezVous(id, updatedRendezVous)) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteRendezVous(@PathParam("id") int id) {
        if (rendezVousMetier.deleteRendezVous(id)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRendezVousbyId(@PathParam("id") int id) {
        RendezVous rendezVous = rendezVousMetier.getRendezVousById(id);
        if (rendezVous != null) {
            return Response.status(Response.Status.OK).entity(rendezVous).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }


    @GET
    @Path("logement/{reference}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRendezVousByLogementReference(@PathParam("reference") int reference) {
        List<RendezVous> rendezVousList = rendezVousMetier.getListeRendezVousByLogementReference(reference);

        if (!rendezVousList.isEmpty()) {
            return Response.status(Response.Status.OK).entity(rendezVousList).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
