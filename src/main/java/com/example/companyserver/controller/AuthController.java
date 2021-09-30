package com.example.companyserver.controller;

import com.example.companyserver.dto.AuthDto;
import com.example.companyserver.dto.TokenDto;
import com.example.companyserver.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth")
    public TokenDto auth(@RequestBody AuthDto authDto) {
        return authService.auth(authDto);
    }
}
