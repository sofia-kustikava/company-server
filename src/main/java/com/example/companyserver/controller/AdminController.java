package com.example.companyserver.controller;

import com.example.companyserver.dto.CompanyDto;
import com.example.companyserver.dto.metric.MetricDto;
import com.example.companyserver.dto.QuoteDto;
import com.example.companyserver.dto.report.ReportDto;
import com.example.companyserver.mapper.CompanyMapper;
import com.example.companyserver.mapper.MetricMapper;
import com.example.companyserver.mapper.QuoteMapper;
import com.example.companyserver.mapper.ReportMapper;
import com.example.companyserver.service.CompanyService;
import com.example.companyserver.service.QuoteService;
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
    private final QuoteMapper quoteMapper;
    private final ReportMapper reportMapper;
    private final MetricMapper metricMapper;
    private final CompanyService companyService;
    private final UserService userService;
    private final QuoteService quoteService;

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

    @GetMapping("/quote/{symbol}")
    public QuoteDto getQuote(@PathVariable String symbol) {
        return quoteMapper.quoteToDto(quoteService.getQuote(symbol));
    }

    @GetMapping("/report/{symbol}")
    public List<ReportDto> getReport(@PathVariable String symbol) {
        return quoteService.getReport(symbol);
    }

    @GetMapping("/metric/{symbol}")
    public MetricDto getMetric(@PathVariable String symbol) {
        return metricMapper.metricToDto(quoteService.getMetric(symbol));
    }

}
