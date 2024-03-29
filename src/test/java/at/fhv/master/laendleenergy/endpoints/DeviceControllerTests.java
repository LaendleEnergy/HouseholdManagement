package at.fhv.master.laendleenergy.endpoints;

import at.fhv.master.laendleenergy.application.DeviceService;
import at.fhv.master.laendleenergy.domain.exceptions.DeviceCategoryNotFound;
import at.fhv.master.laendleenergy.domain.exceptions.DeviceNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.view.DeviceController;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@QuarkusTest
@TestHTTPEndpoint(DeviceController.class)
public class DeviceControllerTests {
    @InjectMock
    DeviceService deviceService;
    /*
    {
      "iss": "https://ard333.com",
      "sub": "alice@example.com",
      "iat": 1704711800,
      "exp": 36001704711800,
      "groups": [
        "Admin"
      ],
      "memberId": "1",
      "householdId": "h1",
      "deviceId": "D1",
      "jti": "c9a62faa-0b1e-4c7b-9704-9647f04ffd1c"
    }
     */
    private final String validJwtToken = "eyJraWQiOiIvcHJpdmF0ZWtleS5wZW0iLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL2FyZDMzMy5jb20iLCJzdWIiOiJhbGljZUBleGFtcGxlLmNvbSIsImlhdCI6MTcwNDcxMTgwMCwiZXhwIjozNjAwMTcwNDcxMTgwMCwiZ3JvdXBzIjpbIkFkbWluIl0sIm1lbWJlcklkIjoiMSIsImhvdXNlaG9sZElkIjoiaDEiLCJkZXZpY2VJZCI6IkQxIiwianRpIjoiYzlhNjJmYWEtMGIxZS00YzdiLTk3MDQtOTY0N2YwNGZmZDFjIn0.XgV-PnqA_LB9OFFE8-zr0UIMugTb6P4qPvymCoancALWvS4VJjF-tXjU02yms0YvSXC-GmpbyUDZtiPm26KApjawXaoNSa5gonsnTHl6s4bT8MkgUrNNs9Di9KmCHgoTohgr9B7pelM6eJCOf5tT-phkoSvaxxrYn099BYsUeA1DVVsApic1egEV1ItZYRops8XUR-KPydeimgYq6tpc2g-7L7RiNIYkssvVxxh25-EGn8lLkivBu3gA7_2siCZfVZbP8JWagT629OK9B_GpnOhz8_-p5KSjMRjDTJBcRTnzYQDGzOB-RmsB0NZaLPw5ulqR1yN3r5KEpm-GExAKRw";
    private final String invalidJwtToken = "eyZraWQiOiIvcHJpdmF0ZWtleS5wZW0iLCJ0eXAAOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpC3MiOiJodHRwczovL2FyZDMzMy5jb20iLCJUdWIiOiJhbGljZUBleGFtcGxlLmNvbSIsImlhdCI6MTcwNDcxMTgwMCwiZXhwIjLzNjAwMTcwNDcxMTgwMCwiZ3JvdXBzIjUbIkFkbWluIl0sIm1lbWJlcklkIjoiMSIsImhvdXNlaGzsZElkIjoiaDEiLCJkZXZpY2VJZCI6IkQxIiwian9pIjoiYzlhNjJmYWEtMGIxZS00YzdiLTk3MDQtOTY0NCYwNGZmZDFjIn0.XgV-PnqA_LB9OFFE4-zr0UIMugTb6P4qPvymCoancALWvS4VJjF-tXjU02yms0YvSXC-GmpbyCDZtiPm26KApjawXaoNSa5gonsnTHl6s4bT8MkgUrNNs9Di9KmCHgoTohgr9B7pelM6eJCOf5tT-phkoSvaxxrYn099BYsUeA1DVVsApic1egEV1ItZYRops8XUR-KPydeimgYq6tpc2g-7L7RiNIYkssvVxxh25-EGn8lLkivBu3gA7_2siCZfVZbP8JWagT629OK9B_GpnOhz8_-p5KSjMRjDTJBcRTnzYQDGzOB-RmsB0NZaLPw5ulqR1yN3r5KEpm-GExAKRw";
    static final String deviceDTOJSONString = "{\"name\":\"name\",\"categoryName\":\"categoryname\"}";

    @Test
    public void testGetCategoriesEndpointWithValidToken() {
        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/getCategories")
                .then()
                .statusCode(200);

        Mockito.verify(deviceService, times(1)).getAllAvailableDeviceCategories();
    }

    @Test
    public void testGetCategoriesEndpointWithoutToken() {
        given()
                .when().get("/getCategories")
                .then()
                .statusCode(200);

        Mockito.verify(deviceService, times(1)).getAllAvailableDeviceCategories();
    }


    @Test
    public void testGetCategoriesEndpointWithInternalServerError() {
        Mockito.when(deviceService.getAllAvailableDeviceCategories()).thenThrow(NullPointerException.class);

        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/getCategories")
                .then()
                .statusCode(500);
    }

    @Test
    public void testGetDevicesEndpointWithValidToken() throws HouseholdNotFoundException {
        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/get")
                .then()
                .statusCode(200);

        Mockito.verify(deviceService, times(1)).getDevicesOfHousehold(anyString());
    }

    @Test
    public void testGetDevicesEndpointWithoutToken() throws HouseholdNotFoundException {
        given()
                .when().get("/get")
                .then()
                .statusCode(401);
    }


    @Test
    public void testGetDevicesEndpointWithInternalServerError() throws HouseholdNotFoundException {
        Mockito.when(deviceService.getDevicesOfHousehold(anyString())).thenThrow(NullPointerException.class);

        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/get")
                .then()
                .statusCode(500);
    }

