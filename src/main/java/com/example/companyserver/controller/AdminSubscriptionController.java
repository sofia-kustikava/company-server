package com.example.companyserver.controller;

import com.example.companyserver.dto.QuoteDto;
import com.example.companyserver.dto.metric.MetricDto;
import com.example.companyserver.dto.report.ReportDto;
import com.example.companyserver.service.InfoCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminSubscriptionController {

    public final InfoCompanyService infoCompanyService;

    @PostMapping("/save/quotes")
    public HttpStatus saveAllQuotes() {
        infoCompanyService.saveQuotes();
        return HttpStatus.OK;
    }

    @PostMapping("/save/metrics")
    public HttpStatus saveAllMetrics() {
        infoCompanyService.saveMetrics();
        return HttpStatus.OK;
    }

    @GetMapping("/quote/{symbol}")
    public QuoteDto getFinnhubQuote(@PathVariable String symbol) {return infoCompanyService.getFinnhubQuote(symbol); }

    @GetMapping("/report/{symbol}")
    public List<ReportDto> getFinnhubReport(@PathVariable String symbol) {return infoCompanyService.getFinnhubReport(symbol);}

    @GetMapping("/metric/{symbol}")
    public MetricDto getFinnhubMetric(@PathVariable String symbol) {return infoCompanyService.getFinnhubMetric(symbol);}
}
