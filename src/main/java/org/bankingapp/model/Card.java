package org.bankingapp.model;

import java.util.Date;
import java.util.regex.Pattern;

/**
 * Abstract base class representing a bank card in the banking system.
 * Provides common functionality for all card types (Credit, Debit, One-Time).
 */
public abstract class Card {
    private final String number;
    private final Date expiryDate;
    private final String cvv;
    private final Account account;
    private String pin;
    private double drawLimit;
    private double currentDraw = 0.0;
    private boolean isot = false;
    private OTCard otCard = null;

    /** Pattern for validating PIN numbers (3-6 digits) */
    private static final Pattern PIN_PATTERN = Pattern.compile("^\\d{3,6}$");

    /**
     * Validates if a given PIN string meets security requirements.
     *
     * @param pin The PIN to validate
     * @return true if the PIN is valid (3-6 digits), false otherwise
     */
    public static boolean isValidPin(String pin) {
        return PIN_PATTERN.matcher(pin).matches();
    }

    /**
     * Constructs a new Card with full parameters.
     *
     * @param number The card number
     * @param expiryDate The expiration date
     * @param cvv The card verification value
     * @param account The associated account
     * @param pin The PIN code
     * @param drawLimit The maximum draw limit
     */
    public Card(String number, Date expiryDate, String cvv, Account account, String pin, double drawLimit) {
        this.number = number;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.account = account;
        this.pin = pin;
        this.drawLimit = drawLimit;
    }

    /**
     * Constructs a new Card with default draw limit (10000.0).
     *
     * @param number The card number
     * @param expiryDate The expiration date
     * @param cvv The card verification value
     * @param account The associated account
     */
    public Card(String number, Date expiryDate, String cvv, Account account) {
        this.number = number;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.account = account;
        this.drawLimit = 10000.0;
    }

    // ------------ GETTERS ------------
    public String getNumber() { return number; }
    public Date getExpiryDate() { return expiryDate; }
    public String getCvv() { return cvv; }
    public Account getAccount() { return account; }
    public String getPin() { return pin; }
    public double getDrawLimit() { return drawLimit; }
    public double getCurrentDraw() { return currentDraw; }
    public boolean isOT() { return isot; }
    // ---------------------------------

    // ------------ SETTERS ------------
    /**
     * Sets a new PIN for the card.
     *
     * @param pin The new PIN to set
     * @throws IllegalArgumentException if the PIN is invalid
     */
    public void setPin(String pin) {
        if (!isValidPin(pin)) {
            throw new IllegalArgumentException("Invalid PIN.");
        } else this.pin = pin;
    }

    /**
     * Sets the draw limit for the card.
     *
     * @param drawLimit The new draw limit
     * @throws IllegalArgumentException if the limit is not positive
     */
    public void setDrawLimit(double drawLimit) {
        if (drawLimit > 0) this.drawLimit = drawLimit;
        else throw new IllegalArgumentException("Draw limit must be positive.");
    }

    /**
     * Sets the current drawn amount.
     *
     * @param currentDraw The amount currently drawn
     * @throws IllegalArgumentException if the amount is not positive
     */
    public void setCurrentDraw(double currentDraw) {
        if (currentDraw > 0.0) {
            this.currentDraw = currentDraw;
        } else throw new IllegalArgumentException("Money drawn must be positive.");
    }

    /**
     * Sets whether this is a one-time card.
     *
     * @param isot true if one-time, false otherwise
     */
    public void setOT(boolean isot) {
        this.isot = isot;
    }
    // ---------------------------------

    @Override
    public String toString() {
        return String.format("Card Number: " + number + ", Exp. Date: " + expiryDate + ", CVV: " + cvv +
                ", Owned By: " + account.getOwner().getName());
    }

    /**
     * Gets the type of the card (implemented by subclasses).
     *
     * @return The card type as String
     */
    public abstract String getType();

    /**
     * Gets the associated one-time card if this is a one-time card.
     *
     * @return The OTCard instance or null
     */
    public OTCard getOTCard() {
        return otCard;
    }
}
