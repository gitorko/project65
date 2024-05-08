package com.demo.project65.repository;

import java.util.UUID;

import com.demo.project65.domain.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface CustomerRepository extends R2dbcRepository<Customer, UUID> {

    @Query("select * from customer e where e.name = $1 and e.age = $2")
    Flux<Customer> findByNameAndAge(String name, Integer age);

    Flux<Customer> findAllBy(Pageable pageable);

}
