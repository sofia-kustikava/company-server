package com.example.companyserver.service;

import com.example.companyserver.entity.CompanyEntity;
import com.example.companyserver.exceptions.CompanyNotFoundException;
import com.example.companyserver.client.FinnhubClient;
import com.example.companyserver.mapper.CompanyMapper;
import com.example.companyserver.repo.CompanyRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepo companyRepo;
    private final CompanyMapper companyMapper;
    private final FinnhubClient finnhubClient;

    public List<CompanyEntity> getCompanies() {
        return companyMapper.INSTANCE.dtoToCompanies(finnhubClient.getCompanies());
    }

    public void saveCompanies(List<CompanyEntity> companies) {
        companies.stream().limit(100).forEach(companyEntity -> {
            Optional<CompanyEntity> bySymbol = companyRepo.findBySymbol(companyEntity.getSymbol());
            bySymbol.ifPresent(entity -> companyEntity.setId(entity.getId()));
            companyRepo.save(companyEntity);
        });
    }

    public void deleteCompany(String symbol) {
        String variable = String.format("%s", symbol);
        companyRepo.delete(companyRepo.findBySymbol(symbol).orElseThrow(() -> new CompanyNotFoundException(variable)));
        log.info("Company was deleted with this id: ", symbol);
    }

    public void deleteAllCompanies() {
        companyRepo.deleteAll(companyMapper.INSTANCE.dtoToCompanies(finnhubClient.getCompanies()));
    }
}
