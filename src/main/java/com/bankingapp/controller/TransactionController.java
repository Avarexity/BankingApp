package com.bankingapp.controller;

import com.bankingapp.dto.TransferRequest;
import com.bankingapp.dto.TransferResponse;
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
    public TransferResponse transfer(
            @Valid @RequestBody TransferRequest request) {
        return TransferResponse.fromEntity(service.transfer(request));
    }
}
