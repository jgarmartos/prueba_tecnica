package com.prueba.prueba.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.prueba.dtos.LoanRequestDTO;
import com.prueba.prueba.dtos.NewStatusDTO;
import com.prueba.prueba.models.LoanRequest;
import com.prueba.prueba.services.LoanService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    private final LoanService loansService;

    public LoanController(LoanService loansService) {
        this.loansService = loansService;
    }

    // GET /api/v1/loans
    @GetMapping
    public ResponseEntity<List<LoanRequest>> getAllLoans() {
        return ResponseEntity.ok(loansService.getAllLoanRequests());
    }

    // POST /api/v1/loans
    @PostMapping
    public ResponseEntity<LoanRequest> createLoan(@Valid @RequestBody LoanRequestDTO request) {
        LoanRequest loanRequest = loansService.createLoan(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(loanRequest);
    }

    // GET /api/v1/loans/{id}
    @GetMapping("/{id}")
    public ResponseEntity<LoanRequest> getLoanById(@PathVariable Long id) {
        LoanRequest loan = loansService.getLoanRequestById(id);
        return ResponseEntity.ok(loan);
    }

    // PATCH /api/v1/loans/{id}/status
    @PatchMapping("/{id}/status")
    public ResponseEntity<LoanRequest> updateStatus(@PathVariable Long id,@Valid @RequestBody NewStatusDTO status) {
        loansService.updateStatus(id, status);
        return ResponseEntity.ok(loansService.getLoanRequestById(id));
    }
}
