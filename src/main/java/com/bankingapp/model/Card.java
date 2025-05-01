package com.bankingapp.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * Abstract base class representing a bank card in the banking system.
 * Provides common functionality for all card types (Credit, Debit, One-Time).
 *
 *  @author Avarexity - Whard A.
 */
public abstract class Card {
    private final String number;
    private final LocalDate expiryDate;
    private final String cvv;
    private final Account account;
    private String pin;
    private BigDecimal drawLimit;
    private BigDecimal currentDraw = BigDecimal.ZERO;
    private boolean ot = false;
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
    public Card(String number, LocalDate expiryDate, String cvv, Account account, String pin, BigDecimal drawLimit) {
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
    public Card(String number, LocalDate expiryDate, String cvv, Account account) {
        this.number = number;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.account = account;
        this.drawLimit = BigDecimal.valueOf(10_000.0);
    }

    // ------------ GETTERS ------------
    public String getNumber() { return number; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public String getCvv() { return cvv; }
    public Account getAccount() { return account; }
    public String getPin() { return pin; }
    public BigDecimal getDrawLimit() { return drawLimit; }
    public BigDecimal getCurrentDraw() { return currentDraw; }
    public boolean isOT() { return ot; }
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
    public void setDrawLimit(BigDecimal drawLimit) {
        if (drawLimit.compareTo(BigDecimal.ZERO) > 0) this.drawLimit = drawLimit;
        else throw new IllegalArgumentException("Draw limit must be positive.");
    }

    /**
     * Sets the current drawn amount.
     *
     * @param currentDraw The amount currently drawn
     * @throws IllegalArgumentException if the amount is not positive
     */
    public void setCurrentDraw(BigDecimal currentDraw) {
        if (currentDraw.compareTo(BigDecimal.ZERO) > -1) this.currentDraw = currentDraw;
        else throw new IllegalArgumentException("Money drawn must be positive.");
    }

    /**
     * Sets whether this is a one-time card.
     *
     * @param ot true if one-time, false otherwise
     */
    public void setOT(boolean ot) {
        this.ot = ot;
    }
    // ---------------------------------

    /**
     * Gets the type of the card (implemented by subclasses).
     *
     * @return The card type as String
     */
    public abstract String getType();

    /**
     * Authorizes the payment made by the card (implemented by subclasses).
     *
     * @param amount, the amount of money to be transferred.
     * @return Whether the transaction was successful.
     */
    public abstract boolean authorizePayment(BigDecimal amount);

    /**
     * Gets the associated one-time card if this is a one-time card.
     *
     * @return The OTCard instance or null
     */
    public OTCard getOTCard() {
        return otCard;
    }

    /**
     * Makes the card a one-time use card.
     */
    public void makeOT() {
        if (otCard == null) {
            otCard = new OTCard(number, expiryDate, cvv, account, pin, drawLimit);
            ot = true;
        }
    }

    public boolean validExp() {
        return expiryDate.isAfter(LocalDate.now());
    }

    @Override
    public String toString() {
        return String.format("Card: %s. (Exp.: %s, CVV: %s) | Owned By: %s | Current Draw: %s%s/%s,",
                number,
                expiryDate.format(DateTimeFormatter.ofPattern("MM/yy")),
                cvv,
                account.getOwner().getName(),
                account.getCurrency().getSymbol(),
                currentDraw.stripTrailingZeros().toPlainString(),
                drawLimit.stripTrailingZeros().toPlainString());
    }
}
