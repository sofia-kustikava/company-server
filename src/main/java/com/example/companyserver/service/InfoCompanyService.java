package com.example.companyserver.service;

import com.example.companyserver.client.FinnhubClient;
import com.example.companyserver.dto.QuoteDto;
import com.example.companyserver.dto.metric.MetricDto;
import com.example.companyserver.dto.report.ReportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InfoCompanyService {

    private final FinnhubClient finnhubClient;

    public QuoteDto getQuote(String symbol){
        return finnhubClient.getQuote(symbol);
    }

    public MetricDto getMetric(String symbol){
        return finnhubClient.getMetric(symbol);
    }

    public List<ReportDto> getReport(String symbol){
        return finnhubClient.getReport(symbol);
    }
}
