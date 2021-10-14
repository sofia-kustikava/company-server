package com.example.companyserver.service;

import com.example.companyserver.client.FinnhubClient;
import com.example.companyserver.dto.report.ReportDto;
import com.example.companyserver.entity.CompanyEntity;
import com.example.companyserver.entity.MetricEntity;
import com.example.companyserver.entity.QuoteEntity;
import com.example.companyserver.mapper.CompanyMapper;
import com.example.companyserver.mapper.MetricMapper;
import com.example.companyserver.mapper.QuoteMapper;
import com.example.companyserver.repo.CompanyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class QuoteService {
    private final CompanyRepo companyRepo;
    private final CompanyMapper companyMapper;
    private final FinnhubClient finnhubClient;

    private final QuoteMapper quoteMapper;
    private final MetricMapper metricMapper;

    public QuoteEntity getQuote(String symbol) {
        CompanyEntity companySymbol = companyRepo.findBySymbol(symbol).orElseThrow(() -> new RuntimeException("There is no quote"));
        return quoteMapper.dtoToQuote(finnhubClient.getQuote(companySymbol.getSymbol()));
    }

    public MetricEntity getMetric(String symbol) {
        CompanyEntity companySymbol = companyRepo.findBySymbol(symbol).orElseThrow(() -> new RuntimeException("There is no quote"));
        return metricMapper.dtoToMetric(finnhubClient.getMetrics(companySymbol.getSymbol()).getMetric());
    }

    public List<ReportDto> getReport(String symbol) {
        CompanyEntity companySymbol = companyRepo.findBySymbol(symbol).orElseThrow(() -> new RuntimeException("There is no quote"));
        return finnhubClient.getReports(companySymbol.getSymbol()).getData()
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

    public List<CompanyEntity> getCompanies() {
        return companyMapper.dtoToCompanies(finnhubClient.getCompanies());
    }
}
