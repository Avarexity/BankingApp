package com.bankingapp.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Abstract base class representing a bank card in the banking system.
 * Provides common functionality for all card types (Credit, Debit, One-Time).
 *
 *  @author Avarexity - Whard A.
 */
@Entity
@Table(name = "cards")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "card_type")
public abstract class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 16)
    private String number;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate expiryDate;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private String cvv;
    private String pin;
    private boolean ot = false;

    /** Pattern for validating PIN numbers (3-6 digits) */
    private static final Pattern PIN_PATTERN = Pattern.compile("^\\d{3,6}$");

    /** Pattern validation for card number (16 digits) */
    private static final Pattern CARD_PATTERN = Pattern.compile("^\\d{4}-\\d{4}-\\d{4}-\\d{4}$");

    /**
     * No-arg constructor for JPA
     */
    protected Card() {}

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
     * Validates if a given card is correct
     *
     * @param card
     * @return
     */
    public static boolean isValidCard(String card) { return CARD_PATTERN.matcher(card).matches(); }

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
    }

    // ------------ GETTERS ------------
    public String getNumber() { return number; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public String getCvv() { return cvv; }
    public Account getAccount() { return account; }
    public String getPin() { return pin; }
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

    public void setAccount(Account account) {
        Objects.requireNonNull(account);
        this.account = account;
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
                account.getCurrency().getSymbol());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return number.equals(card.number);
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }
}