package com.example.companyserver.service;

import com.example.companyserver.client.FinnhubClient;
import com.example.companyserver.dto.CompanyDto;
import com.example.companyserver.dto.QuoteDto;
import com.example.companyserver.dto.metric.MetricDto;
import com.example.companyserver.dto.metric.MetricResponseDto;
import com.example.companyserver.dto.report.DataDto;
import com.example.companyserver.dto.report.ReportDto;
import com.example.companyserver.dto.report.ReportResponseDto;
import com.example.companyserver.dto.report.UnitsDto;
import com.example.companyserver.entity.CompanyEntity;
import com.example.companyserver.entity.MetricEntity;
import com.example.companyserver.entity.QuoteEntity;
import com.example.companyserver.mapper.MetricMapper;
import com.example.companyserver.mapper.QuoteMapper;
import com.example.companyserver.repo.CompanyRepo;
import com.example.companyserver.repo.MetricRepo;
import com.example.companyserver.repo.QuoteRepo;
import com.example.companyserver.utils.TestingData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
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

        infoCompanyService.saveQuotes();
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

        infoCompanyService.saveMetrics();
        verify(metricRepo).saveAll(Arrays.asList(metric, metric2));
    }

    @Test
    public void getQuoteTest() {
        when(quoteRepo.findByCompanies(companyEntity.getSymbol())).thenReturn(Optional.of(quote));
        when(quoteMapper.quoteToDto(quote)).thenReturn(quoteDto);
        QuoteDto quoteFindDto = infoCompanyService.getQuote(companyEntity.getSymbol());
        assertEquals(quoteDto, quoteFindDto);
    }

    @Test
    public void getMetricTest() {
        when(metricRepo.findByCompanies(companyEntity.getSymbol())).thenReturn(Optional.of(metric));
        when(metricMapper.metricToDto(metric)).thenReturn(metricDto);
        MetricDto metricFindDto = infoCompanyService.getMetric(companyEntity.getSymbol());
        assertEquals(metricDto, metricFindDto);
    }

    @Test
    public void getFinnhubQuoteTest() {
        when(companyRepo.findBySymbol(companyEntity.getSymbol())).thenReturn(Optional.of(companyEntity));
        when(finnhubClient.getQuote(companyEntity.getSymbol())).thenReturn(quoteDto);

        QuoteDto actual = infoCompanyService.getFinnhubQuote(companyEntity.getSymbol());
        assertEquals(quoteDto, actual);
    }

    @Test
    public void getFinnhubMetricTest() {
        when(companyRepo.findBySymbol(companyEntity.getSymbol())).thenReturn(Optional.of(companyEntity));

        when(finnhubClient.getMetrics(companyEntity.getSymbol())).thenReturn(metricResponseDto);

        MetricDto actual = infoCompanyService.getFinnhubMetric(companyEntity.getSymbol());
        assertEquals(metricDto, actual);
    }

    @Test
    public void getFinnhubReportTest() {
        ReportDto reportDto = TestingData.getReport();
        ReportDto reportDto2 = TestingData.getReport();
        ReportDto reportDto3 = TestingData.getReport();

        List<ReportDto> reports = List.of(reportDto, reportDto2, reportDto3);
        List<ReportDto> reports2 = List.of(reportDto, reportDto2, reportDto3);
        List<ReportDto> reports3 = List.of(reportDto, reportDto2, reportDto3);

        UnitsDto unitsDto = TestingData.getUnits(reports, reports2, reports3);
        UnitsDto unitsDto2 = TestingData.getUnits(reports, reports2, reports3);
        UnitsDto unitsDto3 = TestingData.getUnits(reports, reports2, reports3);

        ReportResponseDto responseReportDto = ReportResponseDto.builder()
                .report(unitsDto)
                .build();
        ReportResponseDto responseReportDto2 = ReportResponseDto.builder()
                .report(unitsDto2)
                .build();
        ReportResponseDto responseReportDto3 = ReportResponseDto.builder()
                .report(unitsDto3)
                .build();

        List<ReportResponseDto> reportResponseDtos = List.of(responseReportDto, responseReportDto2, responseReportDto3);

        DataDto dataDto = DataDto.builder()
                .data(reportResponseDtos)
                .build();

        when(companyRepo.findBySymbol(companyEntity.getSymbol())).thenReturn(Optional.of(companyEntity));

        when(finnhubClient.getReports(companyEntity.getSymbol())).thenReturn(dataDto);

        List<ReportDto> actual = infoCompanyService.getFinnhubReport(companyEntity.getSymbol());
        assertEquals(10, actual.size());
    }
}
