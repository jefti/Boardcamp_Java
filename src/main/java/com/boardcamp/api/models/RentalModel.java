package com.boardcamp.api.models;

import java.time.LocalDate;

import com.boardcamp.api.dtos.RentalDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rentals")
public class RentalModel {

    public RentalModel(RentalDTO dto, GameModel game, CustomerModel customer){
        this.daysRented = dto.getDaysRented();
        this.rentDate = LocalDate.now();
        this.originalPrice = dto.getDaysRented() * game.getPricePerDay();
        this.returnDate = null;
        this.delayFee = 0;
        this.game = game;
        this.customer = customer;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private LocalDate rentDate;

    @Column(nullable = false)
    private int daysRented;

    @Column(nullable = true)
    private LocalDate returnDate;

    @Column(nullable = false)
    private int originalPrice;

    @Column(nullable = false)
    private int delayFee;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private GameModel game;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerModel customer;
}
