package com.bankingapp.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

/**
 * Represents a bank account in the system.
 * Contains account information, balance, and associated cards.
 *
 * @author Avarexity - Whard A.
 */
public class Account {
    private final Long id;
    private final String name;
    private final Currency currency;
    private BigDecimal balance;
    private final User owner;
    private List<Card> cards = new ArrayList<>();
    private TransactionHistory history;

    /**
     * Constructs a new Account with zero balance.
     *
     * @param id The account ID
     * @param name The account name
     * @param currency The account currency
     * @param owner The account owner
     */
    public Account(Long id, String name, Currency currency, User owner) {
        this.id = id;
        this.name = name;
        this.currency = currency;
        this.balance = BigDecimal.ZERO;
        this.owner = owner;
    }

    /**
     * Constructs a new Account with a specified balance.
     *
     * @param id The account ID
     * @param name The account name
     * @param currency The account currency
     * @param balance The initial balance
     * @param owner The account owner
     */
    public Account(Long id, String name, Currency currency, BigDecimal balance, User owner) {
        this.id = id;
        this.name = name;
        this.currency = currency;
        this.balance = Objects.requireNonNull(balance, "Balance cannot be null.");
        this.owner = owner;
    }

    // ------------ GETTERS ------------
    public Long getId() { return id; }
    public String getName() { return name; }
    public Currency getCurrency() { return currency; }
    public BigDecimal getBalance() { return balance; }
    public User getOwner() { return owner; }
    public List<Card> getCards() { return cards; }
    public TransactionHistory getHistory() { return history; }
    // ---------------------------------

    // ------------ SETTERS ------------
    /**
     * Sets the account balance.
     * @param balance The new balance
     * @throws IllegalArgumentException if balance is negative
     */
    public void setBalance(BigDecimal balance) {
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Balance cannot be negative.");
        }
        this.balance = Objects.requireNonNull(balance, "Balance cannot be null.");
    }

    /**
     * Sets the list of cards associated with this account.
     * @param cards The list of cards
     * @throws IllegalArgumentException if cards list is null
     */
    public void setCards(List<Card> cards) {
        if (cards != null) {
            this.cards = cards;
        } else throw new IllegalArgumentException("Card list cannot be null.");
    }

    public void setHistory(TransactionHistory history) {
        Objects.requireNonNull(history, "Transaction history cannot be null.");
        this.history = history;
    }
    // ---------------------------------

    /**
     * Deposits money into the account.
     *
     * @param amount The amount to deposit
     * @throws IllegalArgumentException if amount is not positive
     */
    public void deposit(BigDecimal amount) {
        Objects.requireNonNull(amount, "Amount cannot be null.");
        if (amount.compareTo(BigDecimal.ZERO) >= 0) {
            this.balance = this.balance.add(amount);
        } else throw new IllegalArgumentException("There must be an amount of money to deposit.");
    }

    /**
     * Withdraws money from the account.
     *
     * @param amount The amount to withdraw
     * @return true if withdrawal succeeded, false if insufficient funds
     */
    public boolean withdraw(BigDecimal amount) {
        Objects.requireNonNull(amount, "Amount cannot be null.");
        if (amount.compareTo(this.balance) <= 0) {
            this.balance = this.balance.subtract(amount);
            return true;
        }
        return false;
    }

    /**
     * Transfers money from this account to another (for use in transaction processing).
     *
     * @param amount The amount to transfer
     */
    public void transferMoney(Account to, BigDecimal amount) {
        if (withdraw(amount)) {
            to.receiveMoney(amount);
        } else throw new IllegalArgumentException("Cannot transfer money to another account.");
    }

    /**
     * Receives money into this account.
     *
     * @param amount The amount received
     * @throws IllegalArgumentException if amount is not positive
     */
    public void receiveMoney(BigDecimal amount) {
        Objects.requireNonNull(amount, "Amount cannot be null.");
        if (amount.compareTo(BigDecimal.ZERO) > 0) this.balance = this.balance.add(amount);
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
    public boolean payCard(Card card, BigDecimal amount) {
        Objects.requireNonNull(amount, "Amount cannot be null.");
        Objects.requireNonNull(card, "Card cannot be null.");
        if (cards.contains(card) && card.authorizePayment(amount)) {
            this.balance = this.balance.subtract(amount);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Account of: ID: %d, Name: %s | Owned by: %s | Balance: %s%s",
                id,
                name,
                owner.getName(),
                currency.getDisplayName(),
                balance.stripTrailingZeros().toPlainString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id.equals(account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
