package com.demo.project65.repository;

import com.demo.project65.config.PgContainer;
import com.demo.project65.domain.Customer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

@DataR2dbcTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryTest extends PgContainer {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    public void test() {
        Customer customer = new Customer();
        customer.setName("jack");
        customer.setAge(30);

        customerRepository.save(customer)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();

    }
}
