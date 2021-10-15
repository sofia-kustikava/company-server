package com.example.companyserver.controller;

import com.example.companyserver.dto.CompanyDto;
import com.example.companyserver.mapper.CompanyMapper;
import com.example.companyserver.service.CompanyService;
import com.example.companyserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final CompanyMapper companyMapper;
    private final CompanyService companyService;
    private final UserService userService;

    @GetMapping("/companies")
    public List<CompanyDto> getAllCompanies() {
        return companyMapper.companiesToDto(companyService.getCompanies());
    }

    @PostMapping("/save")
    public HttpStatus saveAllCompanies() {
        companyService.saveCompanies(companyService.getCompanies());
        return HttpStatus.OK;
    }

    @DeleteMapping("/delete/company/{symbol}")
    public HttpStatus deleteCompanyBySymbol(@PathVariable String symbol) {
        companyService.deleteCompany(symbol);
        return HttpStatus.OK;
    }

    @DeleteMapping("/delete/company/all")
    public HttpStatus deleteAllCompanies() {
        companyService.deleteAllCompanies();
        return HttpStatus.OK;
    }

    @DeleteMapping("/delete/user/{id}")
    public HttpStatus delete(@PathVariable Long id){
        userService.delete(id);
        return HttpStatus.OK;
    }


}
