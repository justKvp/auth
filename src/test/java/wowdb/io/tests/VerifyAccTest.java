package wowdb.io.tests;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wowdb.io.AuthResourceTest;
import wowdb.io.testcontainers.CustomerServiceTestcontainersManager;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTestResource(CustomerServiceTestcontainersManager.class)
@QuarkusTest
@DisplayName("Endpoint : /verifyAccount")
class VerifyAccTest extends AuthResourceTest {

    @Test
    @DisplayName("positive test of verify account")
    void verifyAcc() {
        given()
                .header("Content-Type", "application/json")
                .body("{\"account_name\":\"test\",\"account_password\":\"123456\"}")
                .when().post("/auth/verifyAccount")
                .then()
                .statusCode(200)
                .body(is("{\"code\":0,\"message\":\"Processed successfully\"}"));
    }

    @Test
    @DisplayName("negative test of verify account: Account does not exist")
    void verifyAccDoesNotExist() {
        given()
                .header("Content-Type", "application/json")
                .body("{\"account_name\":\"test321\",\"account_password\":\"123456\"}")
                .when().post("/auth/verifyAccount")
                .then()
                .statusCode(417)
                .body(is("{\"code\":10,\"message\":\"Account [test321] does not exist\"}"));
    }

    @Test
    @DisplayName("negative test of verify account: Login or Password incorrect")
    void verifyAccIncorrectPass() {
        given()
                .header("Content-Type", "application/json")
                .body("{\"account_name\":\"test\",\"account_password\":\"1234567\"}")
                .when().post("/auth/verifyAccount")
                .then()
                .statusCode(412)
                .body(is("{\"code\":14,\"message\":\"Login or Password incorrect\"}"));
    }
}
