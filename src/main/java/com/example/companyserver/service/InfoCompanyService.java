package com.example.companyserver.service;

import com.example.companyserver.client.MicroserviceClient;
import com.example.companyserver.dto.QuoteDto;
import com.example.companyserver.dto.metric.MetricDto;
import com.example.companyserver.dto.report.ReportDto;
import com.example.companyserver.mapper.MetricMapper;
import com.example.companyserver.mapper.QuoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InfoCompanyService {

    public final QuoteMapper quoteMapper;
    public final MetricMapper metricMapper;
    private final MicroserviceClient microserviceClient;

    public QuoteDto getQuote(String symbol){
        return microserviceClient.getQuote(symbol);
    }

    public MetricDto getMetric(String symbol){
        return microserviceClient.getMetric(symbol);
    }

    public List<ReportDto> getReport(String symbol){
        return microserviceClient.getReport(symbol);
    }
}
