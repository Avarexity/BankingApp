package com.bankingapp.dto;

import jakarta.validation.constraints.*;
import java.util.Currency;

public record AccountRequest(
        @NotNull(message = "User ID is required")
        Long userId,  // Not part of Account entity, but needed to link

        @NotBlank(message = "Account name is required")
        String name,

        @NotNull(message = "Currency is required")
        Currency currency
) {}