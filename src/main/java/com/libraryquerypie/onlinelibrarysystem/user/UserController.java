package com.libraryquerypie.onlinelibrarysystem.user;

import com.libraryquerypie.onlinelibrarysystem.user.dto.request.LoginRequest;
import com.libraryquerypie.onlinelibrarysystem.user.dto.request.SignupRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest loginRequest) {
        String token = userService.login(loginRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid SignupRequest signupRequest) {
        String token = userService.signup(signupRequest);
        return ResponseEntity.ok(token);
    }
}
