package com.bankingapp.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Currency;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CardRequest(
        @NotNull(message = "Card number is required")
        String number,

        @NotNull(message = "Card expiry date is required")
        LocalDate expiryDate,

        @NotNull(message = "Card verification value is required")
        String cvv,

        @NotNull(message = "Card PIN is required")
        String pin,

        @NotNull(message = "Card currency is required")
        Currency currency,

        BigDecimal drawLimit,

        @NotNull(message = "Card type is required")
        String type
) {
}
