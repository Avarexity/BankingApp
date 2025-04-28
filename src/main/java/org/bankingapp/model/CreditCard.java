package org.bankingapp.model;

import java.util.Date;

public class CreditCard extends Card {
    private double drawLimit;

    public CreditCard(String number, Date expiryDate, String cvv, Account account, String pin, double creditLimit) {
        super(number, expiryDate, cvv, account, pin);
        if (creditLimit <= 0) {
            throw new IllegalArgumentException("Credit limit must be positive.");
        }
        this.drawLimit = creditLimit;
    }

    public double getDrawLimit() { return drawLimit; }

    public void setDrawLimit(double drawLimit) {
        if (drawLimit > 0) {
            this.drawLimit = drawLimit;
        }
    }
}
