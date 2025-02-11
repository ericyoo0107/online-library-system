package com.libraryquerypie.onlinelibrarysystem.user;

import com.libraryquerypie.onlinelibrarysystem.entity.User;
import com.libraryquerypie.onlinelibrarysystem.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequest {
    private String email;
    private String password;
    private Role role;

    public static User toEntity(SignupRequest request, String encodedPassword) {
        return User.builder()
                .emailId(request.getEmail())
                .hashPassword(encodedPassword)
                .role(request.getRole())
                .build();
    }
}
