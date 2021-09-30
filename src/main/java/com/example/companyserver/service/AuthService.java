package com.example.companyserver.service;

import com.example.companyserver.dto.AuthDto;
import com.example.companyserver.dto.TokenDto;
import com.example.companyserver.entity.UsersEntity;
import com.example.companyserver.repo.UsersRepo;
import com.example.companyserver.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersRepo usersRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public TokenDto auth(AuthDto authDto) {
        UsersEntity user = usersRepo.findByEmail(authDto.getEmail()).orElseThrow(() -> new RuntimeException("There is no user with this email"));
        if (passwordEncoder.matches(authDto.getPassword(), user.getPassword())) {
            return new TokenDto(jwtProvider.generateToken(user.getEmail()));
        }
        throw  new RuntimeException("invalid password");
    }
}
