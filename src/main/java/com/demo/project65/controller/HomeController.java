package com.demo.project65.controller;

import com.demo.project65.domain.Customer;
import com.demo.project65.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class HomeController {

    @Autowired
    CustomerRepository repo;

    @GetMapping("/all")
    public Flux<Customer> findAll() {
        return repo.findAll();
    }

    @GetMapping("/id/{customerId}")
    public Mono<Customer> findById(@PathVariable Long customerId) {
        return repo.findById(customerId);
    }

    @PostMapping(value = "/save")
    public Mono<Customer> save(@RequestBody Customer customer) {
        return repo.save(customer);
    }

    @GetMapping("/find")
    public Flux<Customer> findById(@RequestParam String name, @RequestParam Integer age) {
        return repo.findByNameAndAge(name, age);
    }
}
