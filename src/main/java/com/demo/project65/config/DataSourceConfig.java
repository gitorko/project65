package com.demo.project65.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("spring.datasource")
public class DataSourceConfig {
    String host;
    String username;
    String password;
    String name;
}
