package com.bankingapp.dto;

import com.bankingapp.model.Transaction;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public record TransactionResponse(
        String id,
        String date,
        String state,
        String type,
        String currency,
        BigDecimal amount,
        String note
) {
    public static TransactionResponse fromEntity(Transaction tx) {
        return new TransactionResponse(
                tx.getId(),
                tx.getTimestamp().format(DateTimeFormatter.ofPattern("dd/MM/yyyy @ HH:mm:ss")) + " CEST",
                tx.getState().toString(),
                tx.getType().toString(),
                tx.getCurrency().getCurrencyCode(),
                tx.getAmount().stripTrailingZeros(),
                tx.getNote()
        );
    }
}
