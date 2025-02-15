package com.libraryquerypie.onlinelibrarysystem.user.dto.response;

import com.libraryquerypie.onlinelibrarysystem.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponse {

    private String emailId;
    private String role;

    @Builder
    public UserResponse(String emailId, String role) {
        this.emailId = emailId;
        this.role = role;
    }

    public static UserResponse fromEntity(User user) {
        return UserResponse.builder()
                .emailId(user.getEmailId())
                .role(user.getRole().name())
                .build();
    }
}
