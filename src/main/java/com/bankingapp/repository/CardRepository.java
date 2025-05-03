package com.bankingapp.repository;

import com.bankingapp.model.Account;
import com.bankingapp.model.Card;
import com.bankingapp.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    // Find all cards owned by a specific user
    List<Card> findByOwner(User owner);

    // Find cards by name (exact match)
    List<Card> findByName(String name);

    // Find cards by name (containing pattern)
    List<Card> findByNameContaining(String namePattern);

    // Find cards by type
    List<Card> findByType(String type);

    // Find cards by expiration date
    List<Card> findByExpirationDate(LocalDate expirationDate);

    // Find cards by account
    List<Card> findByAccount(Account account);

    // Find cards by card number
    Card findByNumber(@NotNull String number);

    // Check if a card with a certain number exists
    boolean existsByNumber(@NotNull String number);

    // Count cards for a specific owner
    Long countByOwner(User owner);
}
