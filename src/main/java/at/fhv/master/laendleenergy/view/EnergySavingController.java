package at.fhv.master.laendleenergy.view;

import at.fhv.master.laendleenergy.domain.serializer.DeviceCategorySerializer;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/saving")
public class EnergySavingController {
    @GET
    @Path("/getCurrentIncentive")
    @Authenticated
    public Response getCurrentIncentive() {
        try {
            return Response.ok(DeviceCategorySerializer.parse(), MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
