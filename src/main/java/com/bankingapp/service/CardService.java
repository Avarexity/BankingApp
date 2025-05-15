package com.bankingapp.service;

import com.bankingapp.model.Account;
import com.bankingapp.model.Card;
import com.bankingapp.model.CreditCard;
import com.bankingapp.model.User;
import com.bankingapp.repository.CardRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class CardService {
    private final CardRepository repo;
    private static final Random random = new Random();

    @Autowired
    public CardService(CardRepository repo) { this.repo = repo; }

    public void addCard(@NotNull String type, @NotNull Account owner) {
        Card card;
        if (type.equalsIgnoreCase("credit")) {
            card = new CreditCard(generateNum(), generateExpDate(), generateCVV(),
                    owner);
        }
    }

    public boolean removeCard(String number) {
        try {
            repo.delete(repo.getByNumber(number));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Card getCard(String number) { return repo.getByNumber(number); }

    public List<Card> getByAccount(Account account) { return repo.getByAccount(account); }

    public List<Card> getByOwner(User user) { return repo.getByOwner(user); }

    private static @NotNull String generateNum() {

        return String.format("%04d-%04d-%04d-%04d",
                random.nextInt(10_000),
                random.nextInt(10_000),
                random.nextInt(10_000),
                random.nextInt(10_000));
    }

    private @NotNull LocalDate generateExpDate() {
        return LocalDate.now().plusYears(5);
    }

    private @NotNull String generateCVV() {
        int cvv = new Random().nextInt(900) + 100;

        return String.format("%03d", cvv);
    }
}
