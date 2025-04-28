package org.bankingapp.model;

import java.util.Date;
import java.util.regex.Pattern;

abstract class Card {
    private final String number;
    private final Date expiryDate;
    private final String cvv;
    private final Account account;
    private String pin;

    private static final Pattern PIN_PATTERN = Pattern.compile("^\\d{3,6}$");

    public static boolean isValidPin(String pin) {
        return PIN_PATTERN.matcher(pin).matches();
    }

    public Card(String number, Date expiryDate, String cvv, Account user, String pin) {
        this.number = number;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.account = user;
        this.pin = pin;
    }

    public String getNumber() { return number; }
    public Date getExpiryDate() { return expiryDate; }
    public String getCvv() { return cvv; }
    public Account getAccount() { return account; }
    public String getPin() { return pin; }

    public void setPin(String pin) {
        if (!isValidPin(pin)) {
            throw new IllegalArgumentException("Invalid PIN.");
        } else this.pin = pin;
    }

    @Override
    public String toString() {
        return String.format("Card Number: " + number + ", Exp. Date: " + expiryDate + ", CVV: " + cvv +
                ", Owned By: " + account.getOwner().getName());
    }
}
