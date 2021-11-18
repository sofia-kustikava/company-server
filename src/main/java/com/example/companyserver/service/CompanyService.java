package com.example.companyserver.service;

import com.example.companyserver.client.MicroserviceClient;
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
    private final UserRepo userRepo;
    private final MicroserviceClient microserviceClient;


    public void saveCompanies(List<CompanyEntity> companies) {
        companies.stream().limit(100).forEach(companyEntity -> {
            Optional<CompanyEntity> bySymbol = companyRepo.findBySymbol(companyEntity.getSymbol());
            bySymbol.ifPresent(entity -> companyEntity.setId(entity.getId()));
            companyRepo.save(companyEntity);
        });
    }

    public void deleteCompany(String symbol) {
        CompanyEntity company = companyRepo.findBySymbol(symbol)
                .orElseThrow(() -> new CompanyNotFoundException(String.format("%s", symbol)));
        List<UserEntity> users = company.getUsers();
        users.forEach(user -> {
            List<CompanyEntity> companyEntities = user.getCompanies()
                    .stream()
                    .filter(companyEntity -> !companyEntity.equals(company))
                    .collect(Collectors.toList());
            user.setCompanies(companyEntities);
            userRepo.save(user);
        });
        companyRepo.delete(company);
        log.info("Company was deleted with this id: {}", symbol);
    }

    public List<CompanyDto> getFinnhubCompanies() {
        return microserviceClient.getCompanies();
    }
}
