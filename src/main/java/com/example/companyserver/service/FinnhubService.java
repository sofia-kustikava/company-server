package com.example.companyserver.service;

import com.example.companyserver.dto.CompanyDto;
import com.example.companyserver.entity.CompanyEntity;
import com.example.companyserver.entity.UserEntity;
import com.example.companyserver.feign.FinnhubClient;
import com.example.companyserver.mapper.CompanyMapper;
import com.example.companyserver.repo.CompanyRepo;
import com.example.companyserver.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinnhubService {

    private final UserRepo userRepo;
    private final CompanyRepo companyRepo;
    private final CompanyMapper companyMapper;
    private final FinnhubClient finnhubClient;

    private UserEntity getToken() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        return userRepo.findByEmail(username).orElseThrow(() -> new RuntimeException("There is no user with this email"));
    }

    public List<CompanyEntity> getCompanies() {
        return companyMapper.INSTANCE.dtoToCompanies(finnhubClient.getCompanies());
//        UsersEntity user = getToken();
//        return companiesMapper.INSTANCE.companiesToDto(user.getCompanies());
    }

    //не работает
    public void saveCompanies(CompanyDto companyDto) {
        CompanyEntity company = CompanyEntity.builder()
                .currency(companyDto.getCurrency())
                .description(companyDto.getDescription())
                .displaySymbol(companyDto.getDisplaySymbol())
                .figi(companyDto.getFigi())
                .mic(companyDto.getMic())
                .symbol(companyDto.getSymbol())
                .type(companyDto.getType())
                .build();
        companyRepo.save(company);
    }

}
