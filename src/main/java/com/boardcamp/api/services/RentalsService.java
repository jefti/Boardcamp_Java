package com.boardcamp.api.services;


import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.RentalDTO;
import com.boardcamp.api.exceptions.CustomerNotFoundException;
import com.boardcamp.api.exceptions.GameNotFoundException;
import com.boardcamp.api.exceptions.RentalNotFoundException;
import com.boardcamp.api.exceptions.RentalUnprocessableEntityException;
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

    public RentalModel save(RentalDTO dto){
        CustomerModel customer = customerRepository.findById(dto.getCustomerId()).orElseThrow(
            ()-> new CustomerNotFoundException("Not found Customer with id "+ dto.getCustomerId() + ".")
        );
        
        GameModel game = gamesRepository.findById(dto.getGameId()).orElseThrow(
            ()-> new GameNotFoundException("Not found Game with id "+ dto.getGameId()+".")
        );
        
        List<RentalModel> rentals = rentalsRepository.findValidRentalById(dto.getGameId());

        if(rentals.size() >= game.getStockTotal()){
            throw new RentalUnprocessableEntityException("O jogo "+ game.getName()+" Possui "+ game.getStockTotal() + " cópias disponiveis, e todas já estão alugadas no momento." );
        }
        RentalModel rental = new RentalModel(dto, game, customer);
        return rentalsRepository.save(rental);
    }

    public List<RentalModel> findAll(){
        return rentalsRepository.findAll();
    }

    public RentalModel updateById(long id){
        RentalModel rental = rentalsRepository.findById(id).orElseThrow(
            ()-> new RentalNotFoundException("Aluguel não encontrado.")
        );

        if(rental.getReturnDate() != null){
            throw new RentalUnprocessableEntityException("Aluguel informado já está concluido.");
        }

        LocalDate today = LocalDate.now();
        Duration duration = Duration.between(rental.getRentDate().atStartOfDay(), today.atStartOfDay());
        rental.setReturnDate(today);
    
        long days = duration.toDays();
        if (days > rental.getDaysRented()) {
            int pricePerDay = rental.getGame().getPricePerDay();
            int lateFee = pricePerDay * (int)(days - rental.getDaysRented());
            rental.setDelayFee(lateFee);
        }

        return rentalsRepository.save(rental);
    }
}
