package com.microservice.finnhub.controller;

import com.microservice.finnhub.dto.QuoteDto;
import com.microservice.finnhub.dto.metric.MetricDto;
import com.microservice.finnhub.dto.report.ReportDto;
import com.microservice.finnhub.service.FinnhubCompaniesService;
import com.microservice.finnhub.service.TrackingDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/track")
public class TrackingDataController {

    private final TrackingDataService trackingDataService;
    private final FinnhubCompaniesService finnhubCompaniesService;

    @GetMapping("/quotes/{symbol}")
    public List<QuoteDto> getTrackingQuote(@PathVariable("symbol") String symbol) {
        return trackingDataService.getTrackingQuote(symbol);
    }

    @GetMapping("/metric/{symbol}")
    public MetricDto getTrackingMetric(@PathVariable("symbol") String symbol) {
        return trackingDataService.getTrackingMetric(symbol);
    }

    @GetMapping("/report/{symbol}")
    public  List<ReportDto> getTrackingReport(@PathVariable("symbol") String symbol) {
        return finnhubCompaniesService.getReport(symbol);
    }
}
