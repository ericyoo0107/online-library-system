package com.libraryquerypie.onlinelibrarysystem.jwt;

import com.libraryquerypie.onlinelibrarysystem.exception.custom.UserNotFoundException;
import com.libraryquerypie.onlinelibrarysystem.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmailId(username)
                .map(user -> new User(user.getEmailId(), user.getHashPassword(), new ArrayList<>()))
                .orElseThrow(() -> new UserNotFoundException(username + "을 찾을 수 없습니다."));
    }
}
