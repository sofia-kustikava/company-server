package com.example.companyserver.utils;

import com.example.companyserver.dto.*;
import com.example.companyserver.dto.metric.MetricDto;
import com.example.companyserver.dto.report.ReportDto;
import com.example.companyserver.dto.report.UnitsDto;
import com.example.companyserver.entity.*;
import com.paypal.api.payments.*;
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

    public static SubscriptionEntity getSubscription() {
        return SubscriptionEntity.builder()
                .name("Golden")
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
    public static SubscriptionNameDto getSubscriptionName(String name) {
        return SubscriptionNameDto.builder()
                .name(name)
                .build();
    }
    public static RedirectUrls createRedirectUrls() {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://somedomain.com");
        redirectUrls.setReturnUrl("http://somedomain.com");
        return  redirectUrls;
    }

    public static Amount createAmount(String total) {
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(total);
        return amount;
    }

    public static Transactions createTransactions(Amount amount) {
        Transactions transactions = new Transactions();
        transactions.setAmount(amount);
        return transactions;
    }

    public static Payer createPayer() {
        Payer payer = new Payer();
        payer.setPaymentMethod("PAYPAL");
        return payer;
    }

    public static Payment createPayment(Payer payer, RedirectUrls redirectUrls) {
        Payment payment = new Payment();
        payment.setIntent("SALE");
        payment.setId("1L");
        payment.setPayer(payer);
        payment.setRedirectUrls(redirectUrls);
        return payment;
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
