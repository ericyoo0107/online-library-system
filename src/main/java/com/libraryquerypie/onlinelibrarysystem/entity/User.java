package com.libraryquerypie.onlinelibrarysystem.entity;

import com.libraryquerypie.onlinelibrarysystem.enums.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String emailId;

    @Column(name = "hash_password", nullable = false)
    private String hashPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Builder
    public User(String emailId, String hashPassword, Role role) {
        this.emailId = emailId;
        this.hashPassword = hashPassword;
        this.role = role;
    }
}
