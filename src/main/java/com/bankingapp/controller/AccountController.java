package com.bankingapp.controller;

import com.bankingapp.dto.AccountRequest;
import com.bankingapp.dto.AccountResponse;
import com.bankingapp.model.Account;
import com.bankingapp.model.User;
import com.bankingapp.service.AccountService;
import com.bankingapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;

    @Autowired
    public AccountController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
            @Valid @RequestBody AccountRequest request) {
        User user = userService.getById(request.userId());
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        Account account = accountService.createAccount(request.name(), request.currency(), user);

        return ResponseEntity.ok(AccountResponse.fromEntity(account));
    }

    public ResponseEntity<AccountResponse> getAccount(Long accountId, Long userId) {
        Account account = accountService.getById(accountId);
        User user = userService.getById(userId);

        if (account == null || !account.getOwner().equals(user)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(AccountResponse.fromEntity(account));
    }

    public ResponseEntity<AccountResponse> deleteAccount(Long id) {
        Account account = accountService.getById(id);

        if (account == null) {
            return ResponseEntity.notFound().build();
        }

        if (accountService.removeAccount(account)) {
            return ResponseEntity.ok().build();
        } else throw new RuntimeException("Account could not be deleted");
    }
}
