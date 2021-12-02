package com.example.companyserver.utils;

import com.example.companyserver.dto.*;
import com.example.companyserver.dto.metric.MetricDto;
import com.example.companyserver.dto.report.ReportDto;
import com.example.companyserver.dto.report.UnitsDto;
import com.example.companyserver.entity.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

import java.time.LocalDate;
import java.util.*;

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

    public static SubscriptionEntity getSubscription(Long id, String name) {
        return SubscriptionEntity.builder()
                .id(id)
                .name(name)
                .description("Description sample")
                .price(90D)
                .trackingSize(3)
                .build();
    }

    public static SubscriptionDto getSubscriptionDto(String name) {
        return SubscriptionDto.builder()
                .name(name)
                .description("Description sample")
                .price(90D)
                .trackingSize(3)
                .build();
    }

    public static UserSubscriptionEntity getUserSubscription(LocalDate dateEnd, SubscriptionStatus status) {
        return UserSubscriptionEntity.builder()
                .dateEnd(dateEnd)
                .dateStart(LocalDate.now())
                .subscriptionStatus(status)
                .build();
    }

    public static CompanyEntity getCompany(String symbol) {
        return CompanyEntity.builder()
                .symbol(symbol)
                .build();
    }

    public static CompanyDto getCompanyDto(String symbol) {
        return CompanyDto.builder()
                .symbol(symbol)
                .build();
    }

    public static Map paypalSdkConfig() {
        Map configMap = new HashMap<>();
        configMap.put("mode", "mode");
        return configMap;
    }

    public static OAuthTokenCredential oAuthTokenCredential() {
        return new OAuthTokenCredential("clientId", "clientSecret", paypalSdkConfig());
    }

    public static APIContext apiContext() throws PayPalRESTException {
        APIContext context = new APIContext(oAuthTokenCredential().getAccessToken());
        context.setConfigurationMap(paypalSdkConfig());
        return context;
    }
}
