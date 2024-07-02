package com.demo.project65.controller;

import java.util.UUID;

import com.demo.project65.domain.Customer;
import com.demo.project65.service.CustomerService;
import com.demo.project65.service.DataService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/customer")
public class HomeController {

    final CustomerService customerService;
    final DataService dataService;

    @GetMapping("/all")
    public Flux<Customer> findAll() {
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Customer>> findById(@PathVariable UUID id) {
        return customerService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/save")
    public Mono<Customer> save(@RequestBody Customer customer) {
        return customerService.save(customer);
    }

    @PutMapping(value = "/update")
    public Mono<Customer> update(@RequestBody Customer customer) {
        return customerService.update(customer);
    }

    @DeleteMapping(value = "/{id}")
    public Mono<Void> delete(@PathVariable UUID id) {
        return customerService.deleteById(id);
    }

    @GetMapping("/find")
    public Flux<Customer> find(@RequestParam String name, @RequestParam Integer age) {
        return customerService.findByNameAndAge(name, age);
    }

    @GetMapping("/page")
    public Mono<Page<Customer>> findPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return customerService.findAllByPage(PageRequest.of(page, size));
    }

    @PostMapping("/search")
    public Flux<Customer> search(@RequestBody Customer customer) {
        return customerService.search(customer);
    }
}
