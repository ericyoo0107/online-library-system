package com.libraryquerypie.onlinelibrarysystem.user;

import com.libraryquerypie.onlinelibrarysystem.entity.User;
import com.libraryquerypie.onlinelibrarysystem.enums.Role;
import com.libraryquerypie.onlinelibrarysystem.jwt.JwtUtils;
import com.libraryquerypie.onlinelibrarysystem.user.dto.request.LoginRequest;
import com.libraryquerypie.onlinelibrarysystem.user.dto.request.SignupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 되어 있는 이메일과 비밀번호는 로그인에 성공한다.")
    public void Login_Success() {
        // Given
        createDummyUser();
        LoginRequest loginRequest = LoginRequest.builder()
                .emailId("ericyoo0107@naver.com")
                .password("password")
                .build();

        // When
        String token = userService.login(loginRequest);

        // Then
        assertThat(token).isNotNull();
        assertThat(jwtUtils.extractEmailId(token)).isEqualTo(loginRequest.getEmailId());
    }

    @Test
    @DisplayName("회원가입 되어 있지 않은 이메일은 로그인에 실패한다.")
    public void Login_InvalidEmail() {
        // Given
        LoginRequest loginRequest = new LoginRequest("invalid@email.com", "password");

        // When, Then
        assertThrows(IllegalArgumentException.class, () -> userService.login(loginRequest));
    }

    @Test
    @DisplayName("회원가입 되어 있지 않은 비밀번호는 로그인에 실패한다.")
    public void Login_InvalidPassword() {
        // Given
        LoginRequest loginRequest = new LoginRequest("ericyoo0107@naver.com", "wrong");

        // When, Then
        assertThrows(IllegalArgumentException.class, () -> userService.login(loginRequest));
    }

    @Test
    @DisplayName("일반 회원으로 회원가입 한다.")
    public void Signup_Success() {
        // Given
        SignupRequest request = SignupRequest.builder()
                .email("newEmail@naver.com")
                .password("password")
                .role(Role.USER)
                .build();

        // When
        String token = userService.signup(request);
        User user = userRepository.findByEmailId(request.getEmail()).get();

        // Then
        assertThat(token).isNotNull();
        assertThat(jwtUtils.extractEmailId(token)).isEqualTo(request.getEmail());
        assertThat(passwordEncoder.matches(request.getPassword(), user.getHashPassword())).isTrue();
    }

    @Transactional
    public void createDummyUser() {
        User user = User.builder()
                .emailId("ericyoo0107@naver.com")
                .hashPassword(passwordEncoder.encode("password"))
                .role(Role.USER)
                .build();
        userRepository.save(user);
    }
}
