package org.bankingapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a bank account in the system.
 * Contains account information, balance, and associated cards.
 */
public class Account {
    private final int id;
    private final String name;
    private final String currency;
    private double balance;
    private final User owner;
    private List<Card> cards = new ArrayList<>();

    /**
     * Constructs a new Account with zero balance.
     *
     * @param id The account ID
     * @param name The account name
     * @param currency The account currency
     * @param owner The account owner
     */
    public Account(int id, String name, String currency, User owner) {
        this.id = id;
        this.name = name;
        this.currency = currency;
        this.balance = 0;
        this.owner = owner;
    }

    /**
     * Constructs a new Account with specified balance.
     *
     * @param id The account ID
     * @param name The account name
     * @param currency The account currency
     * @param balance The initial balance
     * @param owner The account owner
     */
    public Account(int id, String name, String currency, double balance, User owner) {
        this.id = id;
        this.name = name;
        this.currency = currency;
        this.balance = balance;
        this.owner = owner;
    }

    // ------------ GETTERS ------------
    public int getId() { return id; }
    public String getName() { return name; }
    public String getCurrency() { return currency; }
    public double getBalance() { return balance; }
    public User getOwner() { return owner; }
    public List<Card> getCards() { return cards; }
    // ---------------------------------

    // ------------ SETTERS ------------
    /**
     * Sets the account balance.
     * @param balance The new balance
     * @throws IllegalArgumentException if balance is negative
     */
    public void setBalance(double balance) {
        if (balance >= 0) {
            this.balance = balance;
        } else throw new IllegalArgumentException("Balance must be positive.");
    }

    /**
     * Sets the list of cards associated with this account.
     * @param cards The list of cards
     * @throws IllegalArgumentException if cards list is null
     */
    public void setCards(List<Card> cards) {
        if (cards != null) {
            this.cards = cards;
        } else throw new IllegalArgumentException("There must be at least one card.");
    }
    // ---------------------------------

    /**
     * Deposits money into the account.
     *
     * @param amount The amount to deposit
     * @throws IllegalArgumentException if amount is not positive
     */
    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        } else throw new IllegalArgumentException("There must be an amount of money to deposit.");
    }

    /**
     * Withdraws money from the account.
     *
     * @param amount The amount to withdraw
     * @return true if withdrawal succeeded, false if insufficient funds
     */
    public boolean withdraw(double amount) {
        if (amount <= this.balance) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    /**
     * Transfers money from this account (for use in transaction processing).
     *
     * @param amount The amount to transfer
     * @return true if transfer succeeded, false if insufficient funds
     */
    public boolean transferMoney(double amount) {
        if (amount <= this.balance) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    /**
     * Receives money into this account.
     *
     * @param amount The amount received
     * @throws IllegalArgumentException if amount is not positive
     */
    public void receiveMoney(double amount) {
        if (amount > 0) this.balance += amount;
        else throw new IllegalArgumentException("Amount must be positive.");
    }

    /**
     * Adds a card to this account.
     *
     * @param card The card to add
     * @return true if card was added successfully
     */
    public boolean addCard(Card card) {
        if (card != null) {
            this.cards.add(card);
            return true;
        }
        return false;
    }

    /**
     * Removes a card from this account.
     *
     * @param card The card to remove
     * @return true if card was removed successfully
     */
    public boolean removeCard(Card card) {
        if (card != null && cards.contains(card)) {
            this.cards.remove(card);
            return true;
        }
        return false;
    }

    /**
     * Processes a payment using a card associated with this account.
     *
     * @param card The card to use for payment
     * @param amount The payment amount
     * @return true if payment succeeded, false otherwise
     */
    public boolean payCard(Card card, double amount) {
        if (card != null && cards.contains(card)) {
            if (card.getType().equals("Credit") &&
                    card.getDrawLimit() <= card.getCurrentDraw() + amount) {
                balance -= amount;
                return true;
            } else if (card.getType().equals("Debit") &&
                    card.getDrawLimit() <= card.getCurrentDraw() + amount &&
                    balance >= amount) {
                balance -= amount;
                return true;
            } else if (card.getType().equals("One-Time Use") &&
                    card.getDrawLimit() <= card.getCurrentDraw() + amount &&
                    balance >= amount && card.getOTCard() != null &&
                    !card.getOTCard().isUsed()) {
                balance -= amount;
                card.getOTCard().use();
                return true;
            }
        }
        return false;
    }
}
