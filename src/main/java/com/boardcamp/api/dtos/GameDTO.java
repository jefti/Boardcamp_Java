package com.boardcamp.api.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GameDTO {

    @NotBlank(message = "Field name is mandatory.")
    private String name;

    @NotBlank(message = "Field image url is mandatory.")
    private String image;

    @NotNull(message = "Field stock cannot be null.")
    @Min(value = 1, message = "Stock cannot be less then 1.")
    private int stockTotal;

    @NotNull(message = "Field price per day cannot be null.")
    @Min(value = 1, message = "Price per day cannot be less then 1.")
    private int pricePerDay;
}
