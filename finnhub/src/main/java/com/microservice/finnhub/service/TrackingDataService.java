package com.microservice.finnhub.service;

import com.microservice.finnhub.dto.QuoteDto;
import com.microservice.finnhub.dto.metric.MetricDto;
import com.microservice.finnhub.entity.CompanyEntity;
import com.microservice.finnhub.entity.MetricEntity;
import com.microservice.finnhub.entity.QuoteEntity;
import com.microservice.finnhub.exception.CompanyNotFoundException;
import com.microservice.finnhub.mapper.MetricMapper;
import com.microservice.finnhub.mapper.QuoteMapper;
import com.microservice.finnhub.repo.CompanyRepo;
import com.microservice.finnhub.repo.MetricRepo;
import com.microservice.finnhub.repo.QuoteRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackingDataService {

    private final CompanyRepo companyRepo;
    private final MetricRepo metricRepo;
    private final QuoteRepo quoteRepo;
    private final QuoteMapper quoteMapper;
    private final MetricMapper metricMapper;

    public List<QuoteDto> getTrackingQuote(String symbol) {
        CompanyEntity company = companyRepo.findBySymbol(symbol).orElseThrow(() -> new CompanyNotFoundException(symbol));
        List<QuoteEntity> quotes = quoteRepo.findByCompanies(company);
        return quotes.stream().map(quoteMapper::quoteToDto).collect(Collectors.toList());
    }

    public MetricDto getTrackingMetric(String symbol) {
        CompanyEntity company = companyRepo.findBySymbol(symbol).orElseThrow(() -> new CompanyNotFoundException(symbol));
        MetricEntity metric = metricRepo.findByCompanies(company)
                .orElseThrow(() -> new CompanyNotFoundException(String.format("%s", symbol)));
        return metricMapper.metricToDto(metric);
    }
}
