package com.microservice.finnhub.service;

import com.microservice.finnhub.dto.CompanyDto;
import com.microservice.finnhub.dto.QuoteDto;
import com.microservice.finnhub.dto.metric.MetricDto;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrackingDataServiceTest {

    @Mock
    private CompanyRepo companyRepo;

    @Mock
    private MetricRepo metricRepo;

    @Mock
    private QuoteRepo quoteRepo;

    @Mock
    private QuoteMapper quoteMapper;

    @Mock
    private MetricMapper metricMapper;

    @InjectMocks
    private TrackingDataService trackingDataService;

    CompanyEntity companyEntity;
    CompanyDto companyDto;
    List<CompanyEntity> companies = new ArrayList<>();
    List<CompanyDto> companyDtos = new ArrayList<>();

    @BeforeEach
    public void beforeTest() {
        companyEntity = TestingData.getCompany("ONFA");
        companyDto = TestingData.getCompanyDto("ONFA");
        companies.add(companyEntity);
        companyDtos.add(companyDto);
    }

    @Test
    public void getTrackingQuoteTest() {
        List<QuoteEntity> quotes = new ArrayList<>();
        List<QuoteDto> quoteDtos = new ArrayList<>();
        QuoteDto quoteDto = TestingData.getQuoteDto(1D);
        QuoteEntity quote = TestingData.getQuote(1D);
        quote.setCompanies(companyEntity);
        quotes.add(quote);
        quoteDtos.add(quoteDto);
        when(companyRepo.findBySymbol(companyEntity.getSymbol())).thenReturn(Optional.of(companyEntity));
        when(quoteRepo.findByCompanies(companies.get(0))).thenReturn(quotes);
        when(quoteMapper.quoteToDto(quote)).thenReturn(quoteDto);
        List<QuoteDto> actual = trackingDataService.getTrackingQuote(companyEntity.getSymbol());
        assertEquals(quoteDtos, actual);
    }
    @Test
    public void getTrackingMetricTest() {
        MetricDto metricDto = TestingData.getMetricDto(1D);
        MetricEntity metric = TestingData.getMetric(1D);
        metric.setCompanies(companyEntity);
        when(companyRepo.findBySymbol(companyEntity.getSymbol())).thenReturn(Optional.of(companyEntity));
        when(metricRepo.findByCompanies(companies.get(0))).thenReturn(Optional.of(metric));
        when(metricMapper.metricToDto(metric)).thenReturn(metricDto);
        MetricDto actual = trackingDataService.getTrackingMetric("ONFA");
        assertEquals(metricDto, actual);
    }
}
