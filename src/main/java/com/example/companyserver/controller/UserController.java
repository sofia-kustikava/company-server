package com.example.companyserver.controller;

import com.example.companyserver.dto.QuoteDto;
import com.example.companyserver.dto.UserDto;
import com.example.companyserver.dto.metric.MetricDto;
import com.example.companyserver.dto.report.ReportDto;
import com.example.companyserver.service.InfoCompanyService;
import com.example.companyserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;
    private final InfoCompanyService infoCompanyService;

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @GetMapping("/quote/{symbol}")
    public QuoteDto getQuote(@PathVariable String symbol) {
        return infoCompanyService.getQuote(symbol);
    }

    @GetMapping("/report/{symbol}")
    public List<ReportDto> getReport(@PathVariable String symbol) {
        return infoCompanyService.getReport(symbol);
    }

    @GetMapping("/metric/{symbol}")
    public MetricDto getMetric(@PathVariable String symbol) {
        return infoCompanyService.getMetric(symbol);
    }

}
