package com.bankingapp.model;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a debit card in the banking system.
 * Extends the base Card class with debit-specific functionality.
 *
 * @author Avarexity - Whard A.
 */
@Entity
@DiscriminatorValue("Debit")
public class DebitCard extends Card {

    private BigDecimal dailyLimit;
    private BigDecimal dailySpent;
    private int dailyUsed;
    private int dailyUses;

    /**
     * No-arg constructor for JPA
     */
    protected DebitCard() {}

    /**
     * Constructs a new DebitCard with full parameters.
     *
     * @param number The card number
     * @param expiryDate The expiration date
     * @param cvv The card verification value
     * @param account The associated account
     * @param pin The PIN code
     * @param drawLimit The maximum draw limit
     */
    public DebitCard(String number, LocalDate expiryDate, String cvv, Account account, String pin, BigDecimal drawLimit, BigDecimal dailyLimit, int dailyUses) {
        super(number, expiryDate, cvv, account, pin, drawLimit);
        if (dailyLimit.compareTo(BigDecimal.ZERO) > 0) this.dailyLimit = dailyLimit;
        else throw new IllegalArgumentException("Daily limit must be positive.");
        if (dailyUses < 0 || dailyUses > 30) throw new IllegalArgumentException("Daily uses must be between 0 and 30.");
        else this.dailyUses = dailyUses;
    }

    /**
     * Constructs a new DebitCard with default parameters.
     *
     * @param number The card number
     * @param expiryDate The expiration date
     * @param cvv The card verification value
     * @param account The associated account
     */
    public DebitCard(String number, LocalDate expiryDate, String cvv, Account account) {
        super(number, expiryDate, cvv, account);
        this.dailyLimit = BigDecimal.valueOf(100_000.0);
        this.dailyUses = 15;
        this.dailySpent = BigDecimal.ZERO;
        this.dailyUsed = 0;
    }

    /**
     * Returns the type of the debit card as a String.
     * @return "Debit", the type of the card as a String.
     */
    @Override
    public String getType() {
        return "Debit";
    }

    /**
     * Attempts to authorize a payment using the card
     *
     * @param amount, the amount of money to be sent
     * @return true - if the transaction was successful, otherwise false
     */
    @Override
    public boolean authorizePayment(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) > 0 &&
                this.dailyLimit.compareTo(amount.add(this.dailySpent)) > -1 &&
                this.dailyUsed < this.dailyUses && this.getAccount() != null &&
                this.getAccount().getBalance().compareTo(amount) >= 0 && validExp()) {
            this.getAccount().setBalance(this.getAccount().getBalance().subtract(amount));
            dailySpent = dailySpent.add(amount);
            dailyUsed++;
            return true;
        } else return false;
    }
}
