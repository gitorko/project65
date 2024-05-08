package com.demo.project65.service;

import com.demo.project65.config.PostgresContainerConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.test.StepVerifier;

@DataR2dbcTest
@Testcontainers
@Import({PostgresContainerConfig.class, DataService.class})
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DataServiceTest {

    @Autowired
    DataService dataService;

    @Test
    public void test1() {
        dataService.r2dbcTemplateCall()
                .as(StepVerifier::create)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void test2() {
        dataService.repositoryCall()
                .as(StepVerifier::create)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void test3() {
        dataService.createCustomer()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

}
