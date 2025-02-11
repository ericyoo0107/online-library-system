package com.libraryquerypie.onlinelibrarysystem.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email_id", nullable = false, unique = true)
    private String emailId;

    @Column(name = "hash_password", nullable = false , unique = true)
    private String hashPassword;

}
