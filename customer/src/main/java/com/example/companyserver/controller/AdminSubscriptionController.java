package com.example.companyserver.controller;

import com.example.companyserver.client.FinnhubClient;
import com.example.companyserver.dto.QuoteDto;
import com.example.companyserver.dto.metric.MetricDto;
import com.example.companyserver.dto.report.ReportDto;
import com.example.companyserver.service.InfoCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminSubscriptionController {

    public final InfoCompanyService infoCompanyService;
    public final FinnhubClient finnhubClient;

    @PostMapping("/save/quote")
    public ResponseEntity<String> saveQuotes() {
        finnhubClient.saveQuotes();
        return new ResponseEntity<>("All quotes were successfully saved", HttpStatus.OK);
    }

    @PostMapping("/save/metric")
    public ResponseEntity<String> saveMetrics() {
        finnhubClient.saveMetrics();
        return new ResponseEntity<>("All metrics were successfully saved", HttpStatus.OK);
    }

    @GetMapping("/quote/{symbol}")
    public QuoteDto getFinnhubQuote(@PathVariable String symbol) {return infoCompanyService.getQuote(symbol); }

    @GetMapping("/report/{symbol}")
    public List<ReportDto> getFinnhubReport(@PathVariable String symbol) {return infoCompanyService.getReport(symbol);}

    @GetMapping("/metric/{symbol}")
    public MetricDto getFinnhubMetric(@PathVariable String symbol) {return infoCompanyService.getMetric(symbol);}
}
