package com.example.companyserver.service;

import com.example.companyserver.client.FinnhubClient;
import com.example.companyserver.entity.CompanyEntity;
import com.example.companyserver.repo.CompanyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerService {
    private final CompanyRepo companyRepo;
    private final FinnhubClient finnhubClient;

    @Scheduled(cron = "0 */15 * ? * *")
    public void getScheduleQuote() {
        List<CompanyEntity> companySymbol = companyRepo.findAllBySymbol();
        companySymbol.forEach(companyEntity -> finnhubClient.getQuote(companyEntity.getSymbol())
        );
    }

    @Scheduled(cron = "0 */15 * ? * *")
    public void getScheduleMetric() {
        List<CompanyEntity> companySymbol = companyRepo.findAllBySymbol();
        companySymbol.forEach(companyEntity -> finnhubClient.getMetrics(companyEntity.getSymbol())
        );
    }

    @Scheduled(cron = "0 */15 * ? * *")
    public void getScheduleReport() {
        List<CompanyEntity> companySymbol = companyRepo.findAllBySymbol();
        companySymbol.forEach(companyEntity -> finnhubClient.getReports(companyEntity.getSymbol()));
    }
}
