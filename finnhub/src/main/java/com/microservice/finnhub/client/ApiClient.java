package com.microservice.finnhub.client;

import com.microservice.finnhub.dto.CompanyDto;
import com.microservice.finnhub.dto.QuoteDto;
import com.microservice.finnhub.dto.metric.MetricResponseDto;
import com.microservice.finnhub.dto.report.DataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "finnhub", url = "https://finnhub.io")
public interface ApiClient {

    @GetMapping("/api/v1/stock/symbol?exchange=${exchange}&token=${token}")
    List<CompanyDto> getCompanies();

    @GetMapping("/api/v1/stock/financials-reported?symbol={symbol}&token=${token}")
    DataDto getReports(@PathVariable(name = "symbol") String symbol);

    @GetMapping("/api/v1/quote?symbol={symbol}&token=${token}")
    QuoteDto getQuote(@PathVariable(name = "symbol") String symbol);

    @GetMapping("/api/v1/stock/metric?symbol={symbol}&metric=all&token=${token}")
    MetricResponseDto getMetrics(@PathVariable(name = "symbol") String symbol);
}
