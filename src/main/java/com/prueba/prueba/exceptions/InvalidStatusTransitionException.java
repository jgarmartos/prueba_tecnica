package com.prueba.prueba.exceptions;

import com.prueba.prueba.models.LoanStatus;

public class InvalidStatusTransitionException extends RuntimeException {
    public InvalidStatusTransitionException(LoanStatus currentStatus, LoanStatus newStatus) {
        super("Transición de estado no permitida: " + currentStatus + " a " + newStatus);
    }
}
