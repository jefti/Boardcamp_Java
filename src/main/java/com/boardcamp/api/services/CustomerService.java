package com.boardcamp.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.CustomerDTO;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.repositories.CustomerRepository;

@Service
public class CustomerService {
    
    final CustomerRepository customerRepository;

    CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public CustomerModel save(CustomerDTO dto){
        CustomerModel customer = new CustomerModel(dto);
        return customerRepository.save(customer);
    }

    public boolean existsByName(String name){
        return customerRepository.existsByName(name);
    }

    public boolean existsByCpf(String cpf){
        return customerRepository.existsByCpf(cpf);
    }

    public Optional<CustomerModel> findByCustomerId(Long id){
        return customerRepository.findById(id);
    }
}
