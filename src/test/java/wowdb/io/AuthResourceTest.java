package wowdb.io;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import wowdb.io.testcontainers.CustomerServiceTestcontainersManager;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTestResource(CustomerServiceTestcontainersManager.class)
@QuarkusTest
class AuthResourceTest {
    @Test
    void healthCheck() {
        when().get("/auth/healthCheck")
                .then()
                .statusCode(200)
                .body(is("online"));
    }

    @Test
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
    void createAccEmailDublicate() {
        given()
                .header("Content-Type", "application/json")
                .body("{\"account_name\":\"test2\",\"account_password\":\"123456\",\"account_email\":\"test@gmail.com\"}")
                .when().post("/auth/createAccount")
                .then()
                .statusCode(417)
                .body(is("{\"code\":12,\"message\":\"The email [test@gmail.com] is already in use\"}"));
    }

    @Test
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