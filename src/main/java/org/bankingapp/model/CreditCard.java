package org.bankingapp.model;

import java.math.BigDecimal;
import java.util.Date;

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
    public CreditCard(String number, Date expiryDate, String cvv, Account account, String pin, BigDecimal drawLimit) {
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
    public CreditCard(String number, Date expiryDate, String cvv, Account account) {
        super(number, expiryDate, cvv, account);
    }

    @Override
    public String getType() {
        return "Credit";
    }
}
