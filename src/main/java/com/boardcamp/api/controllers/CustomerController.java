package com.boardcamp.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
public class CustomerController {
    final CustomerService customerService;

    CustomerController(CustomerService customerService){
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
}
