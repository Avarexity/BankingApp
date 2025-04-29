package org.bankingapp.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a one-time use card in the banking system.
 * Extends the base Card class with single-use functionality.
 *
 * @author Avarexity - Whard A.
 */
public class OTCard extends Card {
    private boolean used = false;

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
        this.setOT(true);
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
        this.setOT(true);
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

    @Override
    public String getType() {
        return "One-Time Use";
    }
}
