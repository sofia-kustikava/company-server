package com.example.companyserver.controller;

import com.example.companyserver.dto.UsersDto;
import com.example.companyserver.entity.UsersEntity;
import com.example.companyserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;

    @GetMapping("/{email}")
    public UsersEntity getUser(@PathVariable("email") String email) {
        return userService.findByEmail(email);

    }
}
