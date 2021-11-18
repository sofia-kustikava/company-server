package com.microservice.finnhub.controller;

import com.microservice.finnhub.dto.CompanyDto;
import com.microservice.finnhub.dto.QuoteDto;
import com.microservice.finnhub.dto.metric.MetricDto;
import com.microservice.finnhub.dto.report.ReportDto;
import com.microservice.finnhub.service.FinnhubCompaniesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/finnhub")
public class FinnhubCompaniesController {

    private final FinnhubCompaniesService companiesService;

    @GetMapping("/all")
    public List<CompanyDto> getAllCompanies() {
        return companiesService.getCompanies();
    }


    @GetMapping("/quote/{symbol}")
    public QuoteDto getFinnhubQuote(@PathVariable String symbol) {return companiesService.getQuote(symbol); }

    @GetMapping("/report/{symbol}")
    public List<ReportDto> getFinnhubReport(@PathVariable String symbol) {return companiesService.getReport(symbol);}

    @GetMapping("/metric/{symbol}")
    public MetricDto getFinnhubMetric(@PathVariable String symbol) {return companiesService.getMetric(symbol);}
}
