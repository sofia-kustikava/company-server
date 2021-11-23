package com.microservice.finnhub.utils;

import com.microservice.finnhub.dto.CompanyDto;
import com.microservice.finnhub.dto.QuoteDto;
import com.microservice.finnhub.dto.metric.MetricDto;
import com.microservice.finnhub.dto.report.ReportDto;
import com.microservice.finnhub.dto.report.UnitsDto;
import com.microservice.finnhub.entity.CompanyEntity;
import com.microservice.finnhub.entity.MetricEntity;
import com.microservice.finnhub.entity.QuoteEntity;

import java.util.*;

public class TestingData {

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

