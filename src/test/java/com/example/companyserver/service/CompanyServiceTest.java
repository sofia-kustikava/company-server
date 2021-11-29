package com.example.companyserver.service;

import com.example.companyserver.dto.CompanyDto;
import com.example.companyserver.entity.CompanyEntity;
import com.example.companyserver.repo.CompanyRepo;
import com.example.companyserver.utils.TestingData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @Mock
    private CompanyRepo companyRepo;


    @InjectMocks
    private CompanyService companyService;

    private List<CompanyEntity> companies = new ArrayList<>();
    private List<CompanyDto> companyDtos = new ArrayList<>();
    private CompanyEntity companyEntity;
    private CompanyDto companyDto;

    @BeforeEach
    public void beforeTest() {
        companyEntity = TestingData.getCompany("ONFA1");
        companyDto = TestingData.getCompanyDto("ONFA1");
        companies.add(companyEntity);
        companyDtos.add(companyDto);
    }

    @Test
    public void saveCompaniesTest() {
        when(companyRepo.findBySymbol(companyEntity.getSymbol())).thenReturn(Optional.of(companyEntity));
        companyService.saveCompanies(companies);
        companies.forEach(companyEntity->verify(companyRepo).save(companyEntity));
    }
}