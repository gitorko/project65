/*
 * Copyright (c) 2020 VMware, Inc. All Rights Reserved.
 */

package com.demo.project65.config;

import javax.sql.DataSource;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

@TestConfiguration
public class PostgresContainerConfig extends AbstractR2dbcConfiguration {

    @Container
    private static final PostgreSQLContainer container = new PostgreSQLContainer("postgres:14");

    static {
        container.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);

        registry.add("spring.r2dbc.url", () -> "r2dbc:postgresql://"
                + container.getHost() + ":" + container.getFirstMappedPort()
                + "/" + container.getDatabaseName());
        registry.add("spring.r2dbc.username", () -> container.getUsername());
        registry.add("spring.r2dbc.password", () -> container.getPassword());
    }

    @Bean
    @Primary
    DataSource dataSource() {
        DataSource dataSource = DataSourceBuilder
                .create()
                .driverClassName(container.getDriverClassName())
                .password(container.getPassword())
                .username(container.getUsername())
                .url(container.getJdbcUrl())
                .build();
        return dataSource;
    }

    @Bean
    SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDropFirst(true);
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:/db/changelog/db.changelog-main.yaml");
        return liquibase;
    }

    @Bean
    @Override
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                        .host(container.getHost())
                        .port(container.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT))
                        .username(container.getUsername())
                        .password(container.getPassword())
                        .database(container.getDatabaseName())
                        .build());
    }

}
