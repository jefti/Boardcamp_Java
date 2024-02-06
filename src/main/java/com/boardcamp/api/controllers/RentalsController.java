package com.boardcamp.api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardcamp.api.dtos.RentalDTO;
import com.boardcamp.api.models.RentalModel;
import com.boardcamp.api.services.RentalsService;

import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/rentals")
public class RentalsController {
    final RentalsService rentalsService;

    RentalsController(RentalsService rentalsService){
        this.rentalsService = rentalsService;
    }

    @PostMapping
    public ResponseEntity<RentalModel> createRental(@RequestBody @Valid RentalDTO body){
        Optional<RentalModel> rental = rentalsService.save(body);
        if(!rental.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(rental.get());
    } 
    
    @GetMapping
    public List<RentalModel> findAll(){
        
    }
}
