package com.bankingapp.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a credit card in the banking system.
 * Extends the base Card class with credit-specific functionality.
 *
 * @author Avarexity - Whard A.
 */
@Entity
@DiscriminatorValue("Credit")
public class CreditCard extends Card {

    private BigDecimal creditLimit;
    private BigDecimal creditUsed;

    /**
     * No-arg constructor for JPA
     */
    protected CreditCard() {}

    /**
     * Constructs a new CreditCard with full parameters.
     *
     * @param number The card number
     * @param expiryDate The expiration date
     * @param cvv The card verification value
     * @param account The associated account
     * @param pin The PIN code
     * @param drawLimit The maximum draw limit
     */
    public CreditCard(String number, LocalDate expiryDate, String cvv, Account account, String pin, BigDecimal drawLimit, BigDecimal creditLimit, BigDecimal creditUsed) {
        super(number, expiryDate, cvv, account, pin, drawLimit);
        this.creditLimit = creditLimit;
        this.creditUsed = creditUsed;
    }

    /**
     * Constructs a new CreditCard with default parameters.
     *
     * @param number The card number
     * @param expiryDate The expiration date
     * @param cvv The card verification value
     * @param account The associated account
     */
    public CreditCard(String number, LocalDate expiryDate, String cvv, Account account) {
        super(number, expiryDate, cvv, account);
        this.creditLimit = BigDecimal.valueOf(10_000.0);
        this.creditUsed = BigDecimal.ZERO;
    }

    /**
     * Gets the type of the credit card.
     *
     * @return "Credit", the type of the card as a string
     */
    @Override
    public String getType() {
        return "Credit";
    }

    /**
     * Attempts to authorize a payment using the card
     *
     * @param amount, the amount of money to be sent
     * @return true - if the transaction was successful, otherwise false
     */
    @Override
    public boolean authorizePayment(@NotNull BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) > 0 &&
                this.creditLimit.compareTo(amount.add(this.creditUsed)) > -1 &&
                this.getAccount() != null && validExp()) {
            this.getAccount().setBalance(this.getAccount().getBalance().subtract(amount));
            this.creditUsed = this.creditUsed.add(amount);
            return true;
        }
        return false;
    }
}
