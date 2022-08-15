package com.demo.project65;

import java.time.Duration;

import com.demo.project65.config.DataSourceConfig;
import com.demo.project65.domain.Customer;
import com.demo.project65.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

@SpringBootApplication
@Slf4j
@EnableConfigurationProperties(DataSourceConfig.class)
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner seedData(CustomerRepository customerRepository) {
        return args -> {
            log.info("Seeding data!");
            Flux<String> names = Flux.just("raj", "david", "pam").delayElements(Duration.ofSeconds(1));
            Flux<Integer> ages = Flux.just(25, 27, 30).delayElements(Duration.ofSeconds(1));
            Flux<Customer> customers = Flux.zip(names, ages).map(tupple -> {
                return new Customer(null, tupple.getT1(), tupple.getT2());
            });
            customerRepository.deleteAll()
                    .thenMany(customers.flatMap(c -> customerRepository.save(c))
                            .thenMany(customerRepository.findAll())
                    ).subscribe(System.out::println);
        };
    }

}

