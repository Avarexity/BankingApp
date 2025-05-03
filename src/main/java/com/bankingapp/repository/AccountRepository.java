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
    // Find all accounts owned by a specific user
    List<Account> findByOwner(User owner);

    // Find accounts by name (exact match)
    List<Account> findByName(String name);

    // Find accounts by name (containing pattern)
    List<Account> findByNameContaining(String namePattern);

    // Find accounts with a specific currency
    List<Account> findByCurrency(Currency currency);

    // Find accounts with balance between two values
    List<Account> findByBalanceBetween(BigDecimal minBalance, BigDecimal maxBalance);

    // Count accounts for a specific user
    long countByOwner(User owner);

    // Check if an account with given ID exists
    boolean existsById(Long id);
}