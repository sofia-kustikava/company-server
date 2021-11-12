package com.example.companyserver.service;

import com.example.companyserver.client.FinnhubClient;
import com.example.companyserver.dto.CompanyDto;
import com.example.companyserver.entity.CompanyEntity;
import com.example.companyserver.entity.UserEntity;
import com.example.companyserver.entity.UserStatus;
import com.example.companyserver.repo.CompanyRepo;
import com.example.companyserver.repo.UserRepo;
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

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @Mock
    private CompanyRepo companyRepo;

    @Mock
    private FinnhubClient finnhubClient;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private CompanyService companyService;

    private List<CompanyDto> companiesDto = new ArrayList<>();
    private List<CompanyEntity> companies = new ArrayList<>();
    private CompanyEntity companyEntity;

    @BeforeEach
    public void beforeTest() {
        companyEntity = TestingData.getCompany("ONFA1");
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
        UserEntity user1 = TestingData.getUser(1L, UserStatus.ACTIVE);
        UserEntity user2 = TestingData.getUser(2L, UserStatus.ACTIVE);
        user1.setCompanies(companies);
        user2.setCompanies(companies);
        List<UserEntity> users = new ArrayList<>(List.of(user1, user2));
        companyEntity.setUsers(users);

        companyService.deleteCompany(companyEntity.getSymbol());
        users.forEach(user -> {
            List<CompanyEntity> companyEntities = user.getCompanies()
                    .stream()
                    .filter(companyEntity -> !companyEntity.equals(companyEntity))
                    .collect(Collectors.toList());
            user.setCompanies(companyEntities);
            verify(userRepo).save(user);
        });
        verify(companyRepo).delete(companyEntity);

    }
}