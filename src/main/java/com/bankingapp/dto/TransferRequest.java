package com.bankingapp.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record TransferRequest(
        @NotNull(message = "Sender ID is required.")
        Long senderId,

        @NotNull(message = "Receiver ID is required")
        Long receiverId,

        @DecimalMin(value = "0.0001", message = "Amount must be greater than 0")
        @DecimalMax(value = "5000000", message = "Amount must be less than 5,000,000")
        BigDecimal amount,

        String note
) {
}
