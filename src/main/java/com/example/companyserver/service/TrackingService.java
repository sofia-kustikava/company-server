package com.example.companyserver.service;

import com.example.companyserver.dto.CompanyDto;
import com.example.companyserver.dto.QuoteDto;
import com.example.companyserver.dto.metric.MetricDto;
import com.example.companyserver.dto.report.ReportDto;
import com.example.companyserver.entity.*;
import com.example.companyserver.exceptions.*;
import com.example.companyserver.mapper.CompanyMapper;
import com.example.companyserver.mapper.MetricMapper;
import com.example.companyserver.mapper.QuoteMapper;
import com.example.companyserver.repo.CompanyRepo;
import com.example.companyserver.repo.MetricRepo;
import com.example.companyserver.repo.QuoteRepo;
import com.example.companyserver.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackingService {

    private final UserRepo userRepo;
    private final CompanyRepo companyRepo;
    private final CompanyMapper companyMapper;
    private final InfoCompanyService infoCompanyService;
    private final QuoteRepo quoteRepo;
    private final QuoteMapper quoteMapper;
    private final MetricRepo metricRepo;
    private final MetricMapper metricMapper;
    private final AuthenticationService authenticationService;

    public void addUserCompany(String symbol) {
        UserEntity user = authenticationService.getUser();
        if (isUserHaveAccessToGetCompanies(user)) {
            CompanyEntity company = companyRepo.findBySymbol(symbol)
                    .orElseThrow(() -> new CompanyNotFoundException(String.format("%s", symbol)));
            if (user.getCompanies().size() < user.getSubscription().getSubscription().getTrackingSize()) {
                user.getCompanies().add(company);
                userRepo.save(user);
            } else {
                log.info("You can't add another company.");
                throw new MaximumCompaniesException();
            }
        } else {
            log.info("This user don't have an access to this method {}", user.getEmail());
            throw new NoAccessTrackingException(user.getEmail());
        }
    }

    public List<CompanyDto> getUserCompanies() {
        UserEntity user = authenticationService.getUser();
        if (!user.getSubscription().getSubscriptionStatus().equals(SubscriptionStatus.ACTIVE)) throw new NotTrackingException(user.getEmail());
        List<CompanyEntity> companies = user.getCompanies();
        return companyMapper.companiesToDto(companies);
    }

    private boolean isUserHaveAccessToGetCompanies(UserEntity user) {
        return user.getSubscription() != null && user.getSubscription().getSubscriptionStatus().equals(SubscriptionStatus.ACTIVE);
    }

    public void deleteCompany(String symbol) {
        UserEntity user = authenticationService.getUser();
        if (!user.getSubscription().getSubscriptionStatus().equals(SubscriptionStatus.ACTIVE)) throw new NotTrackingException(user.getEmail());
        if (isTrackingSymbol(user, symbol)) {
            CompanyEntity company = companyRepo.findBySymbol(symbol).orElseThrow(() -> new CompanyNotFoundException(symbol));
            user.getCompanies().remove(company);
            userRepo.save(user);
            log.info("Company was deleted with this symbol: {}", symbol);
        } else {
            log.info("This company not exist on user's tracking list {}", symbol);
            throw new NotTrackingException(symbol);
        }
    }

    public List<QuoteDto> getTrackingQuote(String symbol) {
        UserEntity user = authenticationService.getUser();
        if (!user.getSubscription().getSubscriptionStatus().equals(SubscriptionStatus.ACTIVE)) throw new NotTrackingException(user.getEmail());
        if (isTrackingSymbol(user, symbol)) {
            CompanyEntity company = companyRepo.findBySymbol(symbol).orElseThrow(() -> new CompanyNotFoundException(symbol));
            List<QuoteEntity> quotes = quoteRepo.findByCompanies(company);

            return quotes.stream().map(quoteMapper::quoteToDto).collect(Collectors.toList());
        } else {
            log.info("This company not exist on your tracking list {}", symbol);
            throw new NotTrackingException(symbol);
        }

    }

    public MetricDto getTrackingMetric(String symbol) {
        UserEntity user = authenticationService.getUser();
        if (!user.getSubscription().getSubscriptionStatus().equals(SubscriptionStatus.ACTIVE)) throw new NotTrackingException(user.getEmail());
        if (!user.getSubscription().getSubscription().getName().equals("Bronze")) {
            if (isTrackingSymbol(user, symbol)) {
                CompanyEntity company = companyRepo.findBySymbol(symbol).orElseThrow(() -> new CompanyNotFoundException(symbol));
                MetricEntity metric = metricRepo.findByCompanies(company)
                        .orElseThrow(() -> new CompanyNotFoundException(String.format("%s", symbol)));
                return metricMapper.metricToDto(metric);
            } else {
                log.info("This company not exist on user's tracking list {}", symbol);
                throw new NotTrackingException(symbol);
            }
        } else {
            log.info("This user don't have an access to this method {}", user.getSubscription().getSubscription().getName());
            throw new NoAccessTrackingException(user.getEmail());
        }
    }

    public List<ReportDto> getTrackingReport(String symbol) {
        UserEntity user = authenticationService.getUser();
        if (!user.getSubscription().getSubscriptionStatus().equals(SubscriptionStatus.ACTIVE)) throw new NotTrackingException(user.getEmail());
        if (user.getSubscription().getSubscription().getName().equals("Golden")) {
            if (isTrackingSymbol(user, symbol)) {
                return infoCompanyService.getFinnhubReport(symbol);
            } else {
                log.info("This company not exist on user's tracking list {}", symbol);
                throw new NotTrackingException(symbol);
            }
        } else {
            log.info("This user don't have an access to this method {}", user.getSubscription().getSubscription().getName());
            throw new NoAccessTrackingException(user.getEmail());
        }
    }

    private boolean isTrackingSymbol(UserEntity user, String symbol) {
        return user.getCompanies() != null && user.getCompanies()
                .stream()
                .map(CompanyEntity::getSymbol)
                .collect(Collectors.toList())
                .contains(symbol);
    }
}
