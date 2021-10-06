package com.example.companyserver.controller;

import com.example.companyserver.dto.CompanyDto;
import com.example.companyserver.mapper.CompanyMapper;
import com.example.companyserver.service.FinnhubService;
import com.example.companyserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController("/admin")
public class AdminController {

    private final CompanyMapper companyMapper;
    public final FinnhubService finnhubService;
    public final UserService userService;

    @GetMapping("/companies")
    public List<CompanyDto> getAllCompanies() {
        return companyMapper.companiesToDto(finnhubService.getCompanies());
    }

    @PostMapping("/save")
    public HttpStatus saveAllCompanies() {
        finnhubService.saveCompanies(finnhubService.getCompanies());
        return HttpStatus.OK;
    }

    @DeleteMapping("/delete/company/{symbol}")
    public HttpStatus deleteCompanyBySymbol(@PathVariable String symbol) {
        finnhubService.deleteCompany(symbol);
        return HttpStatus.OK;
    }

    @DeleteMapping("/delete/company/all")
    public HttpStatus deleteAllCompanies() {
        finnhubService.deleteAllCompanies();
        return HttpStatus.OK;
    }

    @DeleteMapping("/delete/user/{id}")
    public HttpStatus delete(@PathVariable Long id){
        userService.delete(id);
        return HttpStatus.OK;
    }

}
