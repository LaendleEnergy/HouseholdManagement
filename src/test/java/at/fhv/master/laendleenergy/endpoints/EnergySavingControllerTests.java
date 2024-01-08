package at.fhv.master.laendleenergy.endpoints;

import at.fhv.master.laendleenergy.application.EnergySavingService;
import at.fhv.master.laendleenergy.view.DTO.IncentiveDTO;
import at.fhv.master.laendleenergy.view.DTO.SavingTargetDTO;
import at.fhv.master.laendleenergy.view.EnergySavingController;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDate;
import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@QuarkusTest
@TestHTTPEndpoint(EnergySavingController.class)
public class EnergySavingControllerTests {
    @InjectMock
    EnergySavingService energySavingService;
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
    static IncentiveDTO incentiveDTO;
    static SavingTargetDTO savingTargetDTO;
    static final String householdId = "h1";
    static final String incentiveDTOJSONString = "{\"id\":\"id\",\"description\":\"description\",\"endDate\":\"2024-10-10\"}";
    static final String savingTargetDTOJSONString = "{\"id\":\"id\",\"percentage\":10,\"timeframe\":\"Vormonat\"}";


    @BeforeEach
    void setUp() {
        incentiveDTO = new IncentiveDTO("id", "description", LocalDate.of(2024,10,10).toString());
        savingTargetDTO = new SavingTargetDTO("id", 10, "Vormonat");
    }

    @Test
    public void testGetCurrentIncentiveEndpointWithValidToken() {
        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/getCurrentIncentive")
                .then()
                .statusCode(200);

        Mockito.verify(energySavingService, times(1)).getCurrentIncentive(householdId);
    }

    @Test
    public void testGetCurrentIncentiveEndpointWithInvalidToken() {
        given()
                .header("Authorization", "Bearer " + invalidJwtToken)
                .when().get("/getCurrentIncentive")
                .then()
                .statusCode(401);
    }

    @Test
    public void testGetCurrentIncentiveEndpointUnauthenticated() {
        given()
                .when().get("/getCurrentIncentive")
                .then()
                .statusCode(401);
    }

    @Test
    public void testGetCurrentIncentiveWithInternalServerError() {
        Mockito.when(energySavingService.getCurrentIncentive(anyString())).thenThrow(NullPointerException.class);

        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/getCurrentIncentive")
                .then()
                .statusCode(500);
    }

    @Test
    public void testUpdateIncentiveEndpointUnauthenticated() {
        given()
                .contentType(ContentType.JSON)
                .body(incentiveDTOJSONString)
                .when().post("/updateIncentive")
                .then()
                .statusCode(401);
    }

    @Test
    public void testUpdateIncentiveEndpointWithValidToken() {
        given()
                .contentType(ContentType.JSON)
                .body(incentiveDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/updateIncentive")
                .then()
                .statusCode(200);

        Mockito.verify(energySavingService, times(1)).updateIncentive(anyString(), any());
    }

    @Test
    public void testUpdateIncentiveEndpointWithInvalidToken() {
        given()
                .contentType(ContentType.JSON)
                .body(incentiveDTOJSONString)
                .header("Authorization", "Bearer " + invalidJwtToken)
                .when().post("/updateIncentive")
                .then()
                .statusCode(401);
    }

    @Test
    public void testUpdateIncentiveEndpointWithInternalServerError() {
        Mockito.doThrow(NullPointerException.class).when(energySavingService).updateIncentive(anyString(), any());

        given()
                .contentType(ContentType.JSON)
                .body(incentiveDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/updateIncentive")
                .then()
                .statusCode(500);
    }

    @Test
    public void testGetCurrentSavingTargetEndpointWithValidToken() {
        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/getCurrentSavingTarget")
                .then()
                .statusCode(200);

        Mockito.verify(energySavingService, times(1)).getCurrentSavingTarget(householdId);
    }

    @Test
    public void testGetCurrentSavingTargetEndpointWithInvalidToken() {
        given()
                .header("Authorization", "Bearer " + invalidJwtToken)
                .when().get("/getCurrentSavingTarget")
                .then()
                .statusCode(401);
    }

    @Test
    public void testGetCurrentSavingTargetEndpointUnauthenticated() {
        given()
                .when().get("/getCurrentSavingTarget")
                .then()
                .statusCode(401);
    }

    @Test
    public void testGetCurrentSavingTargetEndpointInternalServerErrorException() {
        Mockito.when(energySavingService.getCurrentSavingTarget(anyString())).thenThrow(NullPointerException.class);

        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/getCurrentSavingTarget")
                .then()
                .statusCode(500);
    }

    @Test
    public void testUpdateSavingTargetEndpointWithValidToken() {
        given()
                .contentType(ContentType.JSON)
                .body(savingTargetDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/updateSavingTarget")
                .then()
                .statusCode(200);

        Mockito.verify(energySavingService, times(1)).updateSavingTarget(anyString(), any());
    }

    @Test
    public void testUpdateSavingTargetEndpointWithInvalidToken() {
        given()
                .contentType(ContentType.JSON)
                .body(savingTargetDTOJSONString)
                .header("Authorization", "Bearer " + invalidJwtToken)
                .when().post("/updateSavingTarget")
                .then()
                .statusCode(401);
    }

    @Test
    public void testUpdateSavingTargetEndpointUnauthenticated() {
        given()
                .contentType(ContentType.JSON)
                .body(savingTargetDTOJSONString)
                .when().post("/updateSavingTarget")
                .then()
                .statusCode(401);
    }

    @Test
    public void testUpdateSavingTargetEndpointWithInternalServerError() {
        Mockito.doThrow(NullPointerException.class).when(energySavingService).updateSavingTarget(anyString(), any());

        given()
                .contentType(ContentType.JSON)
                .body(savingTargetDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/updateSavingTarget")
                .then()
                .statusCode(500);
    }

}
