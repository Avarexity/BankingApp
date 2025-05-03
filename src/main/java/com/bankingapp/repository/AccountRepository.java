package com.bankingapp.repository;

import com.bankingapp.model.Account;
import com.bankingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    // get all accounts owned by a specific user
    List<Account> getByOwner(User owner);

    // get accounts by name (exact match)
    List<Account> getByName(String name);

    // get accounts by name (containing pattern)
    List<Account> getByNameContaining(String namePattern);

    // get accounts with a specific currency
    List<Account> getByCurrency(Currency currency);

    // get accounts with balance between two values
    List<Account> getByBalanceBetween(BigDecimal minBalance, BigDecimal maxBalance);

    // Count accounts for a specific user
    long countByOwner(User owner);

    // Check if an account with given ID exists
    boolean existsById(Long id);
}