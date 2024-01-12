package at.fhv.master.laendleenergy.view;

import at.fhv.master.laendleenergy.application.LeaderboardService;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/leaderboard")
public class LeaderboardController {
    @Inject
    LeaderboardService leaderboardService;
    @Inject
    JsonWebToken jwt;

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Authenticated
    public Response getLeaderboard() {
        boolean hasJWT = jwt.getClaimNames() != null;

        if (hasJWT && jwt.containsClaim("householdId")) {
            String householdId = jwt.getClaim("householdId");
            try {
                return Response.ok(leaderboardService.getLeaderboardOfHousehold(householdId), MediaType.APPLICATION_JSON).build();
            } catch (HouseholdNotFoundException e) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
