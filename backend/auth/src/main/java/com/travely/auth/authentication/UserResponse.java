package com.travely.auth.authentication;

import com.travely.auth.user.Role;
import com.travely.auth.user.User;

public record UserResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        Role role
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole()
        );
    }
}
