package com.demo.project65.controller;

import static org.mockito.Mockito.times;

import java.util.UUID;

import com.demo.project65.domain.Customer;
import com.demo.project65.repository.CustomerRepository;
import com.demo.project65.service.CustomerService;
import com.demo.project65.service.DataService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

@RunWith(SpringRunner.class)
@WebFluxTest(controllers = {HomeController.class})
@Import(HomeController.class)
public class HomeControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    CustomerRepository customerRepository;

    @MockBean
    DataService dataService;

    @MockBean
    CustomerService customerService;

    @Test
    public void test_findAll() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(customerService.findAll())
                .thenReturn(Flux.just(Customer.builder().id(id).age(30).name("jack").build()));

        webTestClient.get().uri("/customer/all")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Customer.class);
        Mockito.verify(customerService, times(1)).findAll();

    }
}
