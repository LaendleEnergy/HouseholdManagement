package at.fhv.master.laendleenergy.view;

import at.fhv.master.laendleenergy.application.DeviceService;
import at.fhv.master.laendleenergy.domain.Device;
import at.fhv.master.laendleenergy.domain.DeviceCategory;
import at.fhv.master.laendleenergy.domain.exceptions.DeviceCategoryNotFound;
import at.fhv.master.laendleenergy.domain.exceptions.DeviceNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.view.DTO.DeviceCategoryDTO;
import at.fhv.master.laendleenergy.view.DTO.DeviceDTO;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.stream.Collectors;

@Path("/device")
public class DeviceController {

    @Inject
    DeviceService deviceService;


    //todo: change path in frontend and backend for getCategories
    @GET
    @Path("/getCategories")
    @PermitAll
    public Response getCategories() {
        try {
            return Response.ok(deviceService.getAllAvailableDeviceCategories().stream().map(
                    DeviceCategoryDTO::getName
            ).collect(Collectors.toList()), MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /*@POST
    @Path("/category/add")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addDeviceCategory(DeviceCategoryDTO deviceCategoryDTO) {
        try {
            deviceService.addDeviceCategory(deviceCategoryDTO.getName());
            return Response.ok("DeviceCategory sucessfully added", MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }*/

    @POST
    @Path("/add")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addDevice(DeviceDTO device) {
        try {
            deviceService.addDevice(device.getDeviceName(), device.getDeviceCategoryName());
            return Response.ok("Device sucessfully added", MediaType.APPLICATION_JSON).build();
        } catch (HouseholdNotFoundException | DeviceCategoryNotFound e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/{deviceName}")
    @PermitAll
    public Response removeDevice(@PathParam("deviceName") String deviceName) {
        try {
            deviceService.removeDevice(deviceName);
            return Response.ok("Device sucessfully removed", MediaType.APPLICATION_JSON).build();
        } catch (DeviceNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /*@DELETE
    @Path("/category/{categoryName}")
    @PermitAll
    public Response removeDeviceCategory(@PathParam("categoryName") String categoryName) {
        try {
            deviceService.removeDeviceCategory(categoryName);
            return Response.ok("DeviceCategory sucessfully removed", MediaType.APPLICATION_JSON).build();
        } catch (DeviceCategoryNotFound e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }*/
}
