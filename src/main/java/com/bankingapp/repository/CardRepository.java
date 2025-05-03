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
    // get all cards owned by a specific user
    List<Card> getByOwner(User owner);

    // get cards by name (exact match)
    List<Card> getByName(String name);

    // get cards by name (containing pattern)
    List<Card> getByNameContaining(String namePattern);

    // get cards by type
    List<Card> getByType(String type);

    // get cards by expiration date
    List<Card> getByExpirationDate(LocalDate expirationDate);

    // get cards by account
    List<Card> getByAccount(Account account);

    // get cards by card number
    Card getByNumber(@NotNull String number);

    // Check if a card with a certain number exists
    boolean existsByNumber(@NotNull String number);

    // Count cards for a specific owner
    Long countByOwner(User owner);
}
