package com.libraryquerypie.onlinelibrarysystem.user;

import com.libraryquerypie.onlinelibrarysystem.entity.User;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> searchUser(Long userId);
}
