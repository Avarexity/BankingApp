package com.bankingapp.controller;

import com.bankingapp.dto.TransactionRequest;
import com.bankingapp.dto.TransactionResponse;
import com.bankingapp.model.Account;
import com.bankingapp.model.Institute;
import com.bankingapp.model.Transaction;
import com.bankingapp.service.AccountService;
import com.bankingapp.service.InstituteService;
import com.bankingapp.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService service;
    private final AccountService accountService;
    private final InstituteService instituteService;

    @Autowired
    public TransactionController(TransactionService service, AccountService accountService, InstituteService instituteService) {
        this.service = service;
        this.accountService = accountService;
        this.instituteService = instituteService;
    }

    @PostMapping("/transfer")
    public TransactionResponse transaction(
            @Valid @RequestBody TransactionRequest request) {
        // Use the transfer method from TransactionService which is more appropriate
        // We would need to fetch the accounts using an AccountService first
        Account sender = accountService.getById(request.senderId());
        Account receiver = accountService.getById(request.receiverId());

        if (sender == null || receiver == null) {
            throw new RuntimeException("Sender or receiver account not found");
        }

        Transaction tx = service.transfer(
            sender,
            receiver,
            sender.getCurrency(), // assuming we use sender's currency
            request.amount(),
            request.note()
        );

        return TransactionResponse.fromEntity(tx);
    }

    public TransactionResponse payment(
            @Valid @RequestBody TransactionRequest request) {
        Account sender = accountService.getById(request.senderId());
        Institute receiver = instituteService.getById(request.receiverId());

        if (sender == null || receiver == null) {
            throw new RuntimeException("Sender or receiver account not found");
        }

        Transaction tx = service.payment(
                sender,
                receiver,
                sender.getCurrency(),
                request.amount(),
                request.note()
        );

        return TransactionResponse.fromEntity(tx);
    }

    public TransactionResponse
}
