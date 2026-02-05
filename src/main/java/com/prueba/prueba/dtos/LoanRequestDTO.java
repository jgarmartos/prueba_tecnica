package com.prueba.prueba.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class LoanRequestDTO {

    @NotBlank(message = "El nombre del solicitante no puede estar vacío")
    private String requesterName;

    @NotNull(message = "La cantidad solicitada no puede estar vacía")
    @Positive(message = "La cantidad solicitada debe ser un número positivo")
    private BigDecimal requestedAmount;

    @NotBlank(message = "La divisa no puede estar vacía")
    @Pattern(regexp = "^(USD|EUR|GBP|JPY|MXN|COP|ARS)$", message = "Divisa no válida. Divisas aceptadas: USD, EUR, GBP, JPY, MXN, COP, ARS")
    private String currency;

    @NotBlank(message = "La identificación no puede estar vacía")
    private String identification;

}
