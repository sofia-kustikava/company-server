package com.microservice.finnhub.controller;

import com.microservice.finnhub.mapper.CompanyMapper;
import com.microservice.finnhub.service.FinnhubCompaniesService;
import com.microservice.finnhub.service.SaveFinnhubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final SaveFinnhubService saveFinnhubService;
    private final FinnhubCompaniesService finnhubCompaniesService;
    private final CompanyMapper companyMapper;

    @PostMapping("/save/companies")
    public ResponseEntity<String> saveAllCompanies() {
        saveFinnhubService.saveCompanies(companyMapper.dtoToCompanies(finnhubCompaniesService.getCompanies()));
        return new ResponseEntity<>("All companies were successfully saved", HttpStatus.OK);
    }
}
