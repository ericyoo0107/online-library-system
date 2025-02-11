package com.libraryquerypie.onlinelibrarysystem.user;

import com.libraryquerypie.onlinelibrarysystem.entity.User;
import com.libraryquerypie.onlinelibrarysystem.enums.Role;
import com.libraryquerypie.onlinelibrarysystem.jwt.JwtUtils;
import com.libraryquerypie.onlinelibrarysystem.user.dto.request.LoginRequest;
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
