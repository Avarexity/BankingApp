package com.bankingapp.dto;

import com.bankingapp.model.Card;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public record CardResponse(
        String number,
        String expiryDate,
        String currency,
        BigDecimal drawLimit,
        String type
) {
    public static CardResponse fromEntity(Card card) {
        return new CardResponse(
                card.getNumber().substring(0, 4) +
                        "********" +
                        card.getNumber().substring(card.getNumber().length() - 4),
                card.getExpiryDate().format(DateTimeFormatter.ofPattern("MM/yy")),
                card.getAccount().getCurrency().getCurrencyCode(),
                card.getDrawLimit(),
                card.getType()
        );
    }
}
