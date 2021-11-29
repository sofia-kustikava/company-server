package com.microservice.finnhub.service;

import com.microservice.finnhub.client.ApiClient;
import com.microservice.finnhub.dto.CompanyDto;
import com.microservice.finnhub.dto.QuoteDto;
import com.microservice.finnhub.dto.metric.MetricDto;
import com.microservice.finnhub.dto.metric.MetricResponseDto;
import com.microservice.finnhub.dto.report.DataDto;
import com.microservice.finnhub.dto.report.ReportDto;
import com.microservice.finnhub.dto.report.ReportResponseDto;
import com.microservice.finnhub.dto.report.UnitsDto;
import com.microservice.finnhub.entity.CompanyEntity;
import com.microservice.finnhub.entity.MetricEntity;
import com.microservice.finnhub.entity.QuoteEntity;
import com.microservice.finnhub.repo.CompanyRepo;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FinnhubCompaniesServiceTest {
    @Mock
    private CompanyRepo companyRepo;

    @Mock
    private ApiClient apiClient;

    @InjectMocks
    private FinnhubCompaniesService finnhubCompaniesService;

    private List<CompanyEntity> companies = new ArrayList<>();
    private CompanyEntity companyEntity;
    private List<CompanyEntity> companiesEntity = new ArrayList<>();
    private List<CompanyDto> companiesDto = new ArrayList<>();

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
    public void getCompaniesTest() {
        finnhubCompaniesService.getCompanies();
        verify(apiClient).getCompanies();
    }

    @Test
    public void getQuoteTest() {
        when(companyRepo.findBySymbol(companyEntity.getSymbol())).thenReturn(Optional.of(companyEntity));
        when(apiClient.getQuote(companyEntity.getSymbol())).thenReturn(quoteDto);

        QuoteDto actual = finnhubCompaniesService.getQuote(companyEntity.getSymbol());
        assertEquals(quoteDto, actual);
    }

    @Test
    public void getFinnhubMetricTest() {
        when(companyRepo.findBySymbol(companyEntity.getSymbol())).thenReturn(Optional.of(companyEntity));

        when(apiClient.getMetrics(companyEntity.getSymbol())).thenReturn(metricResponseDto);

        MetricDto actual = finnhubCompaniesService.getMetric(companyEntity.getSymbol());
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

        when(apiClient.getReports(companyEntity.getSymbol())).thenReturn(dataDto);

        List<ReportDto> actual = finnhubCompaniesService.getReport(companyEntity.getSymbol());
        assertEquals(10, actual.size());
    }
}
