package com.example.companyserver.controller;

import com.example.companyserver.entity.UsersEntity;
import com.example.companyserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(path = "/all")
    public @ResponseBody
    List<UsersEntity> getAll() {
        return this.userService.getUsers();
    }
}
