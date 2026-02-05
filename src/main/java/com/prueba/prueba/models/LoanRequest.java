package com.prueba.prueba.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LoanRequest {
    
    private Long id;

    private String requesterName;

    private BigDecimal requestedAmount;

    private String currency;

    private String identification;

    private LocalDateTime createdAt;

    private LoanStatus status;
}
