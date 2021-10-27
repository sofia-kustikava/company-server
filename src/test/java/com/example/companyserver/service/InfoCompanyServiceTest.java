package com.example.companyserver.service;

import com.example.companyserver.client.FinnhubClient;
import com.example.companyserver.dto.CompanyDto;
import com.example.companyserver.dto.QuoteDto;
import com.example.companyserver.dto.metric.MetricDto;
import com.example.companyserver.entity.CompanyEntity;
import com.example.companyserver.entity.MetricEntity;
import com.example.companyserver.entity.QuoteEntity;
import com.example.companyserver.mapper.MetricMapper;
import com.example.companyserver.mapper.QuoteMapper;
import com.example.companyserver.repo.CompanyRepo;
import com.example.companyserver.repo.MetricRepo;
import com.example.companyserver.repo.QuoteRepo;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InfoCompanyServiceTest {

    @Mock
    private CompanyRepo companyRepo;

    @Mock
    private QuoteRepo quoteRepo;

    @Mock
    private MetricRepo metricRepo;

    @Mock
    private FinnhubClient finnhubClient;

    @Mock
    public QuoteMapper quoteMapper;

    @Mock
    public MetricMapper metricMapper;

    @InjectMocks
    private InfoCompanyService infoCompanyService;

    private List<CompanyEntity> companiesEntity = new ArrayList<>();
    private List<CompanyDto> companiesDto = new ArrayList<>();
    private List<CompanyEntity> companies = new ArrayList<>();
    private CompanyEntity companyEntity;

    private QuoteEntity quote;
    private QuoteDto quoteDto;
    private List<QuoteEntity> quotes = new ArrayList<>();

    private MetricEntity metric;
    private MetricDto metricDto;
    private List<MetricEntity> metrics = new ArrayList<>();

    @BeforeEach
    private void beforeTest() {
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

        quote = QuoteEntity.builder()
                .companies(companyEntity)
                .build();

        quoteDto = QuoteDto.builder().build();

        metric = MetricEntity.builder()
                .companies(companyEntity)
                .build();

        metricDto = MetricDto.builder().build();
    }

    @Test
    public void saveQuotes() {
        when(companyRepo.findAll()).thenReturn(companies);
        for (int i = 0; i < quotes.size(); i++) {
            when(quoteMapper.dtoToQuote(quoteDto)).thenReturn(quote);
        }
    }

    @Test
    public void saveMetrics() {
    }

    @Test
    public void getQuote() {
        when(quoteRepo.findByCompanies(companyEntity.getSymbol())).thenReturn(Optional.of(quote));
        when(quoteMapper.quoteToDto(quote)).thenReturn(quoteDto);
        QuoteDto quoteFindDto = infoCompanyService.getQuote(companyEntity.getSymbol());
        assertEquals(quoteDto, quoteFindDto);
    }

    @Test
    public void getMetric() {
        when(metricRepo.findByCompanies(companyEntity.getSymbol())).thenReturn(Optional.of(metric));
        when(metricMapper.metricToDto(metric)).thenReturn(metricDto);
        MetricDto metricFindDto = infoCompanyService.getMetric(companyEntity.getSymbol());
        assertEquals(metricDto, metricFindDto);
    }

    @Test
    public void getFinnhubQuote() {
        when(quoteRepo.findByCompanies(companyEntity.getSymbol())).thenReturn(Optional.of(quote));
        finnhubClient.getQuote(companyEntity.getSymbol());
        verify(finnhubClient).getQuote(companyEntity.getSymbol());
    }

    @Test
    public void getFinnhubMetric() {
        when(metricRepo.findByCompanies(companyEntity.getSymbol())).thenReturn(Optional.of(metric));
        finnhubClient.getMetrics(companyEntity.getSymbol());
        verify(finnhubClient).getMetrics(companyEntity.getSymbol()).getMetric();
    }

    @Test
    public void getFinnhubReport() {
        when(quoteRepo.findByCompanies(companyEntity.getSymbol())).thenReturn(Optional.of(quote));
    }
}
