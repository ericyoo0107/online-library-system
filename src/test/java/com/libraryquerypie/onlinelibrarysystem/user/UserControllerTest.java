package com.libraryquerypie.onlinelibrarysystem.user;

import com.libraryquerypie.onlinelibrarysystem.entity.User;
import com.libraryquerypie.onlinelibrarysystem.enums.Role;
import com.libraryquerypie.onlinelibrarysystem.user.dto.response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserController userController;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        createDummyUsers();
    }

    @Transactional
    public void createDummyUsers() {
        User user1 = User.builder()
                .emailId("user1@example.com")
                .hashPassword("password1")
                .role(Role.USER)
                .build();
        User user2 = User.builder()
                .emailId("user2@example.com")
                .hashPassword("password2")
                .role(Role.USER)
                .build();
        userRepository.saveAll(List.of(user1, user2));
    }

    @Test
    @DisplayName("유저 ID로 유저를 조회한다.")
    public void testGetUser() {
        Long userId = 1L;
        ResponseEntity<List<UserResponse>> response = userController.getUser(userId);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isGreaterThan(0);
    }
}
