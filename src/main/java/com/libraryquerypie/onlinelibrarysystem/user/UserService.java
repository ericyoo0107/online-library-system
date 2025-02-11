package com.libraryquerypie.onlinelibrarysystem.user;

import com.libraryquerypie.onlinelibrarysystem.entity.User;
import com.libraryquerypie.onlinelibrarysystem.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String login(LoginRequest loginRequest){
        User user = userRepository.findByEmailId(loginRequest.getEmailId())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getHashPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        String accessToken = jwtUtils.generateToken(user);
        return accessToken;
    }

    @Transactional
    public String signup(SignupRequest signupRequest){
        if(userRepository.findByEmailId(signupRequest.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 가입되어 있는 E-MAIL 입니다.");
        }
        String encodedPassWord = passwordEncoder.encode(signupRequest.getPassword());
        User user = userRepository.save(SignupRequest.toEntity(signupRequest, encodedPassWord));
        return jwtUtils.generateToken(user);
    }
}
