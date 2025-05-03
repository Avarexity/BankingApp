package com.bankingapp.service;

import com.bankingapp.model.Account;
import com.bankingapp.model.User;
import com.bankingapp.repository.AccountRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepository repo;

    @Autowired
    public AccountService(AccountRepository repo) {
        this.repo = repo;
    }

    public Account createAccount(String name, Currency currency, User owner) {
        Account account = new Account(generateId(), name, currency, owner);
        return repo.save(account);
    }

    public boolean removeAccount(Account account) {
        try {
            repo.delete(account);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Account getById(Long id) {
        if (repo.existsById(id)) {
            return repo.getReferenceById(id);
        } else {
            return null;
        }
    }

    public List<Account> getByOwner(User owner) { return repo.getByOwner(owner); }

    private @NotNull Long generateId() { return repo.count() + 1; }
}

