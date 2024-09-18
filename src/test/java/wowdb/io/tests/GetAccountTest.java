package wowdb.io.tests;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wowdb.io.AuthResourceTest;
import wowdb.io.testcontainers.CustomerServiceTestcontainersManager;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTestResource(CustomerServiceTestcontainersManager.class)
@QuarkusTest
@DisplayName("Endpoint : /getAccount/{userName}")
public class GetAccountTest extends AuthResourceTest {

    @Test
    @DisplayName("positive test of getAccount")
    void getAcc() {
        when().get("/auth/getAccount/test")
                .then()
                .statusCode(200)
                .body(is("{\"id\":3,\"accountAccess\":null,\"realmCharacters\":[],\"username\":\"TEST\",\"email\":\"test@gmail.com\",\"regMail\":\"test@gmail.com\",\"joinDate\":\"2024-09-18T12:38:53.000+00:00\",\"lastIp\":\"127.0.0.1\",\"lastAttemptIp\":\"127.0.0.1\",\"failedLogins\":0,\"locked\":0,\"lockCountry\":\"00\",\"lastLogin\":null,\"online\":0,\"expansion\":2,\"muteTime\":0,\"muteReason\":\"\",\"muteBy\":\"\",\"locale\":0,\"os\":\"\",\"recruiter\":0}"));
    }

    @Test
    @DisplayName("negative test of getAccount: Account does not exist")
    void accDoesNotExist() {
        when().get("/auth/getAccount/test321")
                .then()
                .statusCode(417)
                .body(is("{\"code\":10,\"message\":\"Account [test321] does not exist\"}"));
    }
}
