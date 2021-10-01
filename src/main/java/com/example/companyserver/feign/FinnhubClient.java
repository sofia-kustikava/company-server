package com.example.companyserver.feign;

import com.example.companyserver.dto.CompanyDto;
import com.example.companyserver.dto.MetricDto;
import com.example.companyserver.dto.QuoteDto;
import com.example.companyserver.dto.ReportDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "finnhub", url = "https://finnhub.io")
public interface FinnhubClient {

    @GetMapping("/api/v1/stock/symbol?exchange=${exchange}&token=${token}")
        List<CompanyDto> getCompanies();

    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/stock/financials-reported?symbol={symbol}&token=${token}")
    ReportDto getReports(@PathVariable(name = "symbol") String symbol);

    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/quote?symbol={symbol}&token=${token}")
        QuoteDto getQuote(@PathVariable(name = "symbol") String symbol);

    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/stock/metric?symbol={symbol}&metric=all&token=${token}")
    MetricDto getMetrics(@PathVariable(name = "symbol") String symbol);
}
