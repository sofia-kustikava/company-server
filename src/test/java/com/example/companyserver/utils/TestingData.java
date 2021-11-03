package com.example.companyserver.utils;

import com.example.companyserver.dto.*;
import com.example.companyserver.dto.metric.MetricDto;
import com.example.companyserver.dto.report.ReportDto;
import com.example.companyserver.dto.report.UnitsDto;
import com.example.companyserver.entity.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestingData {

    public static UserEntity getUser(Long id, UserStatus status) {
        return UserEntity.builder()
                .id(id)
                .firstName("User")
                .lastName("Userovich")
                .email("user@mail.com")
                .password("user")
                .status(status)
                .dateCreated(LocalDate.now())
                .updated(LocalDate.now())
                .roles(Arrays.asList(new RoleEntity(null, "USER", Collections.emptyList())))
                .build();
    }

    public static UserDto getDtoUser(Long id) {
        return UserDto.builder()
                .id(id)
                .firstName("User")
                .lastName("Userovich")
                .email("user@mail.com")
                .build();
    }

    public static RegisterDto getRegisterUser() {
        return RegisterDto.builder()
                .firstName("User")
                .lastName("Userovich")
                .email("user@mail.com")
                .password("user")
                .build();
    }

    public static AuthDto getAuthUser() {
        return AuthDto.builder()
                .email("user@mail.com")
                .build();
    }

    public static SubscriptionEntity getSubscription() {
        return SubscriptionEntity.builder()
                .name("Golden")
                .description("Description sample")
                .price(90D)
                .build();
    }

    public static SubscriptionNameDto getSubscriptionName() {
        return SubscriptionNameDto.builder()
                .name("Golden")
                .build();
    }

    public static UserSubscriptionEntity getUserSubscription(LocalDate dateEnd) {
        return UserSubscriptionEntity.builder()
                .dateEnd(dateEnd)
                .dateStart(LocalDate.now())
                .subscriptionStatus(SubscriptionStatus.ACTIVE)
                .build();
    }

    public static UserSubscriptionDto getUserSubscriptionDto(LocalDate dateEnd) {
        return UserSubscriptionDto.builder()
                .dateEnd(dateEnd)
                .dateStart(LocalDate.now())
                .subscriptionStatus(SubscriptionStatus.ACTIVE)
                .build();
    }

    public static CompanyEntity getCompany(String symbol) {
        return CompanyEntity.builder()
                .currency("USD")
                .description("ONE 4 ART LTD")
                .displaySymbol("ONFA")
                .figi("BBG002Q0F4D7")
                .mic("OOTC")
                .symbol(symbol)
                .type("Common Stock")
                .build();
    }

    public static CompanyDto getCompanyDto(String symbol) {
        return CompanyDto.builder()
                .currency("USD")
                .description("ONE 4 ART LTD")
                .displaySymbol("ONFA")
                .figi("BBG002Q0F4D7")
                .mic("OOTC")
                .symbol(symbol)
                .type("Common Stock")
                .build();
    }

    public static QuoteDto getQuoteDto(Double change) {
        return QuoteDto.builder()
                .currentPrice(1D)
                .change(change)
                .percentChange(1D)
                .highPrice(1D)
                .lowPrice(1D)
                .openPrice(1D)
                .closePrice(1D)
                .build();
    }

    public static QuoteEntity getQuote(Double change) {
        return QuoteEntity.builder()
                .currentPrice(1D)
                .change(change)
                .percentChange(1D)
                .highPrice(1D)
                .lowPrice(1D)
                .openPrice(1D)
                .closePrice(1D)
                .build();
    }

    public static MetricEntity getMetric(Double weekHigh) {
        return MetricEntity.builder()
                .weekHigh(weekHigh)
                .weekLow(1D)
                .build();
    }

    public static MetricDto getMetricDto(Double weekHigh) {
        return MetricDto.builder()
                .weekHigh(weekHigh)
                .weekLow(2D)
                .build();
    }

    public static ReportDto getReport() {
        return ReportDto.builder().build();
    }

    public static UnitsDto getUnits(List<ReportDto> bs, List<ReportDto> cf, List<ReportDto> ic) {
        return UnitsDto.builder()
                .bs(bs)
                .cf(cf)
                .ic(ic)
                .build();
    }
}
