package org.bankingapp.model;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private final int id;
    private final String name;
    private final String currency;
    private double balance;
    private final User owner;
    private List<Card> cards = new ArrayList<>();

    public Account(int id, String name, String currency, User owner) {
        this.id = id;
        this.name = name;
        this.currency = currency;
        this.balance = 0;
        this.owner = owner;
    }

    public Account(int id, String name, String currency, double balance, User owner) {
        this.id = id;
        this.name = name;
        this.currency = currency;
        this.balance = balance;
        this.owner = owner;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCurrency() { return currency; }
    public double getBalance() { return balance; }
    public User getOwner() { return owner; }
    public List<Card> getCards() { return cards; }

    public void setBalance(double balance) {
        if (balance >= 0) {
            this.balance = balance;
        } else throw new IllegalArgumentException("Balance must be positive.");
    }

    public void setCards(List<Card> cards) {
        if (cards != null) {
            this.cards = cards;
        } else throw new IllegalArgumentException("There must be at least one card.");
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        } else throw new IllegalArgumentException("There must be an amount of money to deposit.");
    }

    public boolean withdraw(double amount) {
        if (amount <= this.balance) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    public boolean transferMoney(double amount) {
        if (amount <= this.balance) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    public void recieveMoney(double amount) {
        if (amount > 0) this.balance += amount;
        else throw new IllegalArgumentException("Amount must be positive.");
    }

    public boolean addCard(Card card) {
        if (card != null) {
            this.cards.add(card);
            return true;
        }
        return false;
    }

    public boolean removeCard(Card card) {
        if (card != null && cards.contains(card)) {
            this.cards.remove(card);
            return true;
        }
        return false;
    }
}
