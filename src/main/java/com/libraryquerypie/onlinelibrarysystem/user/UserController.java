package com.libraryquerypie.onlinelibrarysystem.user;

import com.libraryquerypie.onlinelibrarysystem.user.dto.request.LoginRequest;
import com.libraryquerypie.onlinelibrarysystem.user.dto.request.SignupRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Tag(name = "User API", description = "User 관련 API")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "아이디, 비밀번호로 로그인을 수행하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공 후 Jwt 토큰을 반환",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)),
            @ApiResponse(responseCode = "400", description = "회원가입 되지 않은 아이디, 비밀번호 입력",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest loginRequest) {
        String token = userService.login(loginRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "회원가입을 수행하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 후 Jwt 토큰을 반환",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)),
            @ApiResponse(responseCode = "400", description = "이미 가입되어 있는 E-MAIL인 경우",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<String> signup(@RequestBody @Valid SignupRequest signupRequest) {
        String token = userService.signup(signupRequest);
        return ResponseEntity.ok(token);
    }
}
