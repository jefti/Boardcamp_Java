package com.boardcamp.api.services;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.CustomerDTO;
import com.boardcamp.api.exceptions.CustomerConflictException;
import com.boardcamp.api.exceptions.CustomerNotFoundException;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.repositories.CustomerRepository;

@Service
public class CustomerService {
    
    final CustomerRepository customerRepository;

    CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public CustomerModel save(CustomerDTO dto){
        if(existsByCpf(dto.getCpf())){
            throw new CustomerConflictException("CPF informado já está em uso.");
        }
        CustomerModel customer = new CustomerModel(dto);
        return customerRepository.save(customer);
    }

    public boolean existsByName(String name){
        return customerRepository.existsByName(name);
    }

    public boolean existsByCpf(String cpf){
        return customerRepository.existsByCpf(cpf);
    }

    public CustomerModel findByCustomerId(long id){
        return customerRepository.findById(id).orElseThrow(
            ()-> new CustomerNotFoundException("Usuario com id '"+ id +"' não encontrado.")
        );
    }
}
