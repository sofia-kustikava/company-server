package com.example.companyserver.service;

import com.example.companyserver.client.FinnhubClient;
import com.example.companyserver.dto.CompanyDto;
import com.example.companyserver.entity.CompanyEntity;
import com.example.companyserver.mapper.CompanyMapper;
import com.example.companyserver.repo.CompanyRepo;
import com.example.companyserver.utils.CompanyData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @Mock
    private CompanyRepo companyRepo;

    @Mock
    private CompanyMapper companyMapper;

    @Mock
    private FinnhubClient finnhubClient;

    @InjectMocks
    private CompanyService companyService;

    private List<CompanyDto> companiesDto = new ArrayList<>();
    private List<CompanyEntity> companies = new ArrayList<>();
    private CompanyEntity companyEntity;

    @BeforeEach
    public void beforeTest() {
        companiesDto.add(CompanyData.getCompanyDto("ONFA1"));
        companyEntity = CompanyData.getCompany("ONFA1");
        companies.add(companyEntity);
    }

    @Test
    public void getCompaniesTest() {
        finnhubClient.getCompanies();
        verify(finnhubClient).getCompanies();
    }

    @Test
    public void saveCompaniesTest() {
        when(companyRepo.findBySymbol(companyEntity.getSymbol())).thenReturn(Optional.of(companyEntity));
        companyService.saveCompanies(companies);
        companies.forEach(companyEntity->verify(companyRepo).save(companyEntity));
    }

    @Test
    public void deleteCompanyBySymbolTest() {
        when(companyRepo.findBySymbol(companyEntity.getSymbol())).thenReturn(Optional.of(companyEntity));
        companyService.deleteCompany(companyEntity.getSymbol());
    }

    @Test
    public void deleteAllCompaniesTest() {
        when(finnhubClient.getCompanies()).thenReturn(companiesDto);

        companyService.deleteAllCompanies();
        verify(companyMapper).dtoToCompanies(companiesDto);
    }

}