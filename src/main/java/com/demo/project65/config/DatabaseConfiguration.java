package com.demo.project65.config;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories
@RequiredArgsConstructor
public class DatabaseConfiguration extends AbstractR2dbcConfiguration {

    private final DataSourceConfig dataSourceConfig;

    @Bean
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                        .host(dataSourceConfig.getHost())
                        .port(5432)
                        .database(dataSourceConfig.getName())
                        .username(dataSourceConfig.getUsername())
                        .password(dataSourceConfig.getPassword())
                        .build()
        );
    }

}
