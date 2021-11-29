package com.microservice.finnhub.service;

import com.microservice.finnhub.client.ApiClient;
import com.microservice.finnhub.entity.MetricEntity;
import com.microservice.finnhub.entity.QuoteEntity;
import com.microservice.finnhub.mapper.MetricMapper;
import com.microservice.finnhub.mapper.QuoteMapper;
import com.microservice.finnhub.repo.CompanyRepo;
import com.microservice.finnhub.repo.MetricRepo;
import com.microservice.finnhub.repo.QuoteRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final CompanyRepo companyRepo;
    private final ApiClient apiClient;
    private final QuoteMapper quoteMapper;
    private final QuoteRepo quoteRepo;
    private final MetricMapper metricMapper;
    private final MetricRepo metricRepo;

    @Scheduled(cron = "0 */5 * ? * *")
    public void saveQuotes() {
        List<QuoteEntity> collect = companyRepo.findAll().stream()
                .limit(10)
                .map(company -> {
                    QuoteEntity quoteEntity = quoteMapper.dtoToQuote(apiClient.getQuote(company.getSymbol()));
                    quoteEntity.setCompanies(company);
                    return quoteEntity;
                })
                .collect(Collectors.toList());
        quoteRepo.saveAll(collect);
    }

    @Scheduled(cron = "0 0 12 ? JAN *")
    public void saveMetrics() {
        List<MetricEntity> collect = companyRepo.findAll().stream()
                .limit(10)
                .map(company -> {
                    MetricEntity metricEntities = metricMapper.dtoToMetric(apiClient.getMetrics(company.getSymbol()).getMetric());
                    metricEntities.setCompanies(company);
                    return metricEntities;
                })
                .collect(Collectors.toList());
        metricRepo.saveAll(collect);
    }
}
