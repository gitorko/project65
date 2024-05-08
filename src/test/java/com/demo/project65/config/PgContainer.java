package com.demo.project65.config;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class PgContainer {

    @Container
    private static final PostgreSQLContainer pgContainer = new PostgreSQLContainer("postgres:14");

    static {
        pgContainer.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.liquibase.enabled", () -> true);
        registry.add("spring.liquibase.change-log", () -> "db/changelog/db.changelog-main.yaml");
        registry.add("spring.liquibase.url", pgContainer::getJdbcUrl);
        registry.add("spring.liquibase.user", pgContainer::getUsername);
        registry.add("spring.liquibase.password", pgContainer::getPassword);

        registry.add("spring.r2dbc.url", () -> "r2dbc:postgresql://"
                + pgContainer.getHost() + ":" + pgContainer.getFirstMappedPort()
                + "/" + pgContainer.getDatabaseName());
        registry.add("spring.r2dbc.username", () -> pgContainer.getUsername());
        registry.add("spring.r2dbc.password", () -> pgContainer.getPassword());
    }
}
