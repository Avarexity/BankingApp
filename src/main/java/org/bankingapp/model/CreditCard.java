package org.bankingapp.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a credit card in the banking system.
 * Extends the base Card class with credit-specific functionality.
 *
 * @author Avarexity - Whard A.
 */
public class CreditCard extends Card {

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
    public CreditCard(String number, LocalDate expiryDate, String cvv, Account account, String pin, BigDecimal drawLimit) {
        super(number, expiryDate, cvv, account, pin, drawLimit);
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
    public boolean authorizePayment(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) > 0 &&
                this.getDrawLimit().compareTo(amount.add(this.getCurrentDraw())) > -1 && validExp()) {
             this.setCurrentDraw(this.getCurrentDraw().add(amount));
             return true;
        } else return false;
    }
}
