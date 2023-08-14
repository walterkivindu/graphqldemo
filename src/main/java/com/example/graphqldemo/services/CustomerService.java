package com.example.graphqldemo.services;

import com.example.graphqldemo.models.Customer;
import com.example.graphqldemo.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }


    public Flux<Customer> customerFlux() {
        return repository.findAll();
    }

    public Mono<Customer> customerMono(Customer customer) {
        return repository.save(customer);
    }

    public Mono<Customer> customerById(Integer id) {
        return repository.findByIdIs(id);
    }

}
