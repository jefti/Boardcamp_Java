package com.boardcamp.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CustomerDTO {
    @NotBlank(message = "Field name is mandatory.")
    private String name;

    @NotBlank(message = "Field cpf is mandatory.")
    @Pattern(regexp = "^\\d{11}$", message = "Field cpf must contain only numeric characters.")
    private String cpf;
}
