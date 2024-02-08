package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.boardcamp.api.dtos.CustomerDTO;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.repositories.RentalsRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class CustomerIntegrationTests {
    
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired    
    private CustomerRepository customerRepository;
    @Autowired    
    private RentalsRepository rentalsRepository;

    @BeforeEach
    void CleanUpDatabaseBefore(){
        rentalsRepository.deleteAll();
        customerRepository.deleteAll();
    }


    @AfterAll
    void CleanUpDatabaseAfter(){
        rentalsRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    void GivenRepeatCustomer_WhenCreating_ThrowsException(){

        CustomerDTO customerDTO = new CustomerDTO("game", "12345678910");
        CustomerModel customer = new CustomerModel(customerDTO);
        HttpEntity body = new HttpEntity<>(customerDTO);
        customerRepository.save(customer);

        ResponseEntity<String> response = restTemplate.exchange(
            "/customers",
            HttpMethod.POST,
            body,
            String.class
        );

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void GivenValidGame_WhenCreating_CreateGame(){

        CustomerDTO customerDTO = new CustomerDTO("game", "12345678910");
        CustomerModel customer = new CustomerModel(customerDTO);
        HttpEntity body = new HttpEntity<>(customer);


        ResponseEntity<String> response = restTemplate.exchange(
            "/customers",
            HttpMethod.POST,
            body,
            String.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, customerRepository.count()); 
    }

}
