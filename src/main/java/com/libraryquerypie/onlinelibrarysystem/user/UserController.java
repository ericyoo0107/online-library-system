package com.libraryquerypie.onlinelibrarysystem.user;

import com.libraryquerypie.onlinelibrarysystem.user.dto.request.LoginRequest;
import com.libraryquerypie.onlinelibrarysystem.user.dto.request.SignupRequest;
import com.libraryquerypie.onlinelibrarysystem.user.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
            @ApiResponse(responseCode = "400", description = "이미 가입되어 있는 E-MAIL인 경우 예외 발생",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<String> signup(@RequestBody @Valid SignupRequest signupRequest) {
        String token = userService.signup(signupRequest);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/list")
    @Operation(summary = "사용자 전체 조회", description = "사용자 정보를 전체 조회 하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 조회 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
    })
    public ResponseEntity<List<UserResponse>> getAllUser() {
        log.info("사용자 전체 조회 API 호출!");
        List<UserResponse> response = userService.getAllUser();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list/{userId}")
    @Operation(summary = "사용자 검색", description = "사용자 정보를 검색 하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 조회 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 값",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "사용자 정보 없음",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<List<UserResponse>> getUser(
            @Parameter(description = "사용자 ID", required = false, example = "1")
            @PathVariable(value = "userId", required = false) Long userId) {
        List<UserResponse> response = userService.getUser(userId);
        return ResponseEntity.ok(response);
    }
}
