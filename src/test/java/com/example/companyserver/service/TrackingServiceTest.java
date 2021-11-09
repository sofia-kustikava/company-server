package com.example.companyserver.service;

import com.example.companyserver.dto.CompanyDto;
import com.example.companyserver.dto.QuoteDto;
import com.example.companyserver.dto.metric.MetricDto;
import com.example.companyserver.dto.report.DataDto;
import com.example.companyserver.dto.report.ReportDto;
import com.example.companyserver.dto.report.ReportResponseDto;
import com.example.companyserver.dto.report.UnitsDto;
import com.example.companyserver.entity.*;
import com.example.companyserver.mapper.CompanyMapper;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrackingServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private CompanyRepo companyRepo;

    @Mock
    private CompanyMapper companyMapper;

    @Mock
    private InfoCompanyService infoCompanyService;

    @Mock
    private QuoteRepo quoteRepo;

    @Mock
    private QuoteMapper quoteMapper;

    @Mock
    private MetricRepo metricRepo;

    @Mock
    private MetricMapper metricMapper;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private TrackingService trackingService;


    UserEntity user;
    SubscriptionEntity subscription;
    CompanyEntity companyEntity;
    CompanyDto companyDto;
    List<CompanyEntity> companies = new ArrayList<>();
    List<CompanyDto> companyDtos = new ArrayList<>();

    @BeforeEach
    public void beforeTest() {
        subscription = TestingData.getSubscription();
        UserSubscriptionEntity userPaidSubscription = TestingData.getUserSubscription(LocalDate.now().minusDays(3), SubscriptionStatus.ACTIVE);
        userPaidSubscription.setSubscription(subscription);
        user = TestingData.getUser(1L, UserStatus.ACTIVE);
        userPaidSubscription.setUser(user);
        user.setSubscription(userPaidSubscription);
        companyEntity = TestingData.getCompany("ONFA");
        companyDto = TestingData.getCompanyDto("ONFA");
        companies.add(companyEntity);
        companyDtos.add(companyDto);
        user.setCompanies(companies);
    }

    @Test
    public void addUserCompanyTest() {
        when(authenticationService.getUser()).thenReturn(user);
        when(companyRepo.findBySymbol(companyEntity.getSymbol())).thenReturn(Optional.of(companyEntity));
        trackingService.addUserCompany(companyEntity.getSymbol());
        verify(userRepo).save(user);
    }

    @Test
    public void getUserCompaniesTest() {
        when(authenticationService.getUser()).thenReturn(user);
        when(companyMapper.companiesToDto(user.getCompanies())).thenReturn(companyDtos);
        List<CompanyDto> actual = trackingService.getUserCompanies();
        assertEquals(companyDtos, actual);
    }

    @Test
    public void deleteCompanyTest() {
        when(authenticationService.getUser()).thenReturn(user);
        when(companyRepo.findBySymbol(companyEntity.getSymbol())).thenReturn(Optional.of(companyEntity));
        trackingService.deleteCompany("ONFA");
        verify(userRepo).save(user);
    }

    @Test
    public void getTrackingQuoteTest() {
        List<QuoteEntity> quotes = new ArrayList<>();
        List<QuoteDto> quoteDtos = new ArrayList<>();
        QuoteDto quoteDto = TestingData.getQuoteDto(2D);
        QuoteEntity quote = TestingData.getQuote(1D);
        quote.setCompanies(companyEntity);
        quoteDtos.add(quoteDto);
        when(authenticationService.getUser()).thenReturn(user);
        when(companyRepo.findBySymbol(companyEntity.getSymbol())).thenReturn(Optional.of(companyEntity));
        when(quoteRepo.findByCompanies(user.getCompanies().get(0))).thenReturn(quotes);
        when(quoteMapper.quoteToDto(quote)).thenReturn(quoteDto);
        List<QuoteDto> actual = trackingService.getTrackingQuote(companyEntity.getSymbol());
        assertEquals(quoteDtos, actual);

    }

    @Test
    public void getTrackingMetricTest() {
        MetricDto metricDto = TestingData.getMetricDto(1D);
        MetricEntity metric = TestingData.getMetric(1D);
        metric.setCompanies(companyEntity);
        when(authenticationService.getUser()).thenReturn(user);
        when(companyRepo.findBySymbol(companyEntity.getSymbol())).thenReturn(Optional.of(companyEntity));
        when(metricRepo.findByCompanies(user.getCompanies().get(0))).thenReturn(Optional.of(metric));
        when(metricMapper.metricToDto(metric)).thenReturn(metricDto);
        MetricDto actual = trackingService.getTrackingMetric("ONFA");
        assertEquals(metricDto, actual);
    }

    @Test
    public void getTrackingReportTest() {
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
        when(authenticationService.getUser()).thenReturn(user);
        when(infoCompanyService.getFinnhubReport(companyEntity.getSymbol())).thenReturn(reports);
        List<ReportDto> actual = trackingService.getTrackingReport(companyEntity.getSymbol());
        assertEquals(3, actual.size());
    }
}
