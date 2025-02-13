package com.libraryquerypie.onlinelibrarysystem.user;

import com.libraryquerypie.onlinelibrarysystem.entity.User;
import com.libraryquerypie.onlinelibrarysystem.exception.custom.BadLoginException;
import com.libraryquerypie.onlinelibrarysystem.exception.custom.BadSignupException;
import com.libraryquerypie.onlinelibrarysystem.jwt.JwtUtils;
import com.libraryquerypie.onlinelibrarysystem.user.dto.request.LoginRequest;
import com.libraryquerypie.onlinelibrarysystem.user.dto.request.SignupRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String login(LoginRequest loginRequest){
        User user = userRepository.findByEmailId(loginRequest.getEmailId())
                .orElseThrow(() -> new BadLoginException("가입되지 않은 E-MAIL"));
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getHashPassword())){
            throw new BadLoginException("잘못된 비밀번호");
        }
        log.info("{} 로그인 성공", user.getEmailId());
        String accessToken = jwtUtils.generateToken(user);
        return accessToken;
    }

    @Transactional
    public String signup(SignupRequest signupRequest){
        if(userRepository.findByEmailId(signupRequest.getEmail()).isPresent()){
            throw new BadSignupException("이미 가입되어 있는 E-MAIL.");
        }
        String encodedPassWord = passwordEncoder.encode(signupRequest.getPassword());
        User user = userRepository.save(SignupRequest.toEntity(signupRequest, encodedPassWord));
        log.info("{} 회원가입 성공", user.getEmailId());
        return jwtUtils.generateToken(user);
    }
}
