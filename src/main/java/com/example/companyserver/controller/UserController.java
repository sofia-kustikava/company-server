package com.example.companyserver.controller;

import com.example.companyserver.controller.advice.UserExceptionHandler;
import com.example.companyserver.dto.UserDto;
import com.example.companyserver.exceptions.UserNotFoundException;
import com.example.companyserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;
    public final UserExceptionHandler userExceptionHandler;

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

}
