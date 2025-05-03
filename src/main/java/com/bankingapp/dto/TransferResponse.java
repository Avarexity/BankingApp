package com.bankingapp.dto;

import com.bankingapp.model.Transaction;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public record TransferResponse(
        String id,
        String date,
        String state,
        String type,
        String currency,
        BigDecimal amount,
        String note
) {
    public static TransferResponse fromEntity(Transaction tx) {
        return new TransferResponse(
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
