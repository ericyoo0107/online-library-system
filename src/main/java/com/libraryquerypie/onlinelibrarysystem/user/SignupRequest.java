package com.libraryquerypie.onlinelibrarysystem.user;

import com.libraryquerypie.onlinelibrarysystem.entity.User;
import com.libraryquerypie.onlinelibrarysystem.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequest {
    @NotBlank(message = "이메일 입력은 필수입니다.")
    private String email;
    @NotBlank(message = "비밀번호 입력은 필수입니다.")
    private String password;
    @NotBlank(message = "ADMIN 또는 USER 중 하나를 선택해주세요.")
    private Role role;

    public static User toEntity(SignupRequest request, String encodedPassword) {
        return User.builder()
                .emailId(request.getEmail())
                .hashPassword(encodedPassword)
                .role(request.getRole())
                .build();
    }
}
