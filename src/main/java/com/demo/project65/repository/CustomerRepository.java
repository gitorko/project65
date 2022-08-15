package com.demo.project65.repository;

import com.demo.project65.domain.Customer;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, Long> {

    @Query("select * from customer e where e.name = $1 and e.age = $2")
    Flux<Customer> findByNameAndAge(String name, Integer age);
}
