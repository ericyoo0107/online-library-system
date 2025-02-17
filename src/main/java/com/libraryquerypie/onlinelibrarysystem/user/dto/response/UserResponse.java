package com.libraryquerypie.onlinelibrarysystem.user.dto.response;

import com.libraryquerypie.onlinelibrarysystem.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponse {
    @Schema(description = "이메일", example = "querypie@naver.com", format = "email")
    private String emailId;
    @Schema(description = "권한", example = "USER", allowableValues = {"ADMIN", "USER"})
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
