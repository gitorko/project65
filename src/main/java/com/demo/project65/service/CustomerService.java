package com.demo.project65.service;

import java.util.UUID;

import com.demo.project65.domain.Customer;
import com.demo.project65.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {

    final CustomerRepository customerRepository;

    public Flux<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Mono<Customer> findById(UUID id) {
        return customerRepository.findById(id);
    }

    public Mono<Customer> update(Customer customer) {
        return customerRepository.findById(customer.getId())
                .flatMap(c -> {
                    c.setName(customer.getName());
                    c.setAge(customer.getAge());
                    c.setPaymentType(customer.getPaymentType());
                    return Mono.just(c);
                })
                .flatMap(p -> customerRepository.save(p));
    }

    public Mono<Customer> save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Mono<Void> deleteById(UUID id) {
        return customerRepository.deleteById(id);
    }

    public Flux<Customer> findByNameAndAge(String name, Integer age) {
        return customerRepository.findByNameAndAge(name, age);
    }

    public Mono<Page<Customer>> findAllByPage(PageRequest pageRequest) {
        return customerRepository.findAllBy(pageRequest.withSort(Sort.by("name").descending()))
                .collectList()
                .zipWith(customerRepository.count())
                .map(t -> new PageImpl<>(t.getT1(), pageRequest, t.getT2()));
    }

    public Flux<Customer> search(Customer customer) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.EXACT);
        Example<Customer> example = Example.of(customer, matcher);
        return customerRepository.findAll(example);
        //return customerRepository.findAll(Example.of(customer));
    }
}
