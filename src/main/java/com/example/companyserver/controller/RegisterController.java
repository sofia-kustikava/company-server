package com.example.companyserver.controller;

import com.example.companyserver.dto.RegisterDto;
import com.example.companyserver.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping("/register")
    public HttpStatus registerUser(@RequestBody @Valid RegisterDto registerDto) {
        registerService.registerUser(registerDto);
        return HttpStatus.OK;
    }

}
