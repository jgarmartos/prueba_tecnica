package com.prueba.prueba.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.prueba.prueba.dtos.LoanRequestDTO;
import com.prueba.prueba.dtos.NewStatusDTO;
import com.prueba.prueba.exceptions.InvalidStatusTransitionException;
import com.prueba.prueba.exceptions.LoanNotFoundException;
import com.prueba.prueba.models.LoanRequest;
import com.prueba.prueba.models.LoanStatus;

@Service("loansService")
public class LoanService {

    private final Map<Long, LoanRequest> loanRequests = new HashMap<>();
    private Long idGenerator = 1L;

    public LoanRequest createLoan(LoanRequestDTO dto) {
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setId(idGenerator++);
        loanRequest.setCurrency(dto.getCurrency());
        loanRequest.setIdentification(dto.getIdentification());
        loanRequest.setRequestedAmount(dto.getRequestedAmount());
        loanRequest.setRequesterName(dto.getRequesterName());
        loanRequest.setCreatedAt(LocalDateTime.now());
        loanRequest.setStatus(LoanStatus.PENDIENTE);

        loanRequests.put(loanRequest.getId(), loanRequest);

        return loanRequest;
    }

    public List<LoanRequest> getAllLoanRequests() {
        return new ArrayList<>(loanRequests.values());
    }

    public LoanRequest getLoanRequestById(Long id) {
        LoanRequest loan = loanRequests.get(id);
        if (loan == null) {
            throw new LoanNotFoundException(id);
        }
        return loan;
    }


    public void updateStatus(Long id, NewStatusDTO statusDTO) {
        LoanRequest existingLoan = loanRequests.get(id);
        if (existingLoan == null) {
            throw new LoanNotFoundException(id);
        }
        if (validatingNewStatus(statusDTO.getStatus(), existingLoan.getStatus())) {
            existingLoan.setStatus(statusDTO.getStatus());
        } else {
            throw new InvalidStatusTransitionException(existingLoan.getStatus(), statusDTO.getStatus());
        }
    }

    private boolean validatingNewStatus(LoanStatus newStatus, LoanStatus currentStatus) {
        if (currentStatus == LoanStatus.PENDIENTE) {
            return newStatus == LoanStatus.APROBADA || newStatus == LoanStatus.RECHAZADA;
        } else if (currentStatus == LoanStatus.APROBADA) {
            return newStatus == LoanStatus.CANCELADA;
        }
        return false;
    }
    
}
