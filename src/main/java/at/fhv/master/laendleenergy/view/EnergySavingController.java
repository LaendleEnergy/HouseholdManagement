package at.fhv.master.laendleenergy.view;

import at.fhv.master.laendleenergy.application.EnergySavingService;
import at.fhv.master.laendleenergy.view.DTO.IncentiveDTO;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.security.Principal;

@Path("/saving")
public class EnergySavingController {

    @Inject
    EnergySavingService energySavingService;
    @Inject
    JsonWebToken jwt;

    @GET
    @Path("/getCurrentIncentive")
    @Authenticated
    public Response getCurrentIncentive(@Context SecurityContext ctx) {
        boolean hasJWT = jwt.getClaimNames() != null;
        Principal caller = ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();

        if (hasJWT && jwt.containsClaim("householdId") && name.equals(jwt.getName())) {
            String householdId = jwt.getClaim("householdId");
            try {
                return Response.ok(energySavingService.getCurrentIncentive(householdId), MediaType.APPLICATION_JSON).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/updateIncentive")
    @Authenticated
    public Response updateIncentive(@Context SecurityContext ctx, IncentiveDTO incentiveDTO) {
        boolean hasJWT = jwt.getClaimNames() != null;
        Principal caller = ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();

        if (hasJWT && jwt.containsClaim("householdId") && name.equals(jwt.getName())) {
            String householdId = jwt.getClaim("householdId");
            try {
                energySavingService.updateIncentive(householdId, incentiveDTO);
                return Response.ok().build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
