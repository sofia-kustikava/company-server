package com.example.companyserver.service;

import com.example.companyserver.client.FinnhubClient;
import com.example.companyserver.entity.*;
import com.example.companyserver.mapper.MetricMapper;
import com.example.companyserver.mapper.QuoteMapper;
import com.example.companyserver.repo.CompanyRepo;
import com.example.companyserver.repo.MetricRepo;
import com.example.companyserver.repo.QuoteRepo;
import com.example.companyserver.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
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
    private final MailService mailService;
    private final UserRepo userRepo;

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

    @Scheduled(cron = "0 0 0 * * ?")
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

    @Scheduled(cron = "0 0 0 * * ?")
    public void isSubscriptionExpired() {
        userRepo.findAllByEndDate(LocalDate.now()).stream()
                .filter(user -> user.getSubscription().getDateEnd().equals(LocalDate.now()))
                .forEach(user -> {
                    if (user.getStatus().equals(UserStatus.ACTIVE)) user.getSubscription().setSubscriptionStatus(SubscriptionStatus.PAUSED);
                    userRepo.save(user);
                    mailService.sendEmailSubscriptionExpired(user);
                });
    }
    @Scheduled(cron = "0 0 0 * * ?")
    public void isSubscriptionWillExpiredIn3Days() {
        userRepo.findAllByEndDate(LocalDate.now().plusDays(3)).stream()
                .filter(user -> user.getSubscription().getDateEnd().plusDays(3).equals(LocalDate.now()))
                .forEach(mailService::sendEmailSubscriptionWillExpire);
    }
}
