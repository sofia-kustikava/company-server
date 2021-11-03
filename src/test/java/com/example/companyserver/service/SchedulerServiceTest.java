package com.example.companyserver.service;

import com.example.companyserver.client.FinnhubClient;
import com.example.companyserver.dto.CompanyDto;
import com.example.companyserver.dto.QuoteDto;
import com.example.companyserver.dto.metric.MetricDto;
import com.example.companyserver.dto.metric.MetricResponseDto;
import com.example.companyserver.entity.*;
import com.example.companyserver.mapper.MetricMapper;
import com.example.companyserver.mapper.QuoteMapper;
import com.example.companyserver.repo.CompanyRepo;
import com.example.companyserver.repo.MetricRepo;
import com.example.companyserver.repo.QuoteRepo;
import com.example.companyserver.repo.UserRepo;
import com.example.companyserver.utils.TestingData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SchedulerServiceTest {

    @Mock
    private CompanyRepo companyRepo;

    @Mock
    private FinnhubClient finnhubClient;

    @Mock
    private QuoteMapper quoteMapper;

    @Mock
    private QuoteRepo quoteRepo;

    @Mock
    private MetricMapper metricMapper;

    @Mock
    private MetricRepo metricRepo;

    @Mock
    private MailService mailService;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private SchedulerService schedulerService;

    private List<CompanyEntity> companiesEntity = new ArrayList<>();
    private List<CompanyDto> companiesDto = new ArrayList<>();
    private List<CompanyEntity> companies = new ArrayList<>();

    private CompanyEntity companyEntity;
    private CompanyEntity companyEntity2;

    private QuoteEntity quote;
    private QuoteDto quoteDto;

    private MetricEntity metric;
    private MetricDto metricDto;
    private MetricResponseDto metricResponseDto;

    private MetricEntity metric2;
    private MetricDto metricDto2;
    private MetricResponseDto metricResponseDto2;

    private QuoteDto quoteDto2;
    private QuoteEntity quote2;

    private UserEntity user;
    private UserSubscriptionEntity userSubscription;

    private UserEntity user2;
    private UserSubscriptionEntity userSubscription2;

    private List<UserEntity> users = new ArrayList<>();
    private SubscriptionEntity subscription;

    @BeforeEach
    public void beforeTest() {
        companiesEntity.add(TestingData.getCompany("ONFA1"));

        companiesDto.add(TestingData.getCompanyDto("ONFA1"));
        companyEntity = TestingData.getCompany("ONFA1");
        companyEntity2 = TestingData.getCompany("ONFA2");

        companies.addAll(Arrays.asList(companyEntity, companyEntity2));

        subscription = TestingData.getSubscription();

        userSubscription = TestingData.getUserSubscription(LocalDate.now());
        userSubscription.setSubscription(subscription);
        user = TestingData.getUser(1L, UserStatus.ACTIVE);
        user.setSubscription(userSubscription);

        userSubscription2 = TestingData.getUserSubscription(LocalDate.now().minusDays(3));
        userSubscription2.setSubscription(subscription);
        user2 = TestingData.getUser(2L, UserStatus.ACTIVE);
        user2.setSubscription(userSubscription2);

        userSubscription2.setUser(user2);
        userSubscription.setUser(user);

        users.addAll(Arrays.asList(user, user2));
    }

    @Test
    public void saveQuotesByScheduleTest() {
        quoteDto = TestingData.getQuoteDto(1D);
        quote = TestingData.getQuote(1D);
        quote.setCompanies(companyEntity);
        quoteDto2 = TestingData.getQuoteDto(2D);
        quote2 = TestingData.getQuote(2D);
        quote2.setCompanies(companyEntity2);

        when(companyRepo.findAll()).thenReturn(companies);
        when(finnhubClient.getQuote(companyEntity.getSymbol())).thenReturn(quoteDto);
        when(finnhubClient.getQuote(companyEntity2.getSymbol())).thenReturn(quoteDto2);
        when(quoteMapper.dtoToQuote(quoteDto)).thenReturn(quote);
        when(quoteMapper.dtoToQuote(quoteDto2)).thenReturn(quote2);
        schedulerService.saveQuotes();
        verify(quoteRepo).saveAll(Arrays.asList(quote, quote2));
    }

    @Test
    public void saveMetricsByScheduleTest() {
        metricDto = TestingData.getMetricDto(1D);
        metric = TestingData.getMetric(1D);
        metric.setCompanies(companyEntity);
        metricResponseDto = MetricResponseDto.builder().build();
        metricResponseDto.setMetric(metricDto);
        metricDto2 = TestingData.getMetricDto(2D);
        metric2 = TestingData.getMetric(2D);
        metric2.setCompanies(companyEntity2);
        metricResponseDto2 = MetricResponseDto.builder().build();
        metricResponseDto2.setMetric(metricDto2);

        when(companyRepo.findAll()).thenReturn(companies);
        when(finnhubClient.getMetrics(companies.get(0).getSymbol())).thenReturn(metricResponseDto);
        when(finnhubClient.getMetrics(companies.get(1).getSymbol())).thenReturn(metricResponseDto2);
        when(metricMapper.dtoToMetric(metricDto)).thenReturn(metric);
        when(metricMapper.dtoToMetric(metricDto2)).thenReturn(metric2);
        schedulerService.saveMetrics();
        verify(metricRepo).saveAll(Arrays.asList(metric, metric2));
    }

    @Test
    public void isSubscriptionExpiredTest() {
        when(userRepo.findAllByEndDate(LocalDate.now())).thenReturn(users);
        schedulerService.isSubscriptionExpired();
        verify(userRepo).save(user);
        verify(mailService).sendEmailSubscriptionExpired(user);

    }

    @Test
    public void isSubscriptionWillExpiredIn3DaysTest() {
        when(userRepo.findAllByEndDate(LocalDate.now().plusDays(3))).thenReturn(users);
        schedulerService.isSubscriptionWillExpiredIn3Days();
        verify(mailService).sendEmailSubscriptionWillExpire(user2);
    }
}