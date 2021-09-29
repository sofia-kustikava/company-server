package com.example.companyserver.controller;

import com.example.companyserver.dto.AuthDto;
import com.example.companyserver.dto.TokenDto;
import com.example.companyserver.entity.UsersEntity;
import com.example.companyserver.security.JwtProvider;
import com.example.companyserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping("/register")
    public void registerUser(@RequestBody @Valid AuthDto authDto) {
        UsersEntity user = new UsersEntity();
        user.setPassword(authDto.getPassword());
        user.setEmail(authDto.getEmail());
        userService.saveUser(user);
    }

    @PostMapping("/auth")
    public TokenDto auth(@RequestBody AuthDto authDto) {
        UsersEntity userEntity = userService.findByEmailAndPassword(authDto.getEmail(), authDto.getPassword());
        String token = jwtProvider.generateToken(userEntity.getEmail());
        return new TokenDto(token);
    }
}
