package com.microservice.finnhub.service;

import com.microservice.finnhub.client.FinnhubClient;
import com.microservice.finnhub.dto.CompanyDto;
import com.microservice.finnhub.dto.QuoteDto;
import com.microservice.finnhub.dto.metric.MetricDto;
import com.microservice.finnhub.dto.metric.MetricResponseDto;
import com.microservice.finnhub.entity.CompanyEntity;
import com.microservice.finnhub.entity.MetricEntity;
import com.microservice.finnhub.entity.QuoteEntity;
import com.microservice.finnhub.mapper.MetricMapper;
import com.microservice.finnhub.mapper.QuoteMapper;
import com.microservice.finnhub.repo.CompanyRepo;
import com.microservice.finnhub.repo.MetricRepo;
import com.microservice.finnhub.repo.QuoteRepo;
import com.microservice.finnhub.utils.TestingData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SaveFinnhubServiceTest {
    @Mock
    private CompanyRepo companyRepo;

    @Mock
    private FinnhubClient finnhubClient;

    @Mock
    private QuoteMapper quoteMapper;

    @Mock
    private QuoteRepo quoteRepo;

    @Mock
    private MetricRepo metricRepo;

    @Mock
    public MetricMapper metricMapper;

    @InjectMocks
    private SaveFinnhubService saveFinnhubService;

    private List<CompanyEntity> companiesEntity = new ArrayList<>();
    private List<CompanyDto> companiesDto = new ArrayList<>();
    private List<CompanyEntity> companies = new ArrayList<>();

    private CompanyEntity companyEntity;
    private CompanyEntity companyEntity2;

    private QuoteEntity quote;
    private QuoteDto quoteDto;

    private MetricEntity metric;
    private MetricResponseDto metricResponseDto;
    private MetricDto metricDto;


    @BeforeEach
    public void beforeTest() {
        companiesEntity.add(TestingData.getCompany("ONFA1"));

        companiesDto.add(TestingData.getCompanyDto("ONFA1"));
        companyEntity = TestingData.getCompany("ONFA1");
        companyEntity2 = TestingData.getCompany("ONFA2");

        quoteDto = TestingData.getQuoteDto(1D);
        quote = TestingData.getQuote(1D);
        quote.setCompanies(companyEntity);

        metric = TestingData.getMetric(1D);
        metric.setCompanies(companyEntity);
        metricDto = TestingData.getMetricDto(1D);
        metricResponseDto = MetricResponseDto.builder().build();
        metricResponseDto.setMetric(metricDto);

        companies.addAll(Arrays.asList(companyEntity, companyEntity2));

    }

    @Test
    public void saveQuotesTest() {

        QuoteDto quoteDto2 = TestingData.getQuoteDto(2D);
        QuoteEntity quote2 = TestingData.getQuote(1D);
        quote2.setCompanies(companyEntity2);

        when(companyRepo.findAll()).thenReturn(companies);
        when(finnhubClient.getQuote(companyEntity.getSymbol())).thenReturn(quoteDto);
        when(finnhubClient.getQuote(companyEntity2.getSymbol())).thenReturn(quoteDto2);

        when(quoteMapper.dtoToQuote(quoteDto)).thenReturn(quote);
        when(quoteMapper.dtoToQuote(quoteDto2)).thenReturn(quote2);

        saveFinnhubService.saveQuotes();
        verify(quoteRepo).saveAll(Arrays.asList(quote, quote2));
    }

    @Test
    public void saveMetricsTest() {
        MetricEntity metric2 = TestingData.getMetric(2D);
        MetricDto metricDto2 = TestingData.getMetricDto(2D);
        MetricResponseDto metricResponseDto2 = MetricResponseDto.builder().build();
        metricResponseDto2.setMetric(metricDto2);

        when(companyRepo.findAll()).thenReturn(companies);
        when(finnhubClient.getMetrics(companies.get(0).getSymbol())).thenReturn(metricResponseDto);
        when(finnhubClient.getMetrics(companies.get(1).getSymbol())).thenReturn(metricResponseDto2);

        when(metricMapper.dtoToMetric(metricDto)).thenReturn(metric);
        when(metricMapper.dtoToMetric(metricDto2)).thenReturn(metric2);

        saveFinnhubService.saveMetrics();
        verify(metricRepo).saveAll(Arrays.asList(metric, metric2));
    }
}
