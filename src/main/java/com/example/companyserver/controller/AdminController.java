package com.example.companyserver.controller;


import com.example.companyserver.dto.CompanyDto;
import com.example.companyserver.mapper.CompanyMapper;
import com.example.companyserver.service.FinnhubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AdminController {

    private final CompanyMapper companyMapper;
    public final FinnhubService finnhubService;

    @GetMapping("/companies")
    public List<CompanyDto> getAllCompanies() {
        return companyMapper.companiesToDto(finnhubService.getCompanies());
    }

    @PostMapping("/save")
    public void saveAllCompanies() {
        finnhubService.saveCompanies(finnhubService.getCompanies());
    }
}
