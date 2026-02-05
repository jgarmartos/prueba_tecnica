package com.prueba.prueba.dtos;

import com.prueba.prueba.models.LoanStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewStatusDTO {

    @NotNull(message = "El nuevo estado no puede estar vacío")
    private LoanStatus status;
    
}
