package com.example.companyserver.controller;

import com.example.companyserver.dto.CompanyDto;
import com.example.companyserver.dto.QuoteDto;
import com.example.companyserver.dto.metric.MetricDto;
import com.example.companyserver.dto.report.ReportDto;
import com.example.companyserver.entity.QuoteEntity;
import com.example.companyserver.service.TrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tracking")
public class TrackingController {

    public final TrackingService trackingService;

    @PostMapping("/add/{symbol}")
    public ResponseEntity<String> addTrackingCompany(@PathVariable("symbol") String symbol) {
        trackingService.addUserCompany(symbol);
        return new ResponseEntity<>("You chose company with that symbol " + symbol, HttpStatus.OK);
    }

    @GetMapping("/track/companies")
    public List<CompanyDto> getTrackingCompanies() {
        return trackingService.getUserCompanies();
    }

    @DeleteMapping("/delete/{symbol}")
    public ResponseEntity<String> deleteTrackingCompanyBySymbol(@PathVariable("symbol") String symbol) {
        trackingService.deleteCompany(symbol);
        return new ResponseEntity<>("You deleted company with that symbol " + symbol, HttpStatus.OK);
    }

    @GetMapping("/quotes/{symbol}")
    public List<QuoteDto>  getTrackingQuote(@PathVariable("symbol") String symbol) {
        return trackingService.getTrackingQuote(symbol);
    }

    @GetMapping("/metric/{symbol}")
    public MetricDto getTrackingMetric(@PathVariable("symbol") String symbol) {
        return trackingService.getTrackingMetric(symbol);
    }

    @GetMapping("/report/{symbol}")
    public  List<ReportDto> getTrackingReport(@PathVariable("symbol") String symbol) {
        return trackingService.getTrackingReport(symbol);
    }
}
