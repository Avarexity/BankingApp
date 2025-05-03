package com.bankingapp.dto;

import com.bankingapp.model.Account;

import java.math.BigDecimal;

public record AccountResponse(
        Long id,
        String name,
        String currency,
        BigDecimal balance
) {
    public static AccountResponse fromEntity(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getName(),
                account.getCurrency().getCurrencyCode(),
                account.getBalance()
        );
    }
}
