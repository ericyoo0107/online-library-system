package com.libraryquerypie.onlinelibrarysystem.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {
    private String emailId;
    private String password;
}
