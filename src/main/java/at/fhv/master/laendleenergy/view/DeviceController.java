package at.fhv.master.laendleenergy.view;

import at.fhv.master.laendleenergy.domain.serializer.DeviceCategorySerializer;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/device")
public class DeviceController {

    @GET
    @Path("/getCategories")
    @PermitAll
    public Response getCategories() {
        try {
            return Response.ok(DeviceCategorySerializer.parse(), MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
