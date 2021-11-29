package com.example.companyserver.service;

import com.example.companyserver.client.FinnhubClient;
import com.example.companyserver.dto.CompanyDto;
import com.example.companyserver.dto.QuoteDto;
import com.example.companyserver.dto.metric.MetricDto;
import com.example.companyserver.dto.report.ReportDto;
import com.example.companyserver.entity.*;
import com.example.companyserver.exceptions.MaximumCompaniesException;
import com.example.companyserver.exceptions.NoAccessTrackingException;
import com.example.companyserver.exceptions.NotTrackingException;
import com.example.companyserver.mapper.CompanyMapper;
import com.example.companyserver.repo.CompanyRepo;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    private FinnhubClient finnhubClient;

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
        subscription = TestingData.getSubscription(1L, "Golden");
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

//    @Test
//    public void getTrackingQuoteTest() {
//        when(authenticationService.getUser()).thenReturn(user);
//        List<QuoteDto> actual = trackingService.getTrackingQuote(companyEntity.getSymbol());
//        List<QuoteDto> expected = finnhubClient.getTrackingQuote(companyEntity.getSymbol());
//        assertEquals(expected, actual);
//
//    }
//
//    @Test
//    public void getTrackingMetricTest() {
//        when(authenticationService.getUser()).thenReturn(user);
//        when(companyRepo.findBySymbol(companyEntity.getSymbol())).thenReturn(Optional.of(companyEntity));
//        MetricDto expected = finnhubClient.getTrackingMetric(companyEntity.getSymbol());
//        MetricDto actual = trackingService.getTrackingMetric("ONFA");
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void getTrackingReportTest() {
//        ReportDto reportDto = TestingData.getReport();
//        ReportDto reportDto2 = TestingData.getReport();
//        ReportDto reportDto3 = TestingData.getReport();
//
//        List<ReportDto> reports = List.of(reportDto, reportDto2, reportDto3);
//
//        when(authenticationService.getUser()).thenReturn(user);
//        List<ReportDto> expected = finnhubClient.getReport(companyEntity.getSymbol());
//        List<ReportDto> actual = trackingService.getTrackingReport(companyEntity.getSymbol());
//        assertEquals(3, actual.size());
//    }

    @Test
    public void noAccessTrackingExceptionTest() {
        UserEntity userWithNoSub = TestingData.getUser(2L, UserStatus.BANNED);
        when(authenticationService.getUser()).thenReturn(userWithNoSub);
        assertThrows(NoAccessTrackingException.class, () -> trackingService.addUserCompany(companyEntity.getSymbol()));
    }

    @Test
    public void maximumCompaniesExceptionTest() {
        UserEntity userWithMaxCompanies = TestingData.getUser(3L, UserStatus.ACTIVE);
        UserSubscriptionEntity userPaidSubscription = TestingData.getUserSubscription(LocalDate.now().minusDays(3), SubscriptionStatus.ACTIVE);
        userPaidSubscription.setSubscription(subscription);
        userPaidSubscription.setUser(userWithMaxCompanies);
        userWithMaxCompanies.setSubscription(userPaidSubscription);
        CompanyEntity companyEntity2 = TestingData.getCompany("ONFA2");
        CompanyEntity companyEntity3 = TestingData.getCompany("ONFA3");
        List<CompanyEntity> companiesUsersWithMaxCompanies = List.of(companyEntity, companyEntity2, companyEntity3);
        userWithMaxCompanies.setCompanies(companiesUsersWithMaxCompanies);
        when(authenticationService.getUser()).thenReturn(userWithMaxCompanies);
        when(companyRepo.findBySymbol(companyEntity.getSymbol())).thenReturn(Optional.of(companyEntity));
        assertThrows(MaximumCompaniesException.class, () -> trackingService.addUserCompany(companyEntity.getSymbol()));
    }

    @Test
    public void notTrackingExceptionTest() {
        when(authenticationService.getUser()).thenReturn(user);
        assertThrows(NotTrackingException.class, () -> trackingService.deleteCompany("ONFA2"));
    }
}
