package com.bankingapp.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a one-time use card in the banking system.
 * Extends the base Card class with single-use functionality.
 *
 * @author Avarexity - Whard A.
 */
@Entity
@DiscriminatorValue("OT")
public class OTCard extends Card {
    private boolean used = false;
    private BigDecimal maxDraw;

    /**
     * No-arg constructor for JPA
     */
    protected OTCard() {}

    /**
     * Constructs a new OTCard with full parameters.
     *
     * @param number The card number
     * @param expiryDate The expiration date
     * @param cvv The card verification value
     * @param account The associated account
     * @param pin The PIN code
     * @param drawLimit The maximum draw limit
     */
    public OTCard(String number, LocalDate expiryDate, String cvv, Account account, String pin, BigDecimal drawLimit) {
        super(number, expiryDate, cvv, account, pin, drawLimit);
    }

    /**
     * Constructs a new OTCard with default parameters.
     *
     * @param number The card number
     * @param expiryDate The expiration date
     * @param cvv The card verification value
     * @param account The associated account
     */
    public OTCard(String number, LocalDate expiryDate, String cvv, Account account) {
        super(number, expiryDate, cvv, account);
        this.maxDraw = BigDecimal.valueOf(100_000.0);
        this.used = false;
    }

    public OTCard(String number, LocalDate expiryDate, String cvv, Account account, BigDecimal maxDraw) {
        super(number, expiryDate, cvv, account);
        if (maxDraw.compareTo(BigDecimal.ZERO) > 0) this.maxDraw = maxDraw;
        else throw new IllegalArgumentException("Maximum draw limit must be positive.");
        this.used = false;
    }

    /**
     * Checks if the card has been used.
     * @return true if used, false otherwise
     */
    public boolean isUsed() { return used; }

    /**
     * Marks the card as used.
     */
    public void use() { this.used = true; }

    /**
     * Returns the type of the One-Time Use card.
     * @return "One-Time Use", a String of the card type
     */
    @Override
    public String getType() {
        return "One-Time Use";
    }

    /**
     * Attempts to authorize a payment using the card
     *
     * @param amount, the amount of money to be sent
     * @return true - if the transaction was successful, otherwise false
     */
    @Override
    public boolean authorizePayment(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) > 0 && this.getAccount() != null &&
                this.getAccount().getBalance().compareTo(amount) >= 0 &&
                !used && validExp()) {
            this.getAccount().setBalance(this.getAccount().getBalance().subtract(amount));
            this.use();
            return true;
        } else return false;
    }
}
