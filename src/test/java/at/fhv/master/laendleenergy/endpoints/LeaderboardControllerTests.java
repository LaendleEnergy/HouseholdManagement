package at.fhv.master.laendleenergy.endpoints;

import at.fhv.master.laendleenergy.application.LeaderboardService;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.view.LeaderboardController;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@QuarkusTest
@TestHTTPEndpoint(LeaderboardController.class)
public class LeaderboardControllerTests {
    @InjectMock
    LeaderboardService leaderboardService;

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
    private final String invalidJwtToken = "eyJrawmuVxbfiwQ";
    static final String householdId = "h1";

    @Test
    public void testGetLeaderboardWithValidToken() throws HouseholdNotFoundException {
        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/get")
                .then()
                .statusCode(200);

        Mockito.verify(leaderboardService, times(1)).getLeaderboardOfHousehold(householdId);
    }

    @Test
    public void testGetLeaderboardWithInvalidToken() {
        given()
                .header("Authorization", "Bearer " + invalidJwtToken)
                .when().get("/get")
                .then()
                .statusCode(401);
    }

    @Test
    public void testGetLeaderboardUnauthenticated() {
        given()
                .when().get("/get")
                .then()
                .statusCode(401);
    }

    @Test
    public void testGetCurrentIncentiveWithInternalServerError() throws HouseholdNotFoundException {
        Mockito.when(leaderboardService.getLeaderboardOfHousehold(anyString())).thenThrow(NullPointerException.class);

        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/get")
                .then()
                .statusCode(500);
    }

    @Test
    public void testGetCurrentIncentiveWithHouseholdNotFoundException() throws HouseholdNotFoundException {
        Mockito.when(leaderboardService.getLeaderboardOfHousehold(anyString())).thenThrow(HouseholdNotFoundException.class);

        given()
                .header("Authorization", "Bearer " + validJwtToken)
                .when().get("/get")
                .then()
                .statusCode(404);
    }
}
