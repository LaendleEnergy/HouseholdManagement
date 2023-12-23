package at.fhv.master.laendleenergy.view;

import at.fhv.master.laendleenergy.application.EnergySavingService;
import at.fhv.master.laendleenergy.view.DTO.IncentiveDTO;
import at.fhv.master.laendleenergy.view.DTO.SavingTargetDTO;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/saving")
public class EnergySavingController {

    @Inject
    EnergySavingService energySavingService;
    @Inject
    JsonWebToken jwt;

    @GET
    @Path("/getCurrentIncentive")
    @Produces(MediaType.APPLICATION_JSON)
    @Authenticated
    public Response getCurrentIncentive() {
        boolean hasJWT = jwt.getClaimNames() != null;

        if (hasJWT && jwt.containsClaim("householdId")) {
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
    @RolesAllowed("Admin")
    public Response updateIncentive(IncentiveDTO incentiveDTO) {
        boolean hasJWT = jwt.getClaimNames() != null;

        if (hasJWT && jwt.containsClaim("householdId")) {
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

    @GET
    @Path("/getCurrentSavingTarget")
    @Produces(MediaType.APPLICATION_JSON)
    @Authenticated
    public Response getCurrentSavingTarget() {
        boolean hasJWT = jwt.getClaimNames() != null;

        if (hasJWT && jwt.containsClaim("householdId")) {
            String householdId = jwt.getClaim("householdId");
            try {
                return Response.ok(energySavingService.getCurrentSavingTarget(householdId), MediaType.APPLICATION_JSON).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/updateSavingTarget")
    @RolesAllowed("Admin")
    public Response updateSavingTarget(SavingTargetDTO savingTargetDTO) {
        boolean hasJWT = jwt.getClaimNames() != null;

        if (hasJWT && jwt.containsClaim("householdId")) {
            String householdId = jwt.getClaim("householdId");
            try {
                energySavingService.updateSavingTarget(householdId, savingTargetDTO);
                return Response.ok().build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
