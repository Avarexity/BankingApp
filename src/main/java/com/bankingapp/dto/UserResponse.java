package com.bankingapp.dto;

import com.bankingapp.model.User;

public record UserResponse(
        Long id,
        String name,
        String email,
        String phone
) {
    public static UserResponse fromEntity(User user) {
        return new UserResponse(
                user.getId(),
                String.format(("%s, %s"), user.getSurname(), user.getName()),
                user.getEmail(),
                user.getPhone()
        );
    }
}
