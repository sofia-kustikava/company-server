package com.microservice.finnhub.service;

import com.microservice.finnhub.client.ApiClient;
import com.microservice.finnhub.entity.CompanyEntity;
import com.microservice.finnhub.entity.MetricEntity;
import com.microservice.finnhub.entity.QuoteEntity;
import com.microservice.finnhub.mapper.MetricMapper;
import com.microservice.finnhub.mapper.QuoteMapper;
import com.microservice.finnhub.repo.CompanyRepo;
import com.microservice.finnhub.repo.MetricRepo;
import com.microservice.finnhub.repo.QuoteRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SaveFinnhubService {

    private final ApiClient apiClient;
    private final CompanyRepo companyRepo;
    private final QuoteMapper quoteMapper;
    private final QuoteRepo quoteRepo;
    private final MetricRepo metricRepo;
    public final MetricMapper metricMapper;

    public void saveCompanies(List<CompanyEntity> companies) {
        companies.stream().limit(100).forEach(companyEntity -> {
            Optional<CompanyEntity> bySymbol = companyRepo.findBySymbol(companyEntity.getSymbol());
            bySymbol.ifPresent(entity -> companyEntity.setId(entity.getId()));
            companyRepo.save(companyEntity);
        });
    }

    public void saveQuotes() {
        List<CompanyEntity> companies = companyRepo.findAll();
        List<QuoteEntity> collect = companies.stream()
                .limit(10).map(company -> {
                    QuoteEntity quoteEntity = quoteMapper.dtoToQuote(apiClient.getQuote(company.getSymbol()));
                    quoteEntity.setCompanies(company);
                    return quoteEntity;
                })
                .collect(Collectors.toList());
        quoteRepo.saveAll(collect);
    }

    public void saveMetrics() {
        List<CompanyEntity> companies = companyRepo.findAll();
        List<MetricEntity> collect = companies.stream()
                .limit(10).map(company -> {
                    MetricEntity metricEntities = metricMapper.dtoToMetric(apiClient.getMetrics(company.getSymbol()).getMetric());
                    metricEntities.setCompanies(company);
                    return metricEntities;
                })
                .collect(Collectors.toList());
        metricRepo.saveAll(collect);
    }
}
