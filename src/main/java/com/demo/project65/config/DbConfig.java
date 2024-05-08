package com.demo.project65.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import reactor.core.publisher.Mono;

@Configuration
@EnableR2dbcAuditing
@EnableR2dbcRepositories
public class DbConfig {

    @Bean
    public ReactiveAuditorAware<String> auditorAware() {
        return () -> Mono.just("admin");
    }
}
