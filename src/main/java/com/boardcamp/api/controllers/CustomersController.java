package com.boardcamp.api.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardcamp.api.dtos.CustomerDTO;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.services.CustomerService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/customers")
public class CustomersController {
    final CustomerService customerService;

    CustomersController(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerModel> createCustomer(@RequestBody @Valid CustomerDTO body){
        if(customerService.existsByCpf(body.getCpf())){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        CustomerModel customer = customerService.save(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerModel> getCustomerById(@PathVariable Long id){
        Optional<CustomerModel> customer = customerService.findByCustomerId(id);
        if(!customer.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(customer.get());
    }
}
