package com.example.companyserver.client;

import com.example.companyserver.dto.CompanyDto;
import com.example.companyserver.dto.QuoteDto;
import com.example.companyserver.dto.metric.MetricDto;
import com.example.companyserver.dto.report.ReportDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "mic", url = "${microservice.path}")
public interface FinnhubClient {
    @GetMapping("/finnhub/all")
    List<CompanyDto> getCompanies();

    @GetMapping("/track/report/{symbol}")
    List<ReportDto> getReport(@PathVariable(name = "symbol") String symbol);

    @GetMapping("/finnhub/quote/{symbol}")
    QuoteDto getQuote(@PathVariable(name = "symbol") String symbol);

    @GetMapping("/finnhub/metric/{symbol}")
    MetricDto getMetric(@PathVariable(name = "symbol") String symbol);

    @GetMapping("/finnhub/database")
    List<CompanyDto> getDatabaseCompanies();

    @PostMapping("/admin/save/companies")
    ResponseEntity<String> saveAllCompanies();

    @PostMapping("/save/quote")
    ResponseEntity<String> saveQuotes();

    @PostMapping("/save/metric")
    ResponseEntity<String> saveMetrics();

    @GetMapping("/track/quotes/{symbol}")
    List<QuoteDto> getTrackingQuote(@PathVariable(name = "symbol") String symbol);

    @GetMapping("/track/metric/{symbol}")
    MetricDto getTrackingMetric(@PathVariable(name = "symbol") String symbol);
}
