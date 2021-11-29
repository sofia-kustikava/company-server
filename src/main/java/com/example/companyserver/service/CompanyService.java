package com.example.companyserver.service;

import com.example.companyserver.client.FinnhubClient;
import com.example.companyserver.dto.CompanyDto;
import com.example.companyserver.entity.CompanyEntity;
import com.example.companyserver.entity.UserEntity;
import com.example.companyserver.exceptions.CompanyNotFoundException;
import com.example.companyserver.repo.CompanyRepo;
import com.example.companyserver.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepo companyRepo;
    private final FinnhubClient finnhubClient;


    public void saveCompanies(List<CompanyEntity> companies) {
        finnhubClient.saveAllCompanies();
        companies.forEach(companyEntity -> {
            Optional<CompanyEntity> bySymbol = companyRepo.findBySymbol(companyEntity.getSymbol());
            bySymbol.ifPresent(entity -> companyEntity.setId(entity.getId()));
            companyRepo.save(companyEntity);
        });
    }

    public List<CompanyDto> getFinnhubCompanies() {
        return finnhubClient.getCompanies();
    }

    public List<CompanyDto> getDatabaseCompanies() {
        return finnhubClient.getDatabaseCompanies();
    }
}
