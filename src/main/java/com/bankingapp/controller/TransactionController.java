package com.bankingapp.controller;

import com.bankingapp.dto.TransactionRequest;
import com.bankingapp.dto.TransactionResponse;
import com.bankingapp.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping("/transfer")
    public TransactionResponse transfer(
            @Valid @RequestBody TransactionRequest request) {
        return TransactionResponse.fromEntity(service.transfer(request));
    }
}
