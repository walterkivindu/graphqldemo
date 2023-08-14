package com.example.graphqldemo.graphql;

import com.example.graphqldemo.models.Customer;
import com.example.graphqldemo.models.UserTypeRequest;
import com.example.graphqldemo.models.files.FileData;
import com.example.graphqldemo.services.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/base")
public class Customers {
    private final CustomerService service;

    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Value("${server.port}")
    private int port;

    public Customers(CustomerService service) {
        this.service = service;
    }

    @QueryMapping(name = "customers")
    public Flux<Customer> getAllCustomers() {
//        return Flux.just(new Customer(1, "Walter", "example@example.com", "5364646"), new Customer(2, "Mulwa", "example@example.com", "5364646"));


        return service.customerFlux();
    }

    @MutationMapping
    public Mono<Customer> create(@Argument com.example.graphqldemo.dto.Customer customer) {
        return service.customerMono(mapDTOToEntity(customer));
    }

    private Customer mapDTOToEntity(com.example.graphqldemo.dto.Customer customer) {
        return new Customer(customer.name(), customer.email(), customer.phone());
    }

    @GetMapping("/url")
    public String getCurrentContext(HttpServletRequest httpServerRequest) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        String uriString = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        String uriString1 = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        String uriString2 = ServletUriComponentsBuilder.fromCurrentServletMapping().build().toUriString();
        String uriString3 = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUriString();
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .replacePath(contextPath + "/base/url")
                .build()
                .toUriString();
        String url2 = ServletUriComponentsBuilder.fromCurrentContextPath()
                .build()
                .toUriString() + "/operators";
        String url3 = ServletUriComponentsBuilder.fromCurrentContextPath().replacePath(":" + port + "/" + contextPath + "/operators")
                .build()
                .toUriString();
        return url;
    }


    @QueryMapping(name = "customer" )
    public Mono<Customer> customer(@Argument("id") Integer id) {
        return getCustomerById(id);
    }

    @GetMapping("/{id}")
    public Mono<Customer> customerFetch(@PathVariable("id") Integer id) {
        return getCustomerById(id);
    }

    @PostMapping("/usertype")
    public String customerFetch(@RequestBody UserTypeRequest request) {
        String value = request.getUserType().getValue();
        return value;
    }

    private Mono<Customer> getCustomerById(Integer id) {
        return service.customerById(id);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @Valid FileData form) {
        try {
            String contentType = file.getContentType();
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            long size = file.getSize();
            String name = file.getName();

            Path directory = Paths.get("./uploads");
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            // Save the file to the local directory
            Path filePath = Paths.get(directory.toString(), file.getOriginalFilename());
            Files.write(filePath, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(form);
    }

}
