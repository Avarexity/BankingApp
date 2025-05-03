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

    public Transaction findById(Long id) {
        if (repo.existsById(id)) {
            return repo.getReferenceById(id);
        } else {
            return null;
        }
    }

    public List<Transaction> findByAccount(Account account) {
        if (repo.existsByAccount(account)) {
            return repo.findByAccount(account);
        } else {
            return null;
        }
    }

    public List<Transaction> findByInstitute(Institute institute) {
        if (repo.existsByInstitute(institute)) {
            return repo.findByInstitute(institute);
        } else {
            return null;
        }
    }

    public List<Transaction> findByInstituteType(InstituteType type) {
        if (repo.existsByInstituteType(type)) {
            return repo.findByInstituteType(type);
        } else {
            return null;
        }
    }

    public List<Transaction> findByMaker(User maker) {
        if (repo.existsByMaker(maker)) {
            return repo.findByMaker(maker);
        } else {
            return null;
        }
    }

    public List<Transaction> findByDateBetween(LocalDate startDate, LocalDate endDate) {
        if (repo.existsByDateBetween(startDate, endDate)) {
            return repo.findByDateBetween(startDate, endDate);
        } else {
            return null;
        }
    }

    public List<Transaction> findByAmount(BigDecimal min, BigDecimal max) {
        if (repo.existsByAmountBetween(min, max)) {
            return repo.findByAmount(min, max);
        } else {
            return null;
        }
    }
}
