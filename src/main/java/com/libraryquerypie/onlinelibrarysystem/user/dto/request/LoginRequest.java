package com.libraryquerypie.onlinelibrarysystem.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "이메일 입력은 필수입니다.")
    @Schema(description = "이메일", example = "eee@gmail.com", format = "email")
    private String emailId;

    @NotBlank(message = "비밀번호 입력은 필수입니다.")
    @Schema(description = "비밀번호", example = "1234")
    private String password;

    @Builder
    public LoginRequest(String emailId, String password) {
        this.emailId = emailId;
        this.password = password;
    }
}
