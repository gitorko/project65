package com.demo.project65.service;

import java.util.Map;

import com.demo.project65.domain.Customer;
import com.demo.project65.domain.PaymentType;
import com.demo.project65.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import io.r2dbc.spi.ConnectionFactory;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class DataService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ConnectionFactory connectionFactory;

    ObjectMapper objectMapper = new ObjectMapper();

    public Flux<Customer> repositoryCall() {
        log.info("Repository call!");
        Flux<String> names = Flux.just("raj", "david", "pam");
        Flux<Integer> ages = Flux.just(25, 27, 30);
        Flux<Customer> customers = Flux.zip(names, ages).map(tupple -> {
            return new Customer(tupple.getT1(), tupple.getT2());
        });
        return customerRepository.deleteAll()
                .thenMany(customers.flatMap(c -> customerRepository.save(c))
                        .thenMany(customerRepository.findAll())
                );
    }

    public Flux<Customer> r2dbcTemplateCall() {
        log.info("R2DBC Template call!");
        R2dbcEntityTemplate template = new R2dbcEntityTemplate(connectionFactory);
        Flux<String> names = Flux.just("ajay", "suresh", "ethan");
        Flux<Integer> ages = Flux.just(25, 27, 30);
        Flux<Customer> customers = Flux.zip(names, ages).map(tupple -> {
            return new Customer(tupple.getT1(), tupple.getT2());
        });
        return template.delete(Customer.class)
                .all()
                .thenMany(customers.flatMap(c -> template.insert(Customer.class)
                                .using(c))
                        .thenMany(template.select(Customer.class)
                                .all())
                );
    }

    @SneakyThrows
    public Mono<Customer> createCustomer() {
        log.info("creating customer!");
        Map<String, String> metaData = Map.of("city", "bangalore");
        Customer customer = Customer.builder()
                .name("roger")
                .age(40)
                .metadata(Json.of(objectMapper.writeValueAsString(metaData)))
                .paymentType(PaymentType.ANNUAL)
                .build();
        return customerRepository.save(customer);
    }

    public Mono<Page<Customer>> getCustomers(PageRequest pageRequest) {
        return customerRepository.findAllBy(pageRequest.withSort(Sort.by("name").descending()))
                .collectList()
                .zipWith(customerRepository.count())
                .map(t -> new PageImpl<>(t.getT1(), pageRequest, t.getT2()));
    }

    public Flux<Customer> search(Customer customer) {
        return customerRepository.findAll(Example.of(customer));
    }

}
