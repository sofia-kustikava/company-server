package com.example.companyserver.service;

import com.example.companyserver.client.FinnhubClient;
import com.example.companyserver.dto.QuoteDto;
import com.example.companyserver.dto.metric.MetricDto;
import com.example.companyserver.dto.report.ReportDto;
import com.example.companyserver.entity.CompanyEntity;
import com.example.companyserver.exceptions.CompanyNotFoundException;
import com.example.companyserver.repo.CompanyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class InfoCompanyService {
    private final CompanyRepo companyRepo;
    private final FinnhubClient finnhubClient;

//    @Scheduled(cron = "0 */15 * ? * *")
    public QuoteDto getQuote(String symbol) {
        CompanyEntity companySymbol = companyRepo.findBySymbol(symbol).orElseThrow(() -> new CompanyNotFoundException(String.format("%s", symbol)));
        return finnhubClient.getQuote(companySymbol.getSymbol());
    }

//    @Scheduled(cron = "0 */15 * ? * *")
    public MetricDto getMetric(String symbol) {
        CompanyEntity companySymbol = companyRepo.findBySymbol(symbol).orElseThrow(() -> new CompanyNotFoundException(String.format("%s", symbol)));
        return finnhubClient.getMetrics(companySymbol.getSymbol()).getMetric();
    }

//    @Scheduled(cron = "0 */15 * ? * *")
    public List<ReportDto> getReport(String symbol) {
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
