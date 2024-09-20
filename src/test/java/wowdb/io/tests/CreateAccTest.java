package wowdb.io.tests;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wowdb.io.AuthResourceTest;
import wowdb.io.testcontainers.AuthResourceTestContainersManager;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTestResource(AuthResourceTestContainersManager.class)
@QuarkusTest
@DisplayName("Endpoint : /createAccount")
class CreateAccTest extends AuthResourceTest {

    @Test
    @DisplayName("positive test of creating account")
    void createAcc() {
        given()
                .header("Content-Type", "application/json")
                .body("{\"account_name\":\"test1\",\"account_password\":\"123456\",\"account_email\":\"test1@gmail.com\"}")
                .when().post("/auth/createAccount")
                .then()
                .statusCode(200)
                .body(is("{\"code\":0,\"message\":\"Processed successfully\"}"));
    }

    @Test
    @DisplayName("negative test of creating account: login already exist")
    void createAccDublicate() {
        given()
                .header("Content-Type", "application/json")
                .body("{\"account_name\":\"test\",\"account_password\":\"123456\",\"account_email\":\"test1@gmail.com\"}")
                .when().post("/auth/createAccount")
                .then()
                .statusCode(417)
                .body(is("{\"code\":11,\"message\":\"The login [test] already exist\"}"));
    }

    @Test
    @DisplayName("negative test of creating account: email is already in use")
    void createAccEmailDublicate() {
        given()
                .header("Content-Type", "application/json")
                .body("{\"account_name\":\"test2\",\"account_password\":\"123456\",\"account_email\":\"test@gmail.com\"}")
                .when().post("/auth/createAccount")
                .then()
                .statusCode(417)
                .body(is("{\"code\":12,\"message\":\"The email [test@gmail.com] is already in use\"}"));
    }
}
