package at.fhv.master.laendleenergy.view;

import at.fhv.master.laendleenergy.application.DeviceService;
import at.fhv.master.laendleenergy.domain.exceptions.DeviceCategoryNotFound;
import at.fhv.master.laendleenergy.domain.exceptions.DeviceNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.view.DTO.DeviceCategoryDTO;
import at.fhv.master.laendleenergy.view.DTO.DeviceDTO;
import at.fhv.master.laendleenergy.view.DTO.IncentiveDTO;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/device")
public class DeviceController {

    @Inject
    DeviceService deviceService;
    @Inject
    JsonWebToken jwt;

    @GET
    @Path("/getCategories")
    @PermitAll
    public Response getCategories() {
        try {
            return Response.ok(deviceService.getAllAvailableDeviceCategories().stream().map(DeviceCategoryDTO::getName).toList(), MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/get")
    @Authenticated
    public Response getDevicesOfHousehold() {
        boolean hasJWT = jwt.getClaimNames() != null;

        if (hasJWT && jwt.containsClaim("householdId")) {
            String householdId = jwt.getClaim("householdId");
            try {
                return Response.ok(deviceService.getDevicesOfHousehold(householdId), MediaType.APPLICATION_JSON).build();
            } catch (HouseholdNotFoundException e) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("Admin")
    public Response addDevice(DeviceDTO device) {
        boolean hasJWT = jwt.getClaimNames() != null;

        if (hasJWT && jwt.containsClaim("householdId")) {
            String memberId = jwt.getClaim("memberId");
            String householdId = jwt.getClaim("householdId");
            String deviceId = jwt.getClaim("deviceId");

            try {
                deviceService.addDevice(device.getName(), device.getCategoryName(), deviceId, householdId, memberId);
                return Response.ok("Device sucessfully added", MediaType.APPLICATION_JSON).build();
            } catch (HouseholdNotFoundException | DeviceCategoryNotFound e) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @DELETE
    @Path("/{deviceName}")
    @RolesAllowed("Admin")
    public Response removeDevice(@PathParam("deviceName") String deviceName) {
        boolean hasJWT = jwt.getClaimNames() != null;

        if (hasJWT && jwt.containsClaim("householdId")) {
            String householdId = jwt.getClaim("householdId");
            try {
                deviceService.removeDevice(deviceName, householdId);
                return Response.ok("Device sucessfully removed", MediaType.APPLICATION_JSON).build();
            } catch (DeviceNotFoundException e) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
