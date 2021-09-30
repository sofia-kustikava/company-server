package com.example.companyserver.service;

import com.example.companyserver.dto.CompaniesDto;
import com.example.companyserver.dto.MetricsDto;
import com.example.companyserver.dto.QuoteDto;
import com.example.companyserver.dto.ReportsDto;
import com.example.companyserver.entity.UsersEntity;
import com.example.companyserver.feign.FinnhubClient;
import com.example.companyserver.mapper.CompaniesMapper;
import com.example.companyserver.repo.UsersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinnhubService {

    private final UsersRepo usersRepo;
    private final CompaniesMapper companiesMapper;
    private  FinnhubClient finnhubClient;

    private UsersEntity getToken() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        return usersRepo.findByEmail(username).orElseThrow(() -> new RuntimeException("There is no user with this email"));
    }
    public List<CompaniesDto> getCompanies() {
        return finnhubClient.getCompanies();
//        UsersEntity user = getToken();
//        return companiesMapper.INSTANCE.companiesToDto(user.getCompanies());
    }


}
