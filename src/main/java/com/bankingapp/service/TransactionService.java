package com.bankingapp.service;

import com.bankingapp.model.*;
import com.bankingapp.repository.TransactionRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository repo;
    private TransactionHistory history;

    @Autowired
    public TransactionService(TransactionRepository repo) {
        this.repo = repo;
        this.history = new TransactionHistory();
    }

    public Transaction transfer(Account sender, Account receiver, Currency currency,
                                BigDecimal amount, String note) {
        Transaction tx = new Transaction(sender, receiver, currency,  amount, note);
        repo.save(tx);
        return tx;
    }

    public Transaction payment(Account sender, Institute merchant, Currency currency,
                               BigDecimal amount, String note) {
        Transaction tx = new Transaction(sender, merchant, currency, amount, note);
        repo.save(tx);
        return tx;
    }

    public TransactionHistory getTransactionHistory() { return history; }

    public void updateTransactionHistory(@NotNull Transaction tx) {
        if (history.getAccount().equals(tx.getSender())){
            history.addTransaction(tx);
        } else throw new IllegalArgumentException("Transaction must be sent from the same account as the transaction history.");
    }

    public void clearTransactionHistory() { history = new TransactionHistory(); }

    public Transaction getById(Long id) {
        if (repo.existsById(id)) {
            return repo.getReferenceById(id);
        } else {
            return null;
        }
    }

    public List<Transaction> getByAccount(Account account) {
        if (repo.existsByAccount(account)) {
            return repo.getByAccount(account);
        } else {
            return null;
        }
    }

    public List<Transaction> getByInstitute(Institute institute) {
        if (repo.existsByInstitute(institute)) {
            return repo.getByInstitute(institute);
        } else {
            return null;
        }
    }

    public List<Transaction> getByInstituteType(InstituteType type) {
        if (repo.existsByInstituteType(type)) {
            return repo.getByInstituteType(type);
        } else {
            return null;
        }
    }

    public List<Transaction> getByMaker(User maker) {
        if (repo.existsByMaker(maker)) {
            return repo.getByMaker(maker);
        } else {
            return null;
        }
    }

    public List<Transaction> getByDateBetween(LocalDate startDate, LocalDate endDate) {
        if (repo.existsByDateBetween(startDate, endDate)) {
            return repo.getByDateBetween(startDate, endDate);
        } else {
            return null;
        }
    }

    public List<Transaction> getByAmount(BigDecimal min, BigDecimal max) {
        if (repo.existsByAmountBetween(min, max)) {
            return repo.getByAmount(min, max);
        } else {
            return null;
        }
    }
}
