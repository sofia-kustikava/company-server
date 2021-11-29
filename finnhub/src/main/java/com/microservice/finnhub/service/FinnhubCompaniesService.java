package com.microservice.finnhub.service;

import com.microservice.finnhub.client.ApiClient;
import com.microservice.finnhub.dto.CompanyDto;
import com.microservice.finnhub.dto.QuoteDto;
import com.microservice.finnhub.dto.metric.MetricDto;
import com.microservice.finnhub.dto.report.ReportDto;
import com.microservice.finnhub.entity.CompanyEntity;
import com.microservice.finnhub.exception.CompanyNotFoundException;
import com.microservice.finnhub.mapper.CompanyMapper;
import com.microservice.finnhub.repo.CompanyRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class FinnhubCompaniesService {
    private final ApiClient apiClient;
    private final CompanyRepo companyRepo;
    private final CompanyMapper companyMapper;

    public List<CompanyDto> getCompanies() {
        return apiClient.getCompanies();
    }

    public List<CompanyDto> getDatabaseCompanies(){
        List<CompanyEntity> companies = companyRepo.findAll();
        return companyMapper.companiesToDto(companies);
    }

    public QuoteDto getQuote(String symbol) {
        CompanyEntity companySymbol = companyRepo.findBySymbol(symbol).orElseThrow(() -> new CompanyNotFoundException(String.format("%s", symbol)));
        return apiClient.getQuote(companySymbol.getSymbol());
    }

    public MetricDto getMetric(String symbol) {
        CompanyEntity companySymbol = companyRepo.findBySymbol(symbol).orElseThrow(() -> new CompanyNotFoundException(String.format("%s", symbol)));
        return apiClient.getMetrics(companySymbol.getSymbol()).getMetric();
    }

    public List<ReportDto> getReport(String symbol) {
        CompanyEntity companySymbol = companyRepo.findBySymbol(symbol).orElseThrow(() -> new CompanyNotFoundException(String.format("%s", symbol)));
        return apiClient.getReports(companySymbol.getSymbol()).getData()
                .stream()
                .flatMap((responseDto) ->
                        Stream.concat(
                                Stream.concat(
                                        responseDto.getReport().getBs().stream(),
                                        responseDto.getReport().getCf().stream()),
                                responseDto.getReport().getIc().stream()))
                .limit(10)
                .collect(Collectors.toList());
    }
}
