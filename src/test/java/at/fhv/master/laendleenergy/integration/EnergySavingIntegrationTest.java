package at.fhv.master.laendleenergy.integration;

import at.fhv.master.laendleenergy.domain.EnergySavingTarget;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Incentive;
import at.fhv.master.laendleenergy.view.DTO.IncentiveDTO;
import at.fhv.master.laendleenergy.view.EnergySavingController;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(EnergySavingController.class)
public class EnergySavingIntegrationTest {

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
    static IncentiveDTO incentiveDTO;
    static final String incentiveDTOJSONString = "{\"id\":\"id\",\"description\":\"description\",\"endDate\":\"2024-10-10\"}";
    @InjectMock
    EntityManager entityManager;
    static String householdId = "h1";
    static Household household;


    @BeforeEach
    void setUp() {
        incentiveDTO = new IncentiveDTO("id", "description", LocalDate.of(2024,10,10).toString());
        household = new Household(householdId, new Incentive(), new EnergySavingTarget(), new LinkedList<>(), new LinkedList<>());
        Mockito.when(entityManager.find(Household.class, householdId)).thenReturn(household);
    }

    @Test
    public void testGetCurrentIncentiveEndpointWithValidToken() {
        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/getCurrentIncentive")
                .then()
                .statusCode(200);
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
     }

    @Test
    public void testGetCurrentSavingTargetEndpointWithValidToken() {
        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/getCurrentSavingTarget")
                .then()
                .statusCode(200);
      }

    @Test
    public void testUpdateSavingTargetEndpointWithValidToken() {
        given()
                .contentType(ContentType.JSON)
                .body(incentiveDTOJSONString)
                .header("Authorization", "Bearer " + validJwtToken)
                .when().post("/updateSavingTarget")
                .then()
                .statusCode(200);
     }
}
