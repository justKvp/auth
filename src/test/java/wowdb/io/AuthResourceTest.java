package wowdb.io;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class AuthResourceTest {
    @Test
    void testHelloEndpoint() {
        given()
          .when().get("/auth/healthCheck")
          .then()
             .statusCode(200)
             .body(is("online"));
    }

}