package com.example.companyserver.service;

import com.example.companyserver.client.FinnhubClient;
import com.example.companyserver.dto.QuoteDto;
import com.example.companyserver.dto.metric.MetricDto;
import com.example.companyserver.dto.report.ReportDto;
import com.example.companyserver.entity.CompanyEntity;
import com.example.companyserver.entity.MetricEntity;
import com.example.companyserver.entity.QuoteEntity;
import com.example.companyserver.exceptions.CompanyNotFoundException;
import com.example.companyserver.mapper.MetricMapper;
import com.example.companyserver.mapper.QuoteMapper;
import com.example.companyserver.repo.CompanyRepo;
import com.example.companyserver.repo.MetricRepo;
import com.example.companyserver.repo.QuoteRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class InfoCompanyService {

    private final CompanyRepo companyRepo;
    private final QuoteRepo quoteRepo;
    private final MetricRepo metricRepo;
    private final FinnhubClient finnhubClient;
    public final QuoteMapper quoteMapper;
    public final MetricMapper metricMapper;

    public void saveQuotes() {
        List<CompanyEntity> companies = companyRepo.findAll();
        List<QuoteEntity> collect = companies.stream()
                .limit(10).map(company -> {
                    QuoteEntity quoteEntity = quoteMapper.dtoToQuote(finnhubClient.getQuote(company.getSymbol()));
                    quoteEntity.setCompanies(company);
                    return quoteEntity;
                })
                .collect(Collectors.toList());
        quoteRepo.saveAll(collect);
    }

    public void saveMetrics() {
        List<CompanyEntity> companies = companyRepo.findAll();
        List<MetricEntity> collect = companies.stream()
                .limit(10).map(company -> {
                    MetricEntity metricEntities = metricMapper.dtoToMetric(finnhubClient.getMetrics(company.getSymbol()).getMetric());
                    metricEntities.setCompanies(company);
                    return metricEntities;
                })
                .collect(Collectors.toList());
        metricRepo.saveAll(collect);
    }

    public QuoteDto getFinnhubQuote(String symbol) {
        CompanyEntity companySymbol = companyRepo.findBySymbol(symbol).orElseThrow(() -> new CompanyNotFoundException(String.format("%s", symbol)));
        return finnhubClient.getQuote(companySymbol.getSymbol());
    }

    public MetricDto getFinnhubMetric(String symbol) {
        CompanyEntity companySymbol = companyRepo.findBySymbol(symbol).orElseThrow(() -> new CompanyNotFoundException(String.format("%s", symbol)));
        return finnhubClient.getMetrics(companySymbol.getSymbol()).getMetric();
    }

    public List<ReportDto> getFinnhubReport(String symbol) {
        CompanyEntity companySymbol = companyRepo.findBySymbol(symbol).orElseThrow(() -> new CompanyNotFoundException(String.format("%s", symbol)));
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
}
