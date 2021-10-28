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
        companiesEntity.add(CompanyEntity.builder()
                .currency("USD")
                .description("ONE 4 ART LTD")
                .displaySymbol("ONFA")
                .figi("BBG002Q0F4D7")
                .mic("OOTC")
                .symbol("ONFA")
                .type("Common Stock")
                .build()
        );

        companiesDto.add(CompanyDto.builder()
                .currency("USD")
                .description("ONE 4 ART LTD")
                .displaySymbol("ONFA")
                .figi("BBG002Q0F4D7")
                .mic("OOTC")
                .symbol("ONFA")
                .type("Common Stock")
                .build()
        );
        companyEntity = CompanyEntity.builder()
                .currency("USD")
                .description("ONE 4 ART LTD")
                .displaySymbol("ONFA")
                .figi("BBG002Q0F4D7")
                .mic("OOTC")
                .symbol("ONFA")
                .type("Common Stock")
                .build();
        companyEntity2 = CompanyEntity.builder()
                .currency("USD")
                .description("ONE 4 ART LTD")
                .displaySymbol("ONFA")
                .figi("BBG002Q0F4D7")
                .mic("OOTC")
                .symbol("ONF2A")
                .type("Common Stock")
                .build();

        quoteDto = QuoteDto.builder()
                .currentPrice(1D)
                .change(1D)
                .percentChange(1D)
                .highPrice(1D)
                .lowPrice(1D)
                .openPrice(1D)
                .closePrice(1D)
                .build();
        quote = QuoteEntity.builder()
                .currentPrice(1D)
                .change(1D)
                .percentChange(1D)
                .highPrice(1D)
                .lowPrice(1D)
                .openPrice(1D)
                .closePrice(1D)
                .companies(companyEntity)
                .build();

        quoteDto2 = QuoteDto.builder()
                .currentPrice(1D)
                .change(2D)
                .percentChange(1D)
                .highPrice(2D)
                .lowPrice(1D)
                .openPrice(21D)
                .closePrice(1D)
                .build();
        quote2 = QuoteEntity.builder()
                .currentPrice(1D)
                .change(2D)
                .percentChange(1D)
                .highPrice(2D)
                .lowPrice(1D)
                .openPrice(21D)
                .closePrice(1D)
                .companies(companyEntity2)
                .build();

        metric = MetricEntity.builder()
                .weekHigh(1D)
                .weekLow(1D)
                .companies(companyEntity)
                .build();
        metricDto = MetricDto.builder()
                .weekHigh(1D)
                .weekLow(2D)
                .build();
        metricResponseDto = MetricResponseDto.builder()
                .metric(metricDto)
                .build();

        metric2 = MetricEntity.builder()
                .weekHigh(1D)
                .weekLow(1D)
                .companies(companyEntity2)
                .build();
        metricDto2 = MetricDto.builder()
                .weekHigh(1D)
                .weekLow(1D)
                .build();
        metricResponseDto2 = MetricResponseDto.builder()
                .metric(metricDto2)
                .build();

        companies.addAll(Arrays.asList(companyEntity, companyEntity2));

        subscription = SubscriptionEntity.builder()
                .name("Golden")
                .description("Description sample")
                .price(90D)
                .build();

        userSubscription = UserSubscriptionEntity.builder()
                .subscription(subscription)
                .dateEnd(LocalDate.now())
                .dateStart(LocalDate.now())
                .subscriptionStatus(SubscriptionStatus.ACTIVE)
                .build();
        user = UserEntity.builder()
                .firstName("User")
                .lastName("Userovich")
                .email("user@mail.com")
                .status(UserStatus.ACTIVE)
                .subscription(userSubscription)
                .build();

        userSubscription2 = UserSubscriptionEntity.builder()
                .subscription(subscription)
                .dateEnd(LocalDate.now().minusDays(3))
                .dateStart(LocalDate.now())
                .subscriptionStatus(SubscriptionStatus.ACTIVE)
                .build();
        user2 = UserEntity.builder()
                .firstName("User2")
                .lastName("Userovich2")
                .email("user2@mail.com")
                .status(UserStatus.ACTIVE)
                .subscription(userSubscription2)
                .build();

        userSubscription2.setUser(user2);
        userSubscription.setUser(user);

        users.addAll(Arrays.asList(user, user2));
    }

    @Test
    void saveQuotesByScheduleTest() {
        when(companyRepo.findAll()).thenReturn(companies);
        when(finnhubClient.getQuote(companyEntity.getSymbol())).thenReturn(quoteDto);
        when(finnhubClient.getQuote(companyEntity2.getSymbol())).thenReturn(quoteDto2);

        when(quoteMapper.dtoToQuote(quoteDto)).thenReturn(quote);
        when(quoteMapper.dtoToQuote(quoteDto2)).thenReturn(quote2);

        schedulerService.saveQuotes();
        verify(quoteRepo).saveAll(Arrays.asList(quote, quote2));
    }

    @Test
    void saveMetricsByScheduleTest() {
        when(companyRepo.findAll()).thenReturn(companies);
        when(finnhubClient.getMetrics(companies.get(0).getSymbol())).thenReturn(metricResponseDto);
        when(finnhubClient.getMetrics(companies.get(1).getSymbol())).thenReturn(metricResponseDto2);

        when(metricMapper.dtoToMetric(metricDto)).thenReturn(metric);
        when(metricMapper.dtoToMetric(metricDto2)).thenReturn(metric2);

        schedulerService.saveMetrics();
        verify(metricRepo).saveAll(Arrays.asList(metric, metric2));
    }

    @Test
    void isSubscriptionExpiredTest() {
        when(userRepo.findAllByEndDate(LocalDate.now())).thenReturn(users);

        schedulerService.isSubscriptionExpired();
        verify(userRepo).save(user);
        verify(mailService).sendEmailSubscriptionExpired(user);

    }

    @Test
    void isSubscriptionWillExpiredIn3DaysTest() {
        when(userRepo.findAllByEndDate(LocalDate.now().plusDays(3))).thenReturn(users);

        schedulerService.isSubscriptionWillExpiredIn3Days();
        verify(mailService).sendEmailSubscriptionWillExpire(user2);
    }
}