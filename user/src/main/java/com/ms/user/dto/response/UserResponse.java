package com.ms.user.dto.response;

import com.ms.user.entity.UserEntity;

import java.util.UUID;

public record UserResponse(
        UUID userId,
        String name,
        String email
) {
    public static UserResponse fromEntity(UserEntity user){
        return new UserResponse(
                user.getUserId(),
                user.getName(),
                user.getEmail()
        );
    }
}
