package org.bankingapp.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a debit card in the banking system.
 * Extends the base Card class with debit-specific functionality.
 *
 * @author Avarexity - Whard A.
 */
public class DebitCard extends Card {

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
    public DebitCard(String number, LocalDate expiryDate, String cvv, Account account, String pin, BigDecimal drawLimit) {
        super(number, expiryDate, cvv, account, pin, drawLimit);
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
    }

    @Override
    public String getType() {
        return "Debit";
    }
}
