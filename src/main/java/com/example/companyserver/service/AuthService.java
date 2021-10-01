package com.example.companyserver.service;

import com.example.companyserver.dto.AuthDto;
import com.example.companyserver.dto.TokenDto;
import com.example.companyserver.entity.UserEntity;
import com.example.companyserver.repo.UserRepo;
import com.example.companyserver.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public TokenDto auth(AuthDto authDto) {
        UserEntity user = userRepo.findByEmail(authDto.getEmail()).orElseThrow(() -> new RuntimeException("There is no user with this email"));
        if (passwordEncoder.matches(authDto.getPassword(), user.getPassword())) {
            return new TokenDto(jwtProvider.generateToken(user.getEmail()));
        }
        throw  new RuntimeException("invalid password");
    }
}
