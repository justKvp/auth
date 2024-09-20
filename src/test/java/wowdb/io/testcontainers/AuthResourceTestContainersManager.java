package wowdb.io.testcontainers;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;
import java.util.Map;

public class AuthResourceTestContainersManager implements QuarkusTestResourceLifecycleManager {

    private MySQLContainer<?> mySQLContainer;

    @Override
    public Map<String, String> start() {
        mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.4.2-oraclelinux9"))
                .withDatabaseName("auth")
                .withUsername("root")
                .withPassword("root")
                .withInitScript("database/init-database.sql");
        mySQLContainer.start();

        Map<String, String> conf = new HashMap<>();
        conf.put("quarkus.datasource.jdbc.url", mySQLContainer.getJdbcUrl());
        conf.put("quarkus.datasource.username", mySQLContainer.getUsername());
        conf.put("quarkus.datasource.password", mySQLContainer.getPassword());
        conf.put("quarkus.rate-limiter.buckets.createAccount.limits[0].permitted-uses", "10");
        return conf;
    }

    @Override
    public void stop() {
        if (mySQLContainer != null) {
            mySQLContainer.stop();
        }
    }
}
