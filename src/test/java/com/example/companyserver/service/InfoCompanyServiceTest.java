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
    private QuoteDto quoteDto2;
    private QuoteEntity quote2;

    private MetricEntity metric;
    private MetricResponseDto metricResponseDto;
    private MetricDto metricDto;

    private MetricEntity metric2;
    private MetricDto metricDto2;
    private MetricResponseDto metricResponseDto2;

    private ReportDto reportDto;
    private ReportDto reportDto2;
    private ReportDto reportDto3;
    private DataDto dataDto;
    private UnitsDto unitsDto;
    private UnitsDto unitsDto2;
    private UnitsDto unitsDto3;
    private ReportResponseDto responseReportDto;
    private ReportResponseDto responseReportDto2;
    private ReportResponseDto responseReportDto3;

    private List<ReportResponseDto> reportResponseDtos = new ArrayList<>();
    private List<ReportDto> reports = new ArrayList<>();
    private List<ReportDto> reports2 = new ArrayList<>();
    private List<ReportDto> reports3 = new ArrayList<>();


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

        reportDto = ReportDto.builder().build();
        reportDto2 = ReportDto.builder().build();
        reportDto3 = ReportDto.builder().build();
        reports.addAll(Arrays.asList(reportDto,reportDto2, reportDto3));
        reports2.addAll(Arrays.asList(reportDto,reportDto2, reportDto3));
        reports3.addAll(Arrays.asList(reportDto,reportDto2, reportDto3));
        unitsDto = UnitsDto.builder()
                .bs(reports)
                .cf(reports2)
                .ic(reports3)
                .build();
        unitsDto2 = UnitsDto.builder()
                .bs(reports)
                .cf(reports2)
                .ic(reports3)
                .build();
        unitsDto3 = UnitsDto.builder()
                .bs(reports)
                .cf(reports2)
                .ic(reports3).build();
        responseReportDto = ReportResponseDto.builder()
                .report(unitsDto)
                .build();
        responseReportDto2 = ReportResponseDto.builder()
                .report(unitsDto2)
                .build();
        responseReportDto3 = ReportResponseDto.builder()
                .report(unitsDto3)
                .build();

        reportResponseDtos.addAll(Arrays.asList(responseReportDto, responseReportDto2, responseReportDto3));

        dataDto = DataDto.builder()
                .data(reportResponseDtos)
                .build();


    }

    @Test
    public void saveQuotesTest() {

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
        when(companyRepo.findBySymbol(companyEntity.getSymbol())).thenReturn(Optional.of(companyEntity));

        when(finnhubClient.getReports(companyEntity.getSymbol())).thenReturn(dataDto);

        List<ReportDto> actual = infoCompanyService.getFinnhubReport(companyEntity.getSymbol());
        assertEquals(10, actual.size());
    }
}
