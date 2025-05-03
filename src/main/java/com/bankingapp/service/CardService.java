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
            repo.delete(repo.findByNumber(number));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Card findCard(String number) { return repo.findByNumber(number); }

    public List<Card> findByAccount(Account account) { return repo.findByAccount(account); }

    public List<Card> findByOwner(User user) { return repo.findByOwner(user); }

    private @NotNull String generateNum() {
        final Random random = new Random();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append(random.nextInt(10));  // random digit 0-9
            if ((i + 1) % 4 == 0 && i != 15) {
                sb.append('-');  // add dash every 4 digits, except at the end
            }
        }

        return sb.toString();
    }

    private @NotNull LocalDate generateExpDate() {
        return LocalDate.now().plusYears(5);
    }

    private @NotNull String generateCVV() {
        int cvv = new Random().nextInt(900) + 100;

        return String.format("%03d", cvv);
    }
}