    @Test
    public void testAddDeviceEndpointWithValidToken() throws HouseholdNotFoundException, DeviceCategoryNotFound, JsonProcessingException {
        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .contentType(ContentType.JSON)
                .body(deviceDTOJSONString)
                .when().post("/add")
                .then()
                .statusCode(200);

        Mockito.verify(deviceService, times(1)).addDevice(anyString(), anyString(), anyString(), anyString(), anyString());
    }

    @Test
    public void testAddDeviceEndpointWithInvalidToken() {
        given()
                .header("Authorization", "Bearer " + invalidJwtToken)
                .contentType(ContentType.JSON)
                .body(deviceDTOJSONString)
                .when().post("/add")
                .then()
                .statusCode(401);
    }

    @Test
    public void testAddDeviceEndpointNotFoundException() throws HouseholdNotFoundException, DeviceCategoryNotFound, JsonProcessingException {
        Mockito.doThrow(DeviceCategoryNotFound.class).when(deviceService).addDevice(anyString(), anyString(), anyString(), anyString(), anyString());

        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .contentType(ContentType.JSON)
                .body(deviceDTOJSONString)
                .when().post("/add")
                .then()
                .statusCode(404);
    }

    @Test
    public void testAddDeviceEndpointWithInternalServerError() throws HouseholdNotFoundException, DeviceCategoryNotFound, JsonProcessingException {
        Mockito.doThrow(NullPointerException.class).when(deviceService).addDevice(anyString(), anyString(), anyString(), anyString(), anyString());

        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .contentType(ContentType.JSON)
                .body(deviceDTOJSONString)
                .when().post("/add")
                .then()
                .statusCode(500);
    }

    @Test
    public void testRemoveDeviceEndpointWithValidToken() throws DeviceNotFoundException, HouseholdNotFoundException {
        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .pathParam("deviceName", "name")
                .when().delete("/{deviceName}")
                .then()
                .statusCode(200);

        Mockito.verify(deviceService, times(1)).removeDevice(anyString(), anyString());
    }

    @Test
    public void testRemoveDeviceEndpointWithInvalidToken() {
        given()
                .header("Authorization", "Bearer " + invalidJwtToken)
                .pathParam("deviceName", "name")
                .when().delete("/{deviceName}")
                .then()
                .statusCode(401);
    }

    @Test
    public void testRemoveDeviceEndpointNotFoundException() throws DeviceNotFoundException, HouseholdNotFoundException {
        Mockito.doThrow(DeviceNotFoundException.class).when(deviceService).removeDevice(anyString(), anyString());

        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .pathParam("deviceName", "name")
                .when().delete("/{deviceName}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testRemoveDeviceEndpointWithInternalServerError() throws DeviceNotFoundException, HouseholdNotFoundException {
        Mockito.doThrow(NullPointerException.class).when(deviceService).removeDevice(anyString(), anyString());

        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .pathParam("deviceName", "name")
                .when().delete("/{deviceName}")
                .then()
                .statusCode(500);
    }

    @Test
    public void testUpdateDeviceEndpointWithValidToken() throws HouseholdNotFoundException, DeviceNotFoundException, DeviceCategoryNotFound {
        given()
                .contentType(ContentType.JSON)
                .body(deviceDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/update")
                .then()
                .statusCode(200);

        Mockito.verify(deviceService, times(1)).updateDevice(any(), anyString());
    }

    @Test
    public void testUpdateDeviceEndpointWithInvalidToken() {
        given()
                .contentType(ContentType.JSON)
                .body(deviceDTOJSONString)
                .header("Authorization", "Bearer " + invalidJwtToken)
                .when().post("/update")
                .then()
                .statusCode(401);
    }

    @Test
    public void testUpdateDeviceEndpointUnauthenticated() {
        given()
                .contentType(ContentType.JSON)
                .body(deviceDTOJSONString)
                .when().post("/update")
                .then()
                .statusCode(401);
    }

    @Test
    public void testUpdateDeviceEndpointWithInternalServerError() throws DeviceNotFoundException, HouseholdNotFoundException, DeviceCategoryNotFound {
        Mockito.doThrow(NullPointerException.class).when(deviceService).updateDevice(any(), anyString());

        given()
                .contentType(ContentType.JSON)
                .body(deviceDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/update")
                .then()
                .statusCode(500);
    }

    @Test
    public void testUpdateDeviceEndpointWithHouseholdNotFoundException() throws HouseholdNotFoundException, DeviceNotFoundException, DeviceCategoryNotFound {
        Mockito.doThrow(HouseholdNotFoundException.class).when(deviceService).updateDevice(any(), anyString());

        given()
                .contentType(ContentType.JSON)
                .body(deviceDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/update")
                .then()
                .statusCode(404);
    }

    @Test
    public void testUpdateDeviceEndpointWithDeviceNotFoundException() throws HouseholdNotFoundException, DeviceNotFoundException, DeviceCategoryNotFound {
        Mockito.doThrow(DeviceNotFoundException.class).when(deviceService).updateDevice(any(), anyString());

        given()
                .contentType(ContentType.JSON)
                .body(deviceDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/update")
                .then()
                .statusCode(404);
    }

    @Test
    public void testUpdateDeviceEndpointWithDeviceCategoryNotFoundException() throws HouseholdNotFoundException, DeviceNotFoundException, DeviceCategoryNotFound {
        Mockito.doThrow(DeviceCategoryNotFound.class).when(deviceService).updateDevice(any(), anyString());

        given()
                .contentType(ContentType.JSON)
                .body(deviceDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/update")
                .then()
                .statusCode(404);
    }
}
