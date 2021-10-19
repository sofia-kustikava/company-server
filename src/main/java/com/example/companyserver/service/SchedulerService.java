package com.example.companyserver.service;

import com.example.companyserver.client.FinnhubClient;
import com.example.companyserver.entity.CompanyEntity;
import com.example.companyserver.entity.MetricEntity;
import com.example.companyserver.entity.QuoteEntity;
import com.example.companyserver.mapper.MetricMapper;
import com.example.companyserver.mapper.QuoteMapper;
import com.example.companyserver.repo.CompanyRepo;
import com.example.companyserver.repo.MetricRepo;
import com.example.companyserver.repo.QuoteRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchedulerService {
    private final CompanyRepo companyRepo;
    private final FinnhubClient finnhubClient;
    private final QuoteMapper quoteMapper;
    private final QuoteRepo quoteRepo;
    private final MetricMapper metricMapper;
    private final MetricRepo metricRepo;

    @Scheduled(cron = "0 */15 * ? * *")
    public void saveQuotes() {
        List<QuoteEntity> collect = companyRepo.findAll().stream()
                .limit(10).map(company -> {
                    QuoteEntity quoteEntity = quoteMapper.dtoToQuote(finnhubClient.getQuote(company.getSymbol()));
                    quoteEntity.setCompanies(company);
                    return quoteEntity;
                })
                .collect(Collectors.toList());
        quoteRepo.saveAll(collect);
    }

    @Scheduled(cron = "0 */15 * ? * *")
    public void saveMetrics() {
        List<MetricEntity> collect = companyRepo.findAll().stream()
                .map(company -> {
                    MetricEntity metricEntities = metricMapper.dtoToMetric(finnhubClient.getMetrics(company.getSymbol()).getMetric());
                    metricEntities.setCompanies(company);
                    return metricEntities;
                })
                .collect(Collectors.toList());
        metricRepo.saveAll(collect);
    }
}
