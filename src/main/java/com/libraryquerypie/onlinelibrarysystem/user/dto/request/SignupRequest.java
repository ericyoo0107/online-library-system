package com.libraryquerypie.onlinelibrarysystem.user.dto.request;

import com.libraryquerypie.onlinelibrarysystem.entity.User;
import com.libraryquerypie.onlinelibrarysystem.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequest {

    @NotBlank(message = "이메일 입력은 필수입니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    @Schema(description = "이메일", example = "eee@gmail.com", format = "email")
    private String email;

    @NotBlank(message = "비밀번호 입력은 필수입니다.")
    @Schema(description = "비밀번호", example = "1234")
    private String password;

    @Schema(description = "권한", example = "USER", allowableValues = {"ADMIN", "USER"})
    private Role role;

    @Builder
    public SignupRequest(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static User toEntity(SignupRequest request, String encodedPassword) {
        return User.builder()
                .emailId(request.getEmail())
                .hashPassword(encodedPassword)
                .role(request.getRole())
                .build();
    }
}
