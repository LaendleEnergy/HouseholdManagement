package at.fhv.master.laendleenergy.endpoints;

import at.fhv.master.laendleenergy.view.EnergySavingController;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.quarkus.test.security.jwt.Claim;
import io.quarkus.test.security.jwt.JwtSecurity;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestHTTPEndpoint(EnergySavingController.class)
public class EnergySavingControllerTests {
    /*@Test
    public void testGetCurrentIncentiveEndpointUnauthorized() {
        given()
                .when().get("/saving/getCurrentIncentive")
                .then()
                .statusCode(401);
    }

    @Test
    @TestSecurity(user="alice@example.com", roles="Admin")
    @JwtSecurity(claims = {
            @Claim(key = "memberId", value = "1"),
            @Claim(key = "householdId", value = "h1"),
            @Claim(key = "deviceId", value = "D1")
    })
    public void testGetCurrentIncentiveEndpoint() {
        RestAssured.when().get("test-security-jwt-claims").then().statusCode(200);
    }*/

}
