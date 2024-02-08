package com.boardcamp.api.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RentalDTO {
    @NotNull(message = "Field customerId is mandatory.")
    @Min(value = 1, message = "Customer id cannot be less then 1.")
    private long customerId;

    @NotNull(message = "Field gameId is mandatory.")
    @Min(value = 1, message = "Game id cannot be less then 1.")
    private long gameId;

    @NotNull(message = "Field daysRented is mandatory.")
    @Min(value = 1, message = "Days rented cannot be less then 1.")
    private int daysRented;
}
