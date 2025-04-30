package org.bankingapp.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

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
        if (amount.compareTo(BigDecimal.ZERO) > 0 &&
                this.getDrawLimit().compareTo(amount.add(this.getCurrentDraw())) > -1 &&
                this.getAccount().getBalance().compareTo(amount) >= 0 &&
                !used && validExp()) {
            this.setCurrentDraw(this.getCurrentDraw().add(amount));
            this.use();
            return true;
        } else return false;
    }
}
