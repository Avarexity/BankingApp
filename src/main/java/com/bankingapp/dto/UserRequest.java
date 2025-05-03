package com.bankingapp.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserRequest(
        @NotNull(message = "User ID is required")
        Long id,

        @NotNull(message = "User name is required")
        String name,

        @NotNull(message = "User surname is required")
        String surname,

        @NotNull(message = "User date of birth is required")
        LocalDate dateOfBirth,

        @NotNull(message = "User email is required")
        String email,

        @NotNull(message = "User phone number is required")
        String phone,

        @NotNull(message = "User password is required")
        char[] password
) {
}
