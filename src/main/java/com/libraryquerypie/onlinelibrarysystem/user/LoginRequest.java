package com.libraryquerypie.onlinelibrarysystem.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "이메일 입력은 필수입니다.")
    private String emailId;
    @NotBlank(message = "비밀번호 입력은 필수입니다.")
    private String password;
}
