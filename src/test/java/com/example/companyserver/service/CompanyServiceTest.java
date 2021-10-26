package com.example.companyserver.service;

import com.example.companyserver.client.FinnhubClient;
import com.example.companyserver.dto.CompanyDto;
import com.example.companyserver.entity.CompanyEntity;
import com.example.companyserver.mapper.CompanyMapper;
import com.example.companyserver.repo.CompanyRepo;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

    private List<CompanyEntity> companiesEntity = new ArrayList<>();
    private List<CompanyDto> companiesDto = new ArrayList<>();
    private CompanyEntity companyEntity;

    @BeforeEach
    public void beforeTest() {
        companiesEntity.add(CompanyEntity.builder()
                .currency("USD")
                .description("ONE 4 ART LTD")
                .displaySymbol("ONFA")
                .figi("BBG002Q0F4D7")
                .mic("OOTC")
                .symbol("ONFA")
                .type("Common Stock")
                .build()
        );

        companiesDto.add(CompanyDto.builder()
                .currency("USD")
                .description("ONE 4 ART LTD")
                .displaySymbol("ONFA")
                .figi("BBG002Q0F4D7")
                .mic("OOTC")
                .symbol("ONFA")
                .type("Common Stock")
                .build()
        );
        companyEntity = CompanyEntity.builder()
                .currency("USD")
                .description("ONE 4 ART LTD")
                .displaySymbol("ONFA")
                .figi("BBG002Q0F4D7")
                .mic("OOTC")
                .symbol("ONFA")
                .type("Common Stock")
                .build();
    }

    @Test
    public void getCompaniesTest() {
        finnhubClient.getCompanies();
        verify(finnhubClient).getCompanies();
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