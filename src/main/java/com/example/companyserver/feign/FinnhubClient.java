package com.example.companyserver.feign;

import com.example.companyserver.dto.CompaniesDto;
import com.example.companyserver.dto.MetricsDto;
import com.example.companyserver.dto.QuoteDto;
import com.example.companyserver.dto.ReportsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "finnhub", url = "https://finnhub.io")
public interface FinnhubClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/stock/symbol?exchange=${exchange}&token=${token}")
        List<CompaniesDto> getCompanies();


    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/stock/financials-reported?symbol={symbol}&token=${token}")
        ReportsDto getReports(@PathVariable(name = "symbol") String symbol);


    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/quote?symbol={symbol}&token=${token}")
        QuoteDto getQuote(@PathVariable(name = "symbol") String symbol);


    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/stock/metric?symbol={symbol}&metric=all&token=${token}")
        MetricsDto getMetrics(@PathVariable(name = "symbol") String symbol);
}
