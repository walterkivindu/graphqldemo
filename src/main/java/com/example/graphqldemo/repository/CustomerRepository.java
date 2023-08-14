package com.example.graphqldemo.repository;

import com.example.graphqldemo.models.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.graphql.data.GraphQlRepository;
import reactor.core.publisher.Mono;

@GraphQlRepository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {

    Mono<Customer> findByIdIs(Integer integer);
}
