package com.example.companyserver.controller;

import com.example.companyserver.dto.CompaniesDto;
import com.example.companyserver.dto.UsersDto;
import com.example.companyserver.service.FinnhubService;
import com.example.companyserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;
    public final FinnhubService finnhubService;

    @GetMapping("/{id}")
    public UsersDto getUser(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @GetMapping("/companies")
    public List<CompaniesDto> getAllCompanies() {
        return finnhubService.getCompanies();
    }
}
