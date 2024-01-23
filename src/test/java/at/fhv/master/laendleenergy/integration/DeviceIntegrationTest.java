package at.fhv.master.laendleenergy.integration;

import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.view.DTO.DeviceDTO;
import at.fhv.master.laendleenergy.view.DeviceController;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.LinkedList;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@QuarkusTest
@TestHTTPEndpoint(DeviceController.class)
public class DeviceIntegrationTest {

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
    @InjectMock
    EntityManager entityManager;
    private final String validJwtToken = "eyJraWQiOiIvcHJpdmF0ZWtleS5wZW0iLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL2FyZDMzMy5jb20iLCJzdWIiOiJhbGljZUBleGFtcGxlLmNvbSIsImlhdCI6MTcwNDcxMTgwMCwiZXhwIjozNjAwMTcwNDcxMTgwMCwiZ3JvdXBzIjpbIkFkbWluIl0sIm1lbWJlcklkIjoiMSIsImhvdXNlaG9sZElkIjoiaDEiLCJkZXZpY2VJZCI6IkQxIiwianRpIjoiYzlhNjJmYWEtMGIxZS00YzdiLTk3MDQtOTY0N2YwNGZmZDFjIn0.XgV-PnqA_LB9OFFE8-zr0UIMugTb6P4qPvymCoancALWvS4VJjF-tXjU02yms0YvSXC-GmpbyUDZtiPm26KApjawXaoNSa5gonsnTHl6s4bT8MkgUrNNs9Di9KmCHgoTohgr9B7pelM6eJCOf5tT-phkoSvaxxrYn099BYsUeA1DVVsApic1egEV1ItZYRops8XUR-KPydeimgYq6tpc2g-7L7RiNIYkssvVxxh25-EGn8lLkivBu3gA7_2siCZfVZbP8JWagT629OK9B_GpnOhz8_-p5KSjMRjDTJBcRTnzYQDGzOB-RmsB0NZaLPw5ulqR1yN3r5KEpm-GExAKRw";
    static final String deviceDTOJSONString = "{\"name\":\"name\",\"categoryName\":\"Mikrowelle\"}";
    static String householdId = "h1";
    static Household household;
    static Device device;


    @BeforeEach
    void setUp() {
        DeviceDTO deviceDTO = new DeviceDTO("name", "Mikrowelle");
        DeviceCategory deviceCategory = new DeviceCategory("Mikrowelle");
        household = new Household(householdId, new Incentive(), new EnergySavingTarget(), new LinkedList<>(), new LinkedList<>());
        device = new Device(deviceCategory, deviceDTO.getName(), household);
        household.setDevices(List.of(device));

        Mockito.when(entityManager.find(Household.class, householdId)).thenReturn(household);
        Mockito.when(entityManager.find(DeviceCategory.class, "Mikrowelle")).thenReturn(deviceCategory);
    }

    @Test
    public void testGetCategoriesEndpointWithValidToken() {
        Query<DeviceCategory> queryMock = Mockito.mock(Query.class);

        Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(DeviceCategory.class))).thenReturn(queryMock);
        Mockito.when(queryMock.getResultList()).thenReturn(List.of(new DeviceCategory(), new DeviceCategory()));

        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/getCategories")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetDevicesEndpointWithValidToken() {
        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/get")
                .then()
                .statusCode(200);
    }


    @Test
    public void testAddDeviceEndpointWithValidToken() {
        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .contentType(ContentType.JSON)
                .body(deviceDTOJSONString)
                .when().post("/add")
                .then()
                .statusCode(200);
    }


    @Test
    public void testRemoveDeviceEndpointWithValidToken() {
        Query<Device> queryMock = Mockito.mock(Query.class);

        Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(Device.class))).thenReturn(queryMock);
        Mockito.when(queryMock.setParameter(Mockito.anyString(), Mockito.any())).thenReturn(queryMock);
        Mockito.when(queryMock.getSingleResult()).thenReturn(device);

        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .pathParam("deviceName", "name")
                .when().delete("/{deviceName}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testUpdateDeviceWithValidToken() {
        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .contentType(ContentType.JSON)
                .body(deviceDTOJSONString)
                .when().post("/update")
                .then()
                .statusCode(200);
    }
}
