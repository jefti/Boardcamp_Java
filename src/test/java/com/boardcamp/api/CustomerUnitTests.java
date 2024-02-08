package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.boardcamp.api.dtos.CustomerDTO;
import com.boardcamp.api.exceptions.CustomerConflictException;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.services.CustomerService;

@SpringBootTest
class CustomerUnitTests {
    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void givenRepeatedCustomer_WhenCreating_thenThrowConflict(){
        CustomerDTO customer = new CustomerDTO("name", "12345678910");
        when(customerRepository.existsByCpf("12345678910")).thenReturn(true);

        CustomerConflictException exception = assertThrows(
            CustomerConflictException.class, 
            ()-> customerService.save(customer));
        
        assertNotNull(exception);
        assertEquals(
            "CPF informado já está em uso.", 
            exception.getMessage());
        verify(customerRepository, times(0)).save(any());
    }

    @Test
    void givenValidBody_WhenCreating_thenCreateCostumer(){
        CustomerDTO customer = new CustomerDTO("name", "12345678910");
        CustomerModel createdCustomer = new CustomerModel(customer);
        when(customerRepository.existsByCpf("12345678910")).thenReturn(false);
        when(customerRepository.save(any())).thenReturn(createdCustomer);

        CustomerModel response = customerService.save(customer);
        
        assertNotNull(response);
        verify(customerRepository, times(1)).existsByCpf(any());
        verify(customerRepository, times(1)).save(any());
        assertEquals(createdCustomer, response);
    }
}
