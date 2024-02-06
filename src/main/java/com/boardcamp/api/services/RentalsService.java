package com.boardcamp.api.services;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.RentalDTO;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.models.RentalModel;
import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.repositories.GamesRepository;
import com.boardcamp.api.repositories.RentalsRepository;

@Service
public class RentalsService {
    final RentalsRepository rentalsRepository;
    final GamesRepository gamesRepository;
    final CustomerRepository customerRepository;

    RentalsService(RentalsRepository rentalsRepository, GamesRepository gamesRepository, CustomerRepository customerRepository){
        this.rentalsRepository = rentalsRepository;
        this.gamesRepository = gamesRepository;
        this.customerRepository = customerRepository;
    }

    public Optional<RentalModel> save(RentalDTO dto){
        Optional<CustomerModel> customer = customerRepository.findById(dto.getCustomerId());
        if(!customer.isPresent()){
            return Optional.empty();
        }
        Optional<GameModel> game = gamesRepository.findById(dto.getGameId());
        if(!game.isPresent()){
            return Optional.empty();
        }
        
        RentalModel rental = new RentalModel(dto, game.get(), customer.get());
        return Optional.of(rentalsRepository.save(rental));
    }

    public List<RentalModel> findAll(){
        return rentalsRepository.findAll();
    }
}
